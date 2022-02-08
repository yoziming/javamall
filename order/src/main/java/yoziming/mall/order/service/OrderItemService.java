package yoziming.mall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import yoziming.mall.common.utils.PageUtils;
import yoziming.mall.order.entity.OrderItemEntity;

import java.util.Map;

/**
 * 訂單項信息
 *
 * @author yoziming
 * @email yoziming@gmail.com
 * @date 2022-01-11 21:43:36
 */
public interface OrderItemService extends IService<OrderItemEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

