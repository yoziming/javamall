package com.yoziming.javamall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yoziming.common.utils.PageUtils;
import com.yoziming.javamall.order.entity.RefundInfoEntity;

import java.util.Map;

/**
 * 退款訊息
 *
 * @author yoziming
 * @date 2022-01-20 17:38:15
 */
public interface RefundInfoService extends IService<RefundInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

