package yoziming.mall.ware.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yoziming.mall.common.exception.NoStockException;
import yoziming.mall.common.to.SkuHasStockTo;
import yoziming.mall.common.utils.PageUtils;
import yoziming.mall.common.utils.R;
import yoziming.mall.ware.entity.WareSkuEntity;
import yoziming.mall.ware.service.WareSkuService;
import yoziming.mall.ware.vo.WareSkuLockVo;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static yoziming.mall.common.exception.BizCodeEnum.NO_STOCK_EXCEPTION;

/**
 * 商品庫存
 *
 * @author yoziming
 * @email yoziming@gmail.com
 * @date 2022-01-11 21:44:25
 */
@RestController
@RequestMapping("ware/waresku")
public class WareSkuController {
    @Autowired
    private WareSkuService wareSkuService;

    @PostMapping("/hasStock")
    public R skuHasStock(@RequestBody List<Long> ids) {
        List<SkuHasStockTo> list = wareSkuService.getSkuHasStock(ids);
        return R.ok().setData(list);
    }

    /**
     * 鎖定庫存
     * 庫存解鎖的場景
     * 1）、下訂單成功，訂單過期沒有支付被系統自動取消或者被用戶手動取消，都要解鎖庫存
     * 2）、下訂單成功，庫存鎖定成功，接下來的業務調用失敗，導致訂單回滾。之前鎖定的庫存就要自動解鎖
     */
    @PostMapping(value = "/lock/order")
    public R orderLockStock(@RequestBody WareSkuLockVo vo) {

        try {
            boolean lockStock = wareSkuService.orderLockStock(vo);
            return R.ok().setData(lockStock);
        } catch (NoStockException e) {
            return R.error(NO_STOCK_EXCEPTION.getCode(), NO_STOCK_EXCEPTION.getMessage());
        }
    }

    /**
     * 列表
     */
    @RequestMapping("/list")

    //@RequiresPermissions("ware:waresku:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = wareSkuService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("ware:waresku:info")
    public R info(@PathVariable("id") Long id) {
        WareSkuEntity wareSku = wareSkuService.getById(id);

        return R.ok().put("wareSku", wareSku);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("ware:waresku:save")
    public R save(@RequestBody WareSkuEntity wareSku) {
        wareSkuService.save(wareSku);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("ware:waresku:update")
    public R update(@RequestBody WareSkuEntity wareSku) {
        wareSkuService.updateById(wareSku);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("ware:waresku:delete")
    public R delete(@RequestBody Long[] ids) {
        wareSkuService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
