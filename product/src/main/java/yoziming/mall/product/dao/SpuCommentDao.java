package yoziming.mall.product.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import yoziming.mall.product.entity.SpuCommentEntity;

/**
 * 商品評價
 *
 * @author yoziming
 * @email yoziming@gmail.com
 * @date 2022-01-11 20:06:30
 */
@Mapper
public interface SpuCommentDao extends BaseMapper<SpuCommentEntity> {

}
