package yoziming.mall.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yoziming.mall.common.utils.PageUtils;
import yoziming.mall.common.utils.Query;
import yoziming.mall.product.dao.CategoryDao;
import yoziming.mall.product.entity.CategoryEntity;
import yoziming.mall.product.service.CategoryBrandRelationService;
import yoziming.mall.product.service.CategoryService;
import yoziming.mall.product.vo.Catalogs2Vo;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    CategoryBrandRelationService categoryBrandRelationService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedissonClient redisson;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 三級樹型列表
     *
     * @return
     */
    @Override
    public List<CategoryEntity> listTree() {
        // 查所有分類
        List<CategoryEntity> entities = baseMapper.selectList(null);
        // lamdba表達式左邊形參，右邊方法
        List<CategoryEntity> levelMenus = entities.stream()
                .filter(e -> e.getParentCid() == 0) // 先篩出第一級，set他的子級，調用下面的方法
                .map((menu) -> {
                    menu.setChildren(findChildren(menu, entities));
                    return menu;
                })
                .sorted(Comparator.comparingInt(menu -> (menu.getSort() == null ? 0 : menu.getSort())))
                .collect(Collectors.toList());

        return levelMenus;
    }

    @Override
    public void removeMenuByIds(List<Long> asList) {
        baseMapper.deleteBatchIds(asList);
    }

    /**
     * 找出所有子級並賦上
     *
     * @param root
     * @param all
     * @return
     */
    private List<CategoryEntity> findChildren(CategoryEntity root, List<CategoryEntity> all) {
        // all是全部的品項
        List<CategoryEntity> children = all.stream().filter(categoryEntity -> {
            return categoryEntity.getParentCid().equals(root.getCatId());
        }).map(categoryEntity -> {
            // 找到子菜單(遞歸)
            categoryEntity.setChildren(findChildren(categoryEntity, all));
            return categoryEntity;
        }).sorted((menu, menu2) -> {
            // 菜單的排序
            return (menu.getSort() == null ? 0 : menu.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
        }).collect(Collectors.toList());

        return children;
    }

    /**
     * 找出商品屬性所在的路徑(電器-大家電-電視)
     *
     * @param catalogId
     * @return
     */
    @Override
    public Long[] findCatalogPath(Long catalogId) {
        List<Long> paths = new ArrayList<>();
        List<Long> parentPath = findParentPath(catalogId, paths);
        // 找的是從子出發，所以還要逆轉
        Collections.reverse(parentPath);
        return parentPath.toArray(new Long[parentPath.size()]);
    }

    /**
     * 級聯更新所有關聯的資料，更新了表，所以刪除快取
     *
     * @param category
     */
    @CacheEvict(cacheNames = "category", allEntries = true)
    @Transactional
    @Override
    public void updateCascade(CategoryEntity category) {
        this.updateById(category);
        categoryBrandRelationService.updateCategory(category.getCatId(), category.getName());
    }

    @Cacheable(value = "category", key = "#root.method.name", sync = true)
    @Override
    public List<CategoryEntity> getLevel1Catalog() {
        return this.list(new QueryWrapper<CategoryEntity>().eq("parent_cid", 0));
    }

    // 從redis獲取所有分類
    @Cacheable(value = "category", key = "#root.method.name")
    @Override
    public Map<String, List<Catalogs2Vo>> getCatalogJson() {
        // 先去緩存拿
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String catalogJson = ops.get("catalogJson");
        if (StringUtils.isBlank(catalogJson)) {
            // 緩存沒資料，去DB拿，且存到緩存中
            Map<String, List<Catalogs2Vo>> catalogJsonFromDb = this.getCatalogJsonFromDb();
            // 為了方便統一交流，redis放的都是JSON序列化後的
            String s = JSON.toJSONString(catalogJsonFromDb);
            // 設定過期時間
            ops.set("catalogJson", s, 10, TimeUnit.MINUTES);
            return catalogJsonFromDb;
        }
        // 把取出的JSONstring反序列化，使用TypeReference指名泛型
        Map<String, List<Catalogs2Vo>> stringListMap = JSON.parseObject(catalogJson, new TypeReference<Map<String,
                List<Catalogs2Vo>>>() {
        });
        return stringListMap;
    }

    // Redisson分布式鎖
    public Map<String, List<Catalogs2Vo>> getCatalogJsonWithRedisson() {
        // 搶鎖
        RLock lock = redisson.getLock("CatalogJson-lock");
        // 搶到了，開始執行業務
        lock.lock();
        Map<String, List<Catalogs2Vo>> result;
        try {
            result = getCatalogJsonFromDb();
        } finally {
            lock.unlock();
        }
        return result;
    }

    // 手動分布式鎖
    public Map<String, List<Catalogs2Vo>> getCatalogJsonFromRedisLock() {
        String uuid = UUID.randomUUID().toString();
        Boolean lock = stringRedisTemplate.opsForValue().setIfAbsent("lock", uuid, 30, TimeUnit.SECONDS);
        if (lock) {
            // 能放到值，相當於搶到鎖了，執行業務，並且走之前要刪掉值
            try {
                return getCatalogJsonFromDb();
            } finally {
                // 刪除鎖的lua腳本，不用太在意複製貼上就好
                String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else" +
                        " " +
                        "return 0 end";
                stringRedisTemplate.execute(new DefaultRedisScript<Long>(script, Long.class), Arrays.asList("lock"),
                        uuid);
            }
        } else {
            // 沒搶到，等會重試
            // 等待一段時間
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return getCatalogJsonFromRedisLock(); // 自旋
        }
    }

    // 從DB獲取所有分類，且二、三級分類以封裝好
    public Map<String, List<Catalogs2Vo>> getCatalogJsonFromDb() {

        // 雙重判斷，拿到鎖也不急著查DB，先看看緩存
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String catalogJson = ops.get("catalogJson");
        if (!StringUtils.isBlank(catalogJson)) {
            // 緩存有了，拿緩存的就滾吧
            return JSON.parseObject(catalogJson,
                    new TypeReference<Map<String,
                            List<Catalogs2Vo>>>() {
                    });
        }
        // 查庫，放緩存的動作

        // 性能優化：將數據庫的多次查詢變為一次
        List<CategoryEntity> selectList = this.baseMapper.selectList(null);

        // 查出所有一級分類
        List<CategoryEntity> level1Categories = getLevel1Catalog();

        // 封裝數據
        Map<String, List<Catalogs2Vo>> parentCid =
                level1Categories.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {
                    // 1、每一個的一級分類,查到這個一級分類的二級分類
                    List<CategoryEntity> categoryEntities = getParentCid(selectList, v.getCatId());

                    // 2、封裝上面的結果
                    List<Catalogs2Vo> catalogs2Vos = null;
                    if (categoryEntities != null) {
                        catalogs2Vos = categoryEntities.stream().map(l2 -> {
                            Catalogs2Vo catalogs2Vo = new Catalogs2Vo(v.getCatId().toString(), null,
                                    l2.getCatId().toString()
                                    , l2.getName().toString());

                            // 1、找當前二級分類的三級分類封裝成vo
                            List<CategoryEntity> level3catalog = getParentCid(selectList, l2.getCatId());

                            if (level3catalog != null) {
                                List<Catalogs2Vo.Category3Vo> category3Vos = level3catalog.stream().map(l3 -> {
                                    //2、封裝成指定格式
                                    Catalogs2Vo.Category3Vo category3Vo =
                                            new Catalogs2Vo.Category3Vo(l2.getCatId().toString(),
                                                    l3.getCatId().toString(),
                                                    l3.getName());

                                    return category3Vo;
                                }).collect(Collectors.toList());
                                catalogs2Vo.setCatalog3List(category3Vos);
                            }
                            return catalogs2Vo;
                        }).collect(Collectors.toList());
                    }
                    return catalogs2Vos;
                }));
        return parentCid;
    }

    private List<CategoryEntity> getParentCid(List<CategoryEntity> selectList, Long parentCid) {
        return selectList.stream().filter(item -> item.getParentCid().equals(parentCid)).collect(Collectors.toList());
    }

    /**
     * 找出所有父path
     *
     * @param catalogId
     * @param paths
     * @return
     */
    private List<Long> findParentPath(Long catalogId, List<Long> paths) {
        // 先放進當前的
        paths.add(catalogId);
        CategoryEntity byId = this.getById(catalogId);
        if (byId.getParentCid() != 0) {
            // 遞歸搜
            findParentPath(byId.getParentCid(), paths);
        }
        return paths;
    }
}