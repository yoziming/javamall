package yozi.mall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import yozi.mall.common.utils.PageUtils;
import yozi.mall.order.entity.OrderReturnApplyEntity;

import java.util.Map;

/**
 * 訂單退貨申請
 *
 * @author yozi
 * @email yoziming@gmail.com
 * @date 2022-01-11 21:43:36
 */
public interface OrderReturnApplyService extends IService<OrderReturnApplyEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

