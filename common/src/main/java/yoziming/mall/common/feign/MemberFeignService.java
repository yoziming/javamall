package yoziming.mall.common.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import yoziming.mall.common.config.FeignConfig;
import yoziming.mall.common.to.MemberAddressTo;
import yoziming.mall.common.utils.R;
import yoziming.mall.common.vo.SocialUser;
import yoziming.mall.common.vo.UserLoginVo;
import yoziming.mall.common.vo.UserRegisterVo;

import java.util.List;

@FeignClient(name = "member", configuration = FeignConfig.class)
public interface MemberFeignService {

    @PostMapping(value = "/member/member/register")
    R register(@RequestBody UserRegisterVo vo);

    @PostMapping(value = "/member/member/login")
    R login(@RequestBody UserLoginVo vo);

    @PostMapping(value = "/member/member/oauth2/login")
    R oauthLogin(@RequestBody SocialUser socialUser) throws Exception;

    @PostMapping(value = "/member/member/weixin/login")
    R weixinLogin(@RequestParam("accessTokenInfo") String accessTokenInfo);

    /**
     * 查詢當前用戶的全部收貨地址 done
     *
     * @param memberId
     * @return
     */
    @RequestMapping(value = "/member/memberreceiveaddress/{memberId}/address")
    List<MemberAddressTo> getAddress(@PathVariable("memberId") Long memberId);

    // 根據id獲取用戶地址信息  done
    @RequestMapping("/member/memberreceiveaddress/info/{id}")
    R info(@PathVariable("id") Long id);
}
