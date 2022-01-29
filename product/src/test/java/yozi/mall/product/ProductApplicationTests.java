package yozi.mall.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import yozi.mall.product.dao.AttrGroupDao;
import yozi.mall.product.vo.SpuItemAttrGroupVo;

import java.util.List;

@SpringBootTest
class ProductApplicationTests {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    AttrGroupDao attrGroupDao;

    @Test
    void test1() {
        List<SpuItemAttrGroupVo> attrGroupWithAttrsBySpuId = attrGroupDao.getAttrGroupWithAttrsBySpuId(23L, 225L);
        System.out.println(attrGroupWithAttrsBySpuId);
    }
}
