package yoziming.mall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import yoziming.mall.common.utils.PageUtils;
import yoziming.mall.member.entity.MemberLevelEntity;

import java.util.Map;

/**
 * 會員等級
 *
 * @author yoziming
 * @email yoziming@gmail.com
 * @date 2022-01-11 21:42:05
 */
public interface MemberLevelService extends IService<MemberLevelEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

