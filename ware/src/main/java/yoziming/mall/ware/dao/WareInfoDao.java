package yoziming.mall.ware.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import yoziming.mall.ware.entity.WareInfoEntity;

/**
 * 倉庫信息
 *
 * @author yoziming
 * @email yoziming@gmail.com
 * @date 2022-01-11 21:44:25
 */
@Mapper
public interface WareInfoDao extends BaseMapper<WareInfoEntity> {

}
