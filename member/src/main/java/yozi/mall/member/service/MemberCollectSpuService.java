package yozi.mall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import yozi.mall.common.utils.PageUtils;
import yozi.mall.member.entity.MemberCollectSpuEntity;

import java.util.Map;

/**
 * 會員收藏的商品
 *
 * @author yozi
 * @email yoziming@gmail.com
 * @date 2022-01-11 21:42:05
 */
public interface MemberCollectSpuService extends IService<MemberCollectSpuEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

