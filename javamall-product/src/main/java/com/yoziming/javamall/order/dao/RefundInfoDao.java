package com.yoziming.javamall.order.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yoziming.javamall.order.entity.RefundInfoEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 退款訊息
 *
 * @author yoziming
 * @date 2022-01-20 17:38:15
 */
@Mapper
public interface RefundInfoDao extends BaseMapper<RefundInfoEntity> {

}
