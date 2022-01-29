package yozi.mall.authserver.controller;

import com.alibaba.fastjson.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import yozi.mall.common.constant.AuthServerConstant;
import yozi.mall.common.feign.MemberFeignService;
import yozi.mall.common.feign.ThirdPartFeignService;
import yozi.mall.common.to.MemberResponseTo;
import yozi.mall.common.utils.R;
import yozi.mall.common.vo.UserLoginVo;
import yozi.mall.common.vo.UserRegisterVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class LoginController {

    @Autowired
    private ThirdPartFeignService thirdPartFeignService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private MemberFeignService memberFeignService;

    // @ResponseBody
    // @GetMapping(value = "/sms/sendCode")
    // public R sendCode(@RequestParam("phone") String phone) {
    //
    //     //1、接口防刷
    //     String redisCode = stringRedisTemplate.opsForValue().get(AuthServerConstant.SMS_CODE_CACHE_PREFIX + phone);
    //     if (!StringUtils.isEmpty(redisCode)) {
    //         //活動存入redis的時間，用當前時間減去存入redis的時間，判斷用戶手機號是否在60s內發送驗證碼
    //         long currentTime = Long.parseLong(redisCode.split("_")[1]);
    //         if (System.currentTimeMillis() - currentTime < 60000) {
    //             //60s內不能再發
    //             return R.error(BizCodeEnume.SMS_CODE_EXCEPTION.getCode(), BizCodeEnume.SMS_CODE_EXCEPTION.getMsg());
    //         }
    //     }
    //
    //     //2、驗證碼的再次效驗 redis.存key-phone,value-code
    //     //        String code = UUID.randomUUID().toString().substring(0, 5);
    //     //        String redisValue = code+"_"+System.currentTimeMillis();
    //     int code = (int) ((Math.random() * 9 + 1) * 100000);// 驗證碼只可以是数字
    //     String codeNum = String.valueOf(code);
    //     String redisStorage = codeNum + "_" + System.currentTimeMillis();
    //
    //     //存入redis，防止同一個手機號在60秒內再次發送驗證碼
    //     stringRedisTemplate.opsForValue().set(AuthServerConstant.SMS_CODE_CACHE_PREFIX + phone,
    //             redisStorage, 365, TimeUnit.DAYS);
    //
    //     thirdPartFeignService.sendCode(phone, codeNum);
    //
    //     return R.ok();
    // }

    // 註冊
    @PostMapping(value = "/register")
    public String register(@Valid UserRegisterVo vos, BindingResult result, RedirectAttributes attributes) {

        // 如果有錯誤回到註冊頁面
        if (result.hasErrors()) {
            Map<String, String> errors =
                    result.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField,
                            FieldError::getDefaultMessage));
            attributes.addAttribute("errors", errors);
            return "redirect:http://auth.mall.com/reg.html";
        }
        // 前端驗證被繞過，若傳空值讓他返回
        if (vos.getPassword().isEmpty() || vos.getCode().isEmpty() || vos.getUserName().isEmpty() || vos.getPhone().isEmpty()) {
            return "redirect:http://auth.mall.com/reg.html";
        }

        // 獲取驗證碼
        String code = vos.getCode();
        //驗證碼通過，真正註冊，調用遠程服務進行註冊會員
        if (code.equals("1234")) {
            R register = memberFeignService.register(vos);
            if (register.getCode() == 0) {
                //成功
                return "redirect:http://auth.mall.com/login.html";
            } else {
                //失敗
                Map<String, String> errors = new HashMap<>();
                errors.put("msg", register.getData("msg", new TypeReference<String>() {
                }));
                attributes.addFlashAttribute("errors", errors);
                return "redirect:http://auth.mall.com/reg.html";
            }
        } else {
            //驗證碼錯誤
            Map<String, String> errors = new HashMap<>();
            errors.put("code", "驗證碼錯誤");
            attributes.addFlashAttribute("errors", errors);
            return "redirect:http://auth.mall.com/reg.html";
        }
    }

    @PostMapping(value = "/login")
    public String login(UserLoginVo vo, RedirectAttributes attributes, HttpSession session) {
        // 遠程調用member的驗證帳號密碼服務
        R login = memberFeignService.login(vo);
        if (login.getCode() == 0) {
            MemberResponseTo data = login.getData("data", new TypeReference<MemberResponseTo>() {
            });
            System.out.println("用戶" + data.getUsername() + "登入成功");
            session.setAttribute(AuthServerConstant.LOGIN_USER, data);
            return "redirect:http://mall.com";
        } else {
            Map<String, String> errors = new HashMap<>();
            errors.put("msg", login.getData("msg", new TypeReference<String>() {
            }));
            attributes.addFlashAttribute("errors", errors);
            return "redirect:http://auth.mall.com/login.html";
        }
    }

    /**
     * 判斷session是否有loginUser，沒有就跳轉登入頁面，有就跳轉首頁
     */
    @GetMapping(value = "/login.html")
    public String loginPage(HttpSession session) {
        // 從session先取出來用戶的信息，判斷用戶是否已經登入過了
        Object attribute = session.getAttribute(AuthServerConstant.LOGIN_USER);
        // 如果用戶沒登入那就跳轉到登入頁面
        if (attribute == null) {
            return "login";
        } else {
            return "redirect:http://mall.com";
        }
    }

    // 登出
    @GetMapping(value = "/loguot.html")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute(AuthServerConstant.LOGIN_USER);
        request.getSession().invalidate();
        return "redirect:http://mall.com";
    }

}
