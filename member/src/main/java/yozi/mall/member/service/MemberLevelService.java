package yozi.mall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import yozi.mall.common.utils.PageUtils;
import yozi.mall.member.entity.MemberLevelEntity;

import java.util.Map;

/**
 * 會員等級
 *
 * @author yozi
 * @email yoziming@gmail.com
 * @date 2022-01-11 21:42:05
 */
public interface MemberLevelService extends IService<MemberLevelEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

