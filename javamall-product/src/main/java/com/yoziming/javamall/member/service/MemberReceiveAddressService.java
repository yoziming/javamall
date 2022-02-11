package com.yoziming.javamall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yoziming.common.utils.PageUtils;
import com.yoziming.javamall.member.entity.MemberReceiveAddressEntity;
import com.yoziming.javamall.member.vo.AddressVo;
import com.yoziming.javamall.ware.vo.FareVo;

import java.util.List;
import java.util.Map;

/**
 * 會員收貨地址
 *
 * @author yoziming
 * @date 2022-01-20 17:30:33
 */
public interface MemberReceiveAddressService extends IService<MemberReceiveAddressEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<MemberReceiveAddressEntity> getAddress(Long memberId);

    void saveAddress(AddressVo addressVo);

    void setDefault(AddressVo addressVo);

    void removeAddress(AddressVo addressVo);

    FareVo getFare(Long addrId);
}

