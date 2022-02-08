package yoziming.mall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import yoziming.mall.common.utils.PageUtils;
import yoziming.mall.member.entity.IntegrationChangeHistoryEntity;

import java.util.Map;

/**
 * 積分變化歷史記錄
 *
 * @author yoziming
 * @email yoziming@gmail.com
 * @date 2022-01-11 21:42:05
 */
public interface IntegrationChangeHistoryService extends IService<IntegrationChangeHistoryEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

