package yozi.mall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import yozi.mall.common.utils.PageUtils;
import yozi.mall.ware.entity.WareInfoEntity;
import yozi.mall.ware.vo.FareVo;

import java.util.Map;

/**
 * 倉庫信息
 *
 * @author yozi
 * @email yoziming@gmail.com
 * @date 2022-01-11 21:44:25
 */
public interface WareInfoService extends IService<WareInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    FareVo getFare(Long addrId);
}

