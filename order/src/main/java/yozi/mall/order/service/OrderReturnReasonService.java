package yozi.mall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import yozi.mall.common.utils.PageUtils;
import yozi.mall.order.entity.OrderReturnReasonEntity;

import java.util.Map;

/**
 * 退貨原因
 *
 * @author yozi
 * @email yoziming@gmail.com
 * @date 2022-01-11 21:43:36
 */
public interface OrderReturnReasonService extends IService<OrderReturnReasonEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

