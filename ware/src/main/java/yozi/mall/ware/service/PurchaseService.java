package yozi.mall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import yozi.mall.common.utils.PageUtils;
import yozi.mall.ware.entity.PurchaseEntity;

import java.util.Map;

/**
 * 採購信息
 *
 * @author yozi
 * @email yoziming@gmail.com
 * @date 2022-01-11 21:44:25
 */
public interface PurchaseService extends IService<PurchaseEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

