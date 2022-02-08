package yoziming.mall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import yoziming.mall.common.utils.PageUtils;
import yoziming.mall.order.entity.UndoLogEntity;

import java.util.Map;

/**
 * @author yoziming
 * @email yoziming@gmail.com
 * @date 2022-01-11 21:43:36
 */
public interface UndoLogService extends IService<UndoLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

