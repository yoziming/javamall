package yoziming.mall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import yoziming.mall.common.utils.PageUtils;
import yoziming.mall.ware.entity.WareInfoEntity;
import yoziming.mall.ware.vo.FareVo;

import java.util.Map;

/**
 * 倉庫信息
 *
 * @author yoziming
 * @email yoziming@gmail.com
 * @date 2022-01-11 21:44:25
 */
public interface WareInfoService extends IService<WareInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    FareVo getFare(Long addrId);
}

