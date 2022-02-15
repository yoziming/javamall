package com.yoziming.javamall.product.controller;

import com.alibaba.fastjson.JSONObject;
import com.yoziming.common.utils.PageUtils;
import com.yoziming.common.utils.R;
import com.yoziming.javamall.product.entity.SpuInfoEntity;
import com.yoziming.javamall.product.service.SpuInfoService;
import com.yoziming.javamall.product.vo.SpuSaveVo;
import com.yoziming.javamall.product.vo.SpuUpdateVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * spu訊息
 *
 * @author yoziming
 * @date 2022-01-20 15:38:28
 */
@Slf4j
@RestController
@RequestMapping("product/spuinfo")
public class SpuInfoController {
    @Autowired
    private SpuInfoService spuInfoService;

    @PostMapping(value = "/{spuId}/down")
    public R spuDown(@PathVariable("spuId") Long spuId) {

        spuInfoService.down(spuId);

        return R.ok();
    }

    @GetMapping("/skuId/{id}")
    public R getSpuInfoBySkuId(@PathVariable("id") Long skuId) {
        SpuInfoEntity entity = spuInfoService.getSpuInfoBySkuId(skuId);
        return R.ok().setData(entity);
    }

    //商品上架
    ///product/spuinfo/{spuId}/up
    @PostMapping(value = "/{spuId}/up")
    public R spuUp(@PathVariable("spuId") Long spuId) {

        spuInfoService.up(spuId);

        return R.ok();
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //    @RequiresPermissions("product:spuinfo:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = spuInfoService.queryPageByCondtion(params);

        return R.ok().put("page", page);
    }

    /**
     * 訊息
     */
    @RequestMapping("/info/{id}")
    //    @RequiresPermissions("product:spuinfo:info")
    public R info(@PathVariable("id") Long id) {
        //		SpuInfoEntity spuInfo = spuInfoService.getById(id);
        SpuUpdateVo spuSaveVo = spuInfoService.getSpu(id);
        return R.ok().put("spuInfo", spuSaveVo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:spuinfo:save")
    public R save(@RequestBody SpuSaveVo vo) {
        //spuInfoService.save(spuInfo);
        log.info("添加的spu：{}", JSONObject.toJSONString(vo));
        spuInfoService.saveSupInfo(vo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //    @RequiresPermissions("product:spuinfo:update")
    public R update(@RequestBody SpuInfoEntity spuInfo) {
        spuInfoService.updateById(spuInfo);

        return R.ok();
    }

    /**
     * 刪除
     */
    @RequestMapping("/delete")
    //    @RequiresPermissions("product:spuinfo:delete")
    public R delete(@RequestBody Long id) {
        //		spuInfoService.removeByIds(Arrays.asList(ids));
        spuInfoService.removeSpu(id);

        return R.ok();
    }

}
