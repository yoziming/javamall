package com.yoziming.javamall.order.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yoziming.javamall.order.entity.OrderReturnApplyEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 訂單退貨申請
 *
 * @author yoziming
 * @date 2022-01-20 17:38:15
 */
@Mapper
public interface OrderReturnApplyDao extends BaseMapper<OrderReturnApplyEntity> {

}
