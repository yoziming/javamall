package yozi.mall.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import yozi.mall.product.entity.BrandEntity;
import yozi.mall.product.service.impl.BrandServiceImpl;

import javax.annotation.Resource;

@SpringBootTest
class ProductApplicationTests {
    @Autowired
    BrandServiceImpl brandService;
    @Test
    void contextLoads() {
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setName("哈哈哈");
        System.out.println(brandService.save(brandEntity));
    }
}
