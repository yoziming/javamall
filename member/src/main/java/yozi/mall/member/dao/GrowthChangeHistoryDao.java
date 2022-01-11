package yozi.mall.member.dao;

import yozi.mall.member.entity.GrowthChangeHistoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 成長值變化歷史記錄
 * 
 * @author yozi
 * @email yoziming@gmail.com
 * @date 2022-01-11 21:42:05
 */
@Mapper
public interface GrowthChangeHistoryDao extends BaseMapper<GrowthChangeHistoryEntity> {
	
}
