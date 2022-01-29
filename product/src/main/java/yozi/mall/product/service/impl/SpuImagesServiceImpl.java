package yozi.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import yozi.mall.common.utils.PageUtils;
import yozi.mall.common.utils.Query;
import yozi.mall.product.dao.SpuImagesDao;
import yozi.mall.product.entity.SpuImagesEntity;
import yozi.mall.product.service.SpuImagesService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("spuImagesService")
public class SpuImagesServiceImpl extends ServiceImpl<SpuImagesDao, SpuImagesEntity> implements SpuImagesService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuImagesEntity> page = this.page(
                new Query<SpuImagesEntity>().getPage(params),
                new QueryWrapper<SpuImagesEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveImages(Long id, List<String> images) {
        if (images != null && images.size() > 0) {
            // 要存成多條紀錄
            List<SpuImagesEntity> collect = images.stream().map(e -> {
                SpuImagesEntity spuImagesEntity = new SpuImagesEntity();
                spuImagesEntity.setSpuId(id);
                spuImagesEntity.setImgUrl(e);
                return spuImagesEntity;
            }).collect(Collectors.toList());
            this.saveBatch(collect);
        }
    }
}