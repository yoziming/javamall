package yozi.mall.product.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yozi.mall.common.utils.PageUtils;
import yozi.mall.common.utils.R;
import yozi.mall.product.entity.ProductAttrValueEntity;
import yozi.mall.product.service.AttrService;
import yozi.mall.product.service.ProductAttrValueService;
import yozi.mall.product.vo.AttrRespVo;
import yozi.mall.product.vo.AttrVo;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 商品屬性
 *
 * @author yozi
 * @email yoziming@gmail.com
 * @date 2022-01-11 20:06:30
 */
@RestController
@RequestMapping("product/attr")
public class AttrController {
    @Autowired
    private AttrService attrService;

    @Autowired
    ProductAttrValueService productAttrValueService;

    // 獲取spu規格
    @GetMapping("/base/listforspu/{spuId}")
    public R baseAttrlistforspu(@PathVariable("spuId") Long spuId) {
        List<ProductAttrValueEntity> entities = productAttrValueService.baseAttrListforspu(spuId);
        return R.ok().put("data", entities);
    }

    // 修改spu
    @PostMapping("/update/{spuId}")
    public R updateSpuAttr(@PathVariable("spuId") Long spuId,
                           @RequestBody List<ProductAttrValueEntity> entities) {
        productAttrValueService.updateSpuAttr(spuId, entities);
        return R.ok();
    }

    /**
     * 規格參數列表
     */
    @GetMapping("/{attrType}/list/{catalogId}")
    //@RequiresPermissions("product:attr:list")
    public R baseAttrList(@RequestParam Map<String, Object> params,
                          @PathVariable("catalogId") Long catalogId,
                          @PathVariable("attrType") String attrType
    ) {
        PageUtils page = attrService.queryBaseAttrPage(params, catalogId, attrType);
        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:attr:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = attrService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{attrId}")
    //@RequiresPermissions("product:attr:info")
    public R info(@PathVariable("attrId") Long attrId) {
        // AttrEntity attr = attrService.getById(attrId);
        AttrRespVo respVo = attrService.getAttrById(attrId);
        return R.ok().put("attr", respVo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:attr:save")
    public R save(@RequestBody AttrVo attr) {
        attrService.saveAttr(attr);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:attr:update")
    public R update(@RequestBody AttrVo attr) {
        attrService.updateAttr(attr);

        return R.ok();
    }

    /**
     * 刪除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:attr:delete")
    public R delete(@RequestBody Long[] attrIds) {
        attrService.removeAttrAndRelationByIds(Arrays.asList(attrIds));
        return R.ok();
    }

}
