package com.yoziming.javamall.member.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yoziming.javamall.member.entity.MemberEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 會員
 *
 * @author yoziming
 * @date 2022-01-20 17:30:33
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {

}
