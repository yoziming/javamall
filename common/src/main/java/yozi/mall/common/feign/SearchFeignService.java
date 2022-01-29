package yozi.mall.common.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import yozi.mall.common.es.SkuEsModel;
import yozi.mall.common.utils.R;

import java.util.List;

@FeignClient("search")
public interface SearchFeignService {

    @PostMapping(value = "/search/save/product")
    R productStatusUp(@RequestBody List<SkuEsModel> skuEsModels);

}
