package yoziming.mall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import yoziming.mall.common.utils.PageUtils;
import yoziming.mall.member.entity.GrowthChangeHistoryEntity;

import java.util.Map;

/**
 * 成長值變化歷史記錄
 *
 * @author yoziming
 * @email yoziming@gmail.com
 * @date 2022-01-11 21:42:05
 */
public interface GrowthChangeHistoryService extends IService<GrowthChangeHistoryEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

