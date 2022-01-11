package yozi.mall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import yozi.mall.common.utils.PageUtils;
import yozi.mall.member.entity.GrowthChangeHistoryEntity;

import java.util.Map;

/**
 * 成長值變化歷史記錄
 *
 * @author yozi
 * @email yoziming@gmail.com
 * @date 2022-01-11 21:42:05
 */
public interface GrowthChangeHistoryService extends IService<GrowthChangeHistoryEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

