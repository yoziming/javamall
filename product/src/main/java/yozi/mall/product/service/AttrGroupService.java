package yozi.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import yozi.mall.common.utils.PageUtils;
import yozi.mall.product.entity.AttrGroupEntity;

import java.util.Map;

/**
 * 屬性分組
 *
 * @author yozi
 * @email yoziming@gmail.com
 * @date 2022-01-11 20:06:30
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

