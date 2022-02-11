package com.yoziming.javamall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yoziming.common.to.Category2Vo;
import com.yoziming.common.to.Category3Vo;
import com.yoziming.common.to.CategoryVo;
import com.yoziming.common.utils.PageUtils;
import com.yoziming.common.utils.Query;
import com.yoziming.javamall.product.dao.CategoryDao;
import com.yoziming.javamall.product.entity.CategoryEntity;
import com.yoziming.javamall.product.service.CategoryBrandRelationService;
import com.yoziming.javamall.product.service.CategoryService;
import com.yoziming.javamall.product.vo.Catelog2Vo;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;
    @Autowired
    CategoryDao categoryDao;
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        List<CategoryEntity> categoryEntities = baseMapper.selectList(null);
        List<CategoryEntity> levelMenus = categoryEntities.stream()
                .filter(e -> e.getParentCid() == 0)
                .map((menu) -> {
                    menu.setChildren(getChildrens(menu, categoryEntities));
                    return menu;
                })
                .sorted((menu, menu2) -> {
                    return (menu.getSort() == null ? 0 : menu.getSort()) - (menu2.getSort() == null ? 0 :
                            menu2.getSort());
                })
                .collect(Collectors.toList());

        return levelMenus;
    }

    @Override
    public Long[] findCatelogPath(Long catelogId) {

        List<Long> paths = new ArrayList<>();

        //遞歸查詢是否還有父節點
        List<Long> parentPath = findParentPath(catelogId, paths);

        //進行一個逆序排列
        Collections.reverse(parentPath);

        return (Long[]) parentPath.toArray(new Long[parentPath.size()]);
    }

    /**
     * 級聯更新所有關聯的數據
     *
     * @param category
     * @CacheEvict:失效模式
     * @CachePut:雙寫模式，需要有返回值 1、同時進行多種緩存操作：@Caching
     * 2、指定刪除某個分區下的所有數據 @CacheEvict(value = "category",allEntries = true)
     * 3、存儲同一類型的數據，都可以指定為同一分區
     */
    @CacheEvict(value = "category", allEntries = true)       //刪除某個分區下的所有數據
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateCascade(CategoryEntity category) {
        RReadWriteLock readWriteLock = redissonClient.getReadWriteLock("catalogJson-lock");
        //創建寫鎖
        RLock rLock = readWriteLock.writeLock();

        try {
            rLock.lock();
            this.baseMapper.updateById(category);
            categoryBrandRelationService.updateCategory(category.getCatId(), category.getName());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rLock.unlock();
        }
    }

    @Override
    public List<CategoryEntity> getCategoryByIds(Long catIds) {
        CategoryEntity categoryEntity3 = baseMapper.selectById(catIds);
        CategoryEntity categoryEntity2 = baseMapper.selectById(categoryEntity3.getParentCid());
        CategoryEntity categoryEntity1 = baseMapper.selectById(categoryEntity2.getParentCid());
        return Arrays.asList(categoryEntity1, categoryEntity2, categoryEntity3);
    }

    @Cacheable(value = {"category"}, key = "#root.method.name", sync = true)
    @Override
    public List<CategoryEntity> getLevel1Categorys() {
        List<CategoryEntity> categoryEntities = this.baseMapper.selectList(
                new QueryWrapper<CategoryEntity>().eq("parent_cid", 0));
        return categoryEntities;
    }

    /**
     * 首頁獲取分類二級json
     *
     * @return
     */
    @Cacheable(value = "category", key = "#root.methodName")
    @Override
    public Map<String, List<Catelog2Vo>> getCatalogJson() {
        //將數據庫的多次查詢變為一次
        List<CategoryEntity> selectList = this.baseMapper.selectList(null);

        //1、查出所有分類
        //1、1）查出所有一級分類
        List<CategoryEntity> level1Categorys = getParent_cid(selectList, 0L);

        //封裝數據
        Map<String, List<Catelog2Vo>> parentCid =
                level1Categorys.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {
            //1、每一個的一級分類,查到這個一級分類的二級分類
            List<CategoryEntity> categoryEntities = getParent_cid(selectList, v.getCatId());

            //2、封裝上面的結果
            List<Catelog2Vo> catelog2Vos = null;
            if (categoryEntities != null) {
                catelog2Vos = categoryEntities.stream().map(l2 -> {
                    Catelog2Vo catelog2Vo = new Catelog2Vo(v.getCatId().toString(), null, l2.getCatId().toString(),
                            l2.getName().toString());

                    //1、找當前二級分類的三級分類封裝成vo
                    List<CategoryEntity> level3Catelog = getParent_cid(selectList, l2.getCatId());

                    if (level3Catelog != null) {
                        List<Catelog2Vo.Category3Vo> category3Vos = level3Catelog.stream().map(l3 -> {
                            //2、封裝成指定格式
                            Catelog2Vo.Category3Vo category3Vo = new Catelog2Vo.Category3Vo(l2.getCatId().toString(),
                                    l3.getCatId().toString(), l3.getName());

                            return category3Vo;
                        }).collect(Collectors.toList());
                        catelog2Vo.setCatalog3List(category3Vos);
                    }

                    return catelog2Vo;
                }).collect(Collectors.toList());
            }

            return catelog2Vos;
        }));

        return parentCid;
    }

    @Cacheable(value = "category2", key = "#root.methodName")
    @Override
    public List<CategoryVo> getCategory() {
        List<CategoryEntity> selectList = this.baseMapper.selectList(null);
        List<CategoryEntity> level1Categorys = getParent_cid(selectList, 0L);
        List<CategoryVo> categoryVos = level1Categorys.stream().map(cat1 -> {
            List<CategoryEntity> level2Categorys = getParent_cid(selectList, cat1.getCatId());
            List<Category2Vo> category2Vos = level2Categorys.stream().map(cat2 -> {
                List<CategoryEntity> level3Categorys = getParent_cid(selectList, cat2.getCatId());
                List<Category3Vo> category3Vos = level3Categorys.stream().map(cat3 -> {
                    Category3Vo category3Vo = new Category3Vo();
                    category3Vo.setId(cat3.getCatId());
                    category3Vo.setName(cat3.getName());
                    return category3Vo;
                }).collect(Collectors.toList());
                Category2Vo category2Vo = new Category2Vo();
                category2Vo.setId(cat2.getCatId());
                category2Vo.setName(cat2.getName());
                category2Vo.setCat3(category3Vos);
                return category2Vo;
            }).collect(Collectors.toList());
            CategoryVo categoryVo = new CategoryVo();
            categoryVo.setId(cat1.getCatId());
            categoryVo.setName(cat1.getName());
            categoryVo.setCat2(category2Vos);
            return categoryVo;
        }).collect(Collectors.toList());
        return categoryVos;
    }

    //遞歸查找所有菜單的子菜單
    private List<CategoryEntity> getChildrens(CategoryEntity root, List<CategoryEntity> all) {

        List<CategoryEntity> children = all.stream().filter(categoryEntity -> {
            return categoryEntity.getParentCid().equals(root.getCatId());
        }).map(categoryEntity -> {
            //1、找到子菜單(遞歸)
            categoryEntity.setChildren(getChildrens(categoryEntity, all));
            return categoryEntity;
        }).sorted((menu, menu2) -> {
            //2、菜單的排序
            return (menu.getSort() == null ? 0 : menu.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
        }).collect(Collectors.toList());

        return children;
    }

    private List<Long> findParentPath(Long catelogId, List<Long> paths) {

        //1、收集當前節點id
        paths.add(catelogId);

        //根據當前分類id查詢訊息
        CategoryEntity byId = this.getById(catelogId);
        //如果當前不是父分類
        if (byId.getParentCid() != 0) {
            findParentPath(byId.getParentCid(), paths);
        }

        return paths;
    }

    private List<CategoryEntity> getParent_cid(List<CategoryEntity> selectList, Long parentCid) {
        List<CategoryEntity> categoryEntities =
                selectList.stream().filter(item -> item.getParentCid().equals(parentCid)).collect(Collectors.toList());
        return categoryEntities;
        // return this.baseMapper.selectList(
        //         new QueryWrapper<CategoryEntity>().eq("parent_cid", parentCid));
    }
}