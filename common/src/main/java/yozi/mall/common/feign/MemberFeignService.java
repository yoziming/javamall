package yozi.mall.common.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import yozi.mall.common.to.MemberAddressTo;
import yozi.mall.common.utils.R;
import yozi.mall.common.vo.SocialUser;
import yozi.mall.common.vo.UserLoginVo;
import yozi.mall.common.vo.UserRegisterVo;

import java.util.List;

@FeignClient("member")
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
    @GetMapping(value = "/member/memberreceiveaddress/{memberId}/address")
    List<MemberAddressTo> getAddress(@PathVariable("memberId") Long memberId);
}