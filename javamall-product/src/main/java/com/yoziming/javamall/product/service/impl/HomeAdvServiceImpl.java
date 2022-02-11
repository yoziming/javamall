package com.yoziming.javamall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yoziming.common.utils.PageUtils;
import com.yoziming.common.utils.Query;
import com.yoziming.javamall.product.dao.HomeAdvDao;
import com.yoziming.javamall.product.entity.HomeAdvEntity;
import com.yoziming.javamall.product.service.HomeAdvService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service("homeAdvService")
public class HomeAdvServiceImpl extends ServiceImpl<HomeAdvDao, HomeAdvEntity> implements HomeAdvService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<HomeAdvEntity> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(params.get("type"))) {
            wrapper.eq("type", params.get("type"));
        }
        IPage<HomeAdvEntity> page = this.page(
                new Query<HomeAdvEntity>().getPage(params),
                wrapper
        );
        log.info("首頁運營位={}", page.getRecords());
        return new PageUtils(page);
    }

    @Override
    public List<HomeAdvEntity> getHomeAdv() {

        Page<HomeAdvEntity> page = new Page<>(1, 4, 4, false);
        QueryWrapper<HomeAdvEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 1).orderByDesc("sort");
        return this.page(page, queryWrapper).getRecords();
    }

    @Override
    public List<List<HomeAdvEntity>> getIndexAdv() {
        List<List<HomeAdvEntity>> result = new ArrayList<>();
        QueryWrapper<HomeAdvEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 1);
        List<HomeAdvEntity> homeAdvEntities = baseMapper.selectList(queryWrapper);
        // 輪播圖
        List<HomeAdvEntity> adv1 = homeAdvEntities.stream().filter(item -> {
            if (1 == item.getType()) {
                return true;
            }
            return false;
        }).sorted(((o1, o2) -> {
            return o1.getSort().compareTo(o2.getSort());
        })).limit(4).collect(Collectors.toList());
        // 品牌工廠
        List<HomeAdvEntity> adv2 = homeAdvEntities.stream().filter(item -> {
            if (2 == item.getType()) {
                return true;
            }
            return false;
        }).sorted(((o1, o2) -> {
            return o1.getSort().compareTo(o2.getSort());
        })).limit(3).collect(Collectors.toList());
        // 新品
        List<HomeAdvEntity> adv3 = homeAdvEntities.stream().filter(item -> {
            if (3 == item.getType()) {
                return true;
            }
            return false;
        }).sorted(((o1, o2) -> {
            return o1.getSort().compareTo(o2.getSort());
        })).limit(4).collect(Collectors.toList());
        result.add(adv1);
        result.add(adv2);
        result.add(adv3);
        return result;
    }

}