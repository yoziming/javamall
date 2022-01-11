package yozi.mall.order.dao;

import yozi.mall.order.entity.UndoLogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * 
 * @author yozi
 * @email yoziming@gmail.com
 * @date 2022-01-11 21:43:36
 */
@Mapper
public interface UndoLogDao extends BaseMapper<UndoLogEntity> {
	
}
