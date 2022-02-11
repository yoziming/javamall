package com.yoziming.javamall.member.controller;

import com.yoziming.common.utils.PageUtils;
import com.yoziming.common.utils.R;
import com.yoziming.javamall.member.entity.MemberReceiveAddressEntity;
import com.yoziming.javamall.member.service.MemberReceiveAddressService;
import com.yoziming.javamall.member.vo.AddressVo;
import com.yoziming.javamall.ware.vo.FareVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 會員收貨地址
 *
 * @author yoziming
 * @date 2022-01-20 17:30:33
 */
@RestController
@RequestMapping("member/memberreceiveaddress")
public class MemberReceiveAddressController {
    @Autowired
    private MemberReceiveAddressService memberReceiveAddressService;

    @GetMapping("/fare")
    public R getFare(@RequestParam Long addrId) {
        FareVo fare = memberReceiveAddressService.getFare(addrId);
        return R.ok().setData(fare);
    }

    @GetMapping("/{memberId}/address")
    public List<MemberReceiveAddressEntity> getAddress(@PathVariable("memberId") Long memberId) {
        return memberReceiveAddressService.getAddress(memberId);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //    @RequiresPermissions("member:memberreceiveaddress:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = memberReceiveAddressService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 訊息
     */
    @RequestMapping("/info/{id}")
    //    @RequiresPermissions("member:memberreceiveaddress:info")
    public R info(@PathVariable("id") Long id) {
        MemberReceiveAddressEntity memberReceiveAddress = memberReceiveAddressService.getById(id);

        return R.ok().put("memberReceiveAddress", memberReceiveAddress);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AddressVo addressVo) {
        memberReceiveAddressService.saveAddress(addressVo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //    @RequiresPermissions("member:memberreceiveaddress:update")
    public R update(@RequestBody MemberReceiveAddressEntity memberReceiveAddress) {
        memberReceiveAddressService.updateById(memberReceiveAddress);

        return R.ok();
    }

    /**
     * 刪除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody AddressVo addressVo) {

        memberReceiveAddressService.removeAddress(addressVo);

        return R.ok();
    }

    /**
     * 設置預設
     */
    @RequestMapping("/default")
    public R setDefault(@RequestBody AddressVo addressVo) {

        memberReceiveAddressService.setDefault(addressVo);

        return R.ok();
    }

}
