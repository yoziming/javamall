package com.yoziming.javamall.member.web;

import com.alibaba.fastjson.TypeReference;
import com.yoziming.common.constant.AuthServerConstant;
import com.yoziming.common.exception.BizCodeEnum;
import com.yoziming.common.to.CategoryVo;
import com.yoziming.common.utils.R;
import com.yoziming.common.vo.MemberRespVo;
import com.yoziming.javamall.member.controller.MemberController;
import com.yoziming.javamall.member.service.MemberService;
import com.yoziming.javamall.member.vo.MemberLoginVo;
import com.yoziming.javamall.member.vo.MemberRegistVo;
import com.yoziming.javamall.member.vo.PasswordFindVo;
import com.yoziming.javamall.product.controller.CategoryController;
import com.yoziming.javamall.thirdparty.controller.SmsSendController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Author: yoziming
 * @Date: 2022/2/3 16:12
 * @Description:
 */
@RequestMapping("/auth")
@Controller
@Slf4j
public class LoginController {

    //    @Autowired
    //    ThirdPartFeignService thirdPartFeignService;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    MemberController memberController;

    @Autowired
    MemberService memberService;

    @Autowired
    CategoryController categoryController;

    @Autowired
    SmsSendController smsSendController;

    @ResponseBody
    @GetMapping("/sms/sendcode")
    public R sendCode(@RequestParam("phone") String phone) {

        // 1、接口防刷
        String redisCode = redisTemplate.opsForValue().get(AuthServerConstant.SMS_CODE_CACHE_PREFIX + phone);
        if (!StringUtils.isEmpty(redisCode)) {
            long l = Long.parseLong(redisCode.split("_")[1]);
            if ((System.currentTimeMillis() - l) < 60000) {
                //60秒內不能再發
                return R.error(BizCodeEnum.SMS_CODE_EXCEPTION.getCode(), BizCodeEnum.SMS_CODE_EXCEPTION.getMessage());
            }
        }
        //2、驗證碼的再次校驗，redis, 存key-phone value-code    sms:code:
        String code = UUID.randomUUID().toString().substring(0, 5);
        //redis緩存驗證碼，防止同一個phone在60秒內再次發送驗證碼

        redisTemplate.opsForValue().set(AuthServerConstant.SMS_CODE_CACHE_PREFIX + phone,
                code + "_" + System.currentTimeMillis(), 10, TimeUnit.MINUTES);
        log.info("向{}發送驗證碼", phone);
        smsSendController.sendCode(phone, code);
        return R.ok();
    }

    /**
     * 只要跳到下一個頁面取出這個數據以後，session裏面的數據就會刪掉
     * RedirectAttributes redirectAttributes:模擬重定向攜帶數據
     *
     * @param vo
     * @param result
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/regist")
    public String regist(@Valid MemberRegistVo vo, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            Map<String, String> errors =
                    result.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField,
                            FieldError::getDefaultMessage));
            redirectAttributes.addFlashAttribute("errors", errors);
            //校驗出錯，轉發到註冊頁
            return "redirect:http://localhost:11000/auth/reg.html";
        }
        //1、校驗驗證碼
        String code = vo.getCode();
        String s = redisTemplate.opsForValue().get(AuthServerConstant.SMS_CODE_CACHE_PREFIX + vo.getPhone());
        if (!StringUtils.isEmpty(s)) {
            if (code.equals(s.split("_")[0])) {
                //刪除驗證碼;令牌機制
                redisTemplate.delete(AuthServerConstant.SMS_CODE_CACHE_PREFIX + vo.getPhone());
                //驗證碼通過。真正註冊。調用遠程服務進行註冊
                R regist = memberController.regist(vo);
                if (regist.getCode() == 0) {
                    //註冊成功回到登入頁
                    return "redirect:http://localhost:11000/auth/login.html";
                } else {
                    Map<String, String> errors = new HashMap<>();
                    errors.put("msg", "驗證碼錯誤");
                    redirectAttributes.addFlashAttribute("error", errors);
                    return "redirect:http://localhost:11000/auth/reg.html";
                }

            } else {
                Map<String, String> errors = new HashMap<>();
                errors.put("msg", "驗證碼錯誤");
                redirectAttributes.addFlashAttribute("error", errors);
                return "redirect:http://localhost:11000/auth/reg.html";
            }
        } else {
            Map<String, String> errors = new HashMap<>();
            errors.put("msg", "驗證碼錯誤");
            redirectAttributes.addFlashAttribute("error", errors);
            return "redirect:http://localhost:11000/auth/reg.html";
        }
    }

    @PostMapping("/login")
    public String login(MemberLoginVo vo, RedirectAttributes redirectAttributes
            , HttpSession session) {
        //遠程登入
        R login = memberController.login(vo);
        if (login.getCode() == 0) {
            //成功
            MemberRespVo data = login.getData("data", new TypeReference<MemberRespVo>() {
            });
            session.setAttribute(AuthServerConstant.LOGIN_USER, data);
            return "redirect:http://localhost:11000";
        } else {
            Map<String, String> errors = new HashMap<>();
            errors.put("msg", login.getData("msg", new TypeReference<String>() {
            }));
            redirectAttributes.addFlashAttribute("error", errors);
            return "redirect:http://localhost:11000/auth/login.html";
        }

    }

    @PostMapping("/passwordfind")
    public String passwordfind(PasswordFindVo vo, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            Map<String, String> errors =
                    result.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField,
                            FieldError::getDefaultMessage));
            redirectAttributes.addFlashAttribute("errors", errors);
            //校驗出錯，轉發到註冊頁
            return "redirect:http://localhost:11000/auth/reg.html";
        }
        //1、校驗驗證碼
        String code = vo.getCode();
        String s = redisTemplate.opsForValue().get(AuthServerConstant.SMS_CODE_CACHE_PREFIX + vo.getPhone());
        if (!StringUtils.isEmpty(s)) {
            if (code.equals(s.split("_")[0])) {
                //刪除驗證碼;令牌機制
                redisTemplate.delete(AuthServerConstant.SMS_CODE_CACHE_PREFIX + vo.getPhone());
                //驗證碼通過。真正註冊。調用遠程服務進行註冊
                try {
                    memberService.findPassword(vo);
                } catch (RuntimeException e) {
                    Map<String, String> errors = new HashMap<>();
                    errors.put("msg", "郵箱不存在！");
                    redirectAttributes.addFlashAttribute("error", errors);
                    return "redirect:http://localhost:11000/auth/passwordfind.html";
                }
                //註冊成功回到登入頁
                return "redirect:http://localhost:11000/auth/login.html";
            } else {
                Map<String, String> errors = new HashMap<>();
                errors.put("msg", "驗證碼錯誤");
                redirectAttributes.addFlashAttribute("error", errors);
                return "redirect:http://localhost:11000/auth/passwordfind.html";
            }
        } else {
            Map<String, String> errors = new HashMap<>();
            errors.put("msg", "驗證碼錯誤");
            redirectAttributes.addFlashAttribute("error", errors);
            return "redirect:http://localhost:11000/auth/passwordfind.html";
        }

    }

    @GetMapping("/login.html")
    public String loginPage(Model model) {
        R cat = categoryController.getCat();
        List<CategoryVo> category = cat.getData("category", new TypeReference<List<CategoryVo>>() {
        });
        model.addAttribute("category", category);
        return "login";
    }

    @GetMapping("/reg.html")
    public String regPage() {
        return "reg";
    }

    @GetMapping("/passwordfind.html")
    public String passwordfindPage() {
        return "passwordfind";
    }

    @GetMapping("/logout.html")
    public String logout(HttpSession session) {
        session.removeAttribute(AuthServerConstant.LOGIN_USER);
        return "redirect:http://localhost:11000";
    }
}
