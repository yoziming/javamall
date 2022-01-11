package yozi.mall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import yozi.mall.common.utils.PageUtils;
import yozi.mall.ware.entity.WareOrderTaskDetailEntity;

import java.util.Map;

/**
 * 庫存工作單
 *
 * @author yozi
 * @email yoziming@gmail.com
 * @date 2022-01-11 21:44:25
 */
public interface WareOrderTaskDetailService extends IService<WareOrderTaskDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

