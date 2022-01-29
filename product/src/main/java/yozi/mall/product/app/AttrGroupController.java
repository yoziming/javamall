package yozi.mall.product.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yozi.mall.common.utils.PageUtils;
import yozi.mall.common.utils.R;
import yozi.mall.product.entity.AttrEntity;
import yozi.mall.product.entity.AttrGroupEntity;
import yozi.mall.product.service.AttrAttrgroupRelationService;
import yozi.mall.product.service.AttrGroupService;
import yozi.mall.product.service.AttrService;
import yozi.mall.product.service.CategoryService;
import yozi.mall.product.vo.AttrGroupRelationVo;
import yozi.mall.product.vo.AttrGroupWithAttrsVo;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 屬性分組
 *
 * @author yozi
 * @email yoziming@gmail.com
 * @date 2022-01-11 20:06:30
 */
@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AttrService attrService;

    @Autowired
    private AttrAttrgroupRelationService relationService;

    /**
     * 根據分組ID查找關聯
     *
     * @param attrgroupId
     * @return
     */
    @GetMapping("/{attrgroupId}/attr/relation")
    public R attrRelation(@PathVariable("attrgroupId") Long attrgroupId) {
        List<AttrEntity> entities = attrService.getAttrRelation(attrgroupId);
        return R.ok().put("data", entities);
    }

    /**
     * 根據分組ID查找無關聯(新增時逆向篩選用)
     */
    @GetMapping("/{attrgroupId}/noattr/relation")
    public R attrNoRelation(@RequestParam Map<String, Object> params,
                            @PathVariable("attrgroupId") Long attrgroupId) {
        PageUtils page = attrService.getAttrNoRelation(params, attrgroupId);

        return R.ok().put("page", page);

    }

    /**
     * 列表
     */
    @RequestMapping("/list/{catalogId}")
    //@RequiresPermissions("product:attr:list")
    public R list(@RequestParam Map<String, Object> params,
                  @PathVariable("catalogId") Long catalogId) {
        PageUtils page = attrGroupService.queryPageCatalogId(params, catalogId);

        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:attrgroup:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = attrGroupService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
    //@RequiresPermissions("product:attrgroup:info")
    public R info(@PathVariable("attrGroupId") Long attrGroupId) {
        AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);
        Long catalogId = attrGroup.getCatalogId();
        // 找出商品屬性所在的路徑(電器-大家電-電視)
        Long[] path = categoryService.findCatalogPath(catalogId);
        attrGroup.setCatalogPath(path);

        return R.ok().put("attrGroup", attrGroup);
    }

    /**
     * 新增關聯保存
     */
    @RequestMapping("/attr/relation")
    //@RequiresPermissions("product:attrgroup:save")
    public R addAttrGroupRelation(@RequestBody List<AttrGroupRelationVo> vos) {
        relationService.addAttrGroupRelation(vos);

        return R.ok();
    }

    /**
     * 新增商品獲取規格參數
     */
    @GetMapping("/{catalogId}/withattr")
    public R getAttrGroupWithAttrs(@PathVariable("catalogId") Long catalogId) {
        List<AttrGroupWithAttrsVo> vos = attrGroupService.getAttrGroupWithAttrsByCatalogId(catalogId);
        return R.ok().put("data", vos);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:attrgroup:save")
    public R save(@RequestBody AttrGroupEntity attrGroup) {
        attrGroupService.save(attrGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:attrgroup:update")
    public R update(@RequestBody AttrGroupEntity attrGroup) {
        attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * 删除關聯
     */
    @PostMapping("/attr/relation/delete")
    public R delete(@RequestBody AttrGroupRelationVo[] vos) {
        attrService.deleteRelation(vos);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:attrgroup:delete")
    public R delete(@RequestBody Long[] attrGroupIds) {
        attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }

}
