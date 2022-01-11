package yozi.mall.member.dao;

import yozi.mall.member.entity.IntegrationChangeHistoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 積分變化歷史記錄
 * 
 * @author yozi
 * @email yoziming@gmail.com
 * @date 2022-01-11 21:42:05
 */
@Mapper
public interface IntegrationChangeHistoryDao extends BaseMapper<IntegrationChangeHistoryEntity> {
	
}
