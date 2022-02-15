package yoziming.mall.order.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import yoziming.mall.order.entity.UndoLogEntity;

/**
 * @author yoziming
 * @email yoziming@gmail.com
 * @date 2022-01-11 21:43:36
 */
@Mapper
public interface UndoLogDao extends BaseMapper<UndoLogEntity> {

}