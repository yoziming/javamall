package com.yoziming.javamall.member.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yoziming.javamall.member.entity.MemberReceiveAddressEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 會員收貨地址
 *
 * @author yoziming
 * @date 2022-01-20 17:30:33
 */
@Mapper
public interface MemberReceiveAddressDao extends BaseMapper<MemberReceiveAddressEntity> {

    boolean updateDefaultStatus(@PathVariable("memberId") Long memberId);

}
