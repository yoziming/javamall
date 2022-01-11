package yozi.mall.ware.dao;

import yozi.mall.ware.entity.WareInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 倉庫信息
 * 
 * @author yozi
 * @email yoziming@gmail.com
 * @date 2022-01-11 21:44:25
 */
@Mapper
public interface WareInfoDao extends BaseMapper<WareInfoEntity> {
	
}
