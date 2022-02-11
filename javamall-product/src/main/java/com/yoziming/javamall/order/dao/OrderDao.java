package com.yoziming.javamall.order.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yoziming.javamall.order.entity.OrderEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 訂單
 *
 * @author yoziming
 * @date 2022-01-20 17:38:15
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {

    void updateOrderStatus(@Param("outTradeNo") String outTradeNo, @Param("code") Integer code);
}
