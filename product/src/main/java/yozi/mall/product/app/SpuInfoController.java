package yozi.mall.product.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yozi.mall.common.to.SpuInfoTo;
import yozi.mall.common.utils.PageUtils;
import yozi.mall.common.utils.R;
import yozi.mall.product.entity.SpuInfoEntity;
import yozi.mall.product.service.SpuInfoService;
import yozi.mall.product.vo.SpuSaveVo;

import java.util.Arrays;
import java.util.Map;

/**
 * spu信息
 *
 * @author yozi
 * @email yoziming@gmail.com
 * @date 2022-01-11 20:06:30
 */
@RestController
@RequestMapping("product/spuinfo")
public class SpuInfoController {
    @Autowired
    private SpuInfoService spuInfoService;

    // 上架
    @PostMapping("/{spuId}/up")
    public R spuUp(@PathVariable Long spuId) {
        spuInfoService.up(spuId);
        return R.ok();
    }

    /**
     * 提交訂單，遠程接口
     * 根據skuId查詢spu的信息
     */
    @GetMapping(value = "/skuId/{skuId}")
    public R getSpuInfoBySkuId(@PathVariable("skuId") Long skuId) {

        SpuInfoTo spuInfoEntity = spuInfoService.getSpuInfoBySkuId(skuId);

        return R.ok().setData(spuInfoEntity);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = spuInfoService.queryPageByKeyword(params);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("product:spuinfo:info")
    public R info(@PathVariable("id") Long id) {
        SpuInfoEntity spuInfo = spuInfoService.getById(id);

        return R.ok().put("spuInfo", spuInfo);
    }

    /**
     * 保存SpuSaveVo
     */
    @RequestMapping("/save")
    public R save(@RequestBody SpuSaveVo vo) {
        spuInfoService.saveSpuInfo(vo);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:spuinfo:update")
    public R update(@RequestBody SpuInfoEntity spuInfo) {
        spuInfoService.updateById(spuInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:spuinfo:delete")
    public R delete(@RequestBody Long[] ids) {
        spuInfoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
