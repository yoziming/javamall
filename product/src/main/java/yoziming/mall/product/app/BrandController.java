package yoziming.mall.product.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yoziming.mall.common.utils.PageUtils;
import yoziming.mall.common.utils.R;
import yoziming.mall.product.entity.BrandEntity;
import yoziming.mall.product.service.BrandService;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 品牌
 *
 * @author yoziming
 * @email yoziming@gmail.com
 * @date 2022-01-11 20:06:30
 */
@RestController
@RequestMapping("product/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:brand:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = brandService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{brandId}")
    //@RequiresPermissions("product:brand:info")
    public R info(@PathVariable("brandId") Long brandId) {
        BrandEntity brand = brandService.getById(brandId);

        return R.ok().put("brand", brand);
    }

    @GetMapping("/infos")
    public R info(@RequestParam("brandIds") List<Long> brandIds) {
        List<BrandEntity> brand = brandService.getBrandsByIds(brandIds);
        return R.ok().put("brand", brand);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:brand:save")
    public R save(@Valid @RequestBody BrandEntity brand) {
        brandService.save(brand);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:brand:update")
    public R update(@RequestBody BrandEntity brand) {
        brandService.updateDetail(brand);

        return R.ok();
    }

    /**
     * 切換顯示狀態
     */
    @RequestMapping("/update/status")
    //@RequiresPermissions("product:brand:update")
    public R updateStatus(@RequestBody BrandEntity brand) {
        brandService.updateById(brand);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:brand:delete")
    public R delete(@RequestBody Long[] brandIds) {
        brandService.removeByIds(Arrays.asList(brandIds));

        return R.ok();
    }

    // @RequestMapping("/upload")
    // public R upload(HttpServletRequest request, HttpServletResponse response) throws ServletException,
    //         IOException {
    //     System.out.println("接收到有人來上傳");
    //     // 先判斷使否為多段資料，確認是來上傳檔案的
    //     if (ServletFileUpload.isMultipartContent(request)) {
    //         // 創建一個fileItemFactory工廠實現類
    //         FileItemFactory fileItemFactory = new DiskFileItemFactory();
    //         // 創建解析用的工具類
    //         ServletFileUpload up = new ServletFileUpload(fileItemFactory);
    //
    //         try {
    //             // 拆分成一個一個的表單項目FileItem
    //             List<FileItem> list = up.parseRequest(request);
    //
    //             // 判斷是普通表單類還是上傳的檔案
    //             for (FileItem fileItem : list) {
    //                 if (fileItem.isFormField()) {
    //                     // 是普通表單項
    //                     System.out.println("表單name=" + fileItem.getFieldName());
    //                     // 參數UTF-8避免亂碼
    //                     System.out.println("值=" + fileItem.getString("UTF-8"));
    //                 } else {
    //                     // 來到這是個檔案
    //                     LeanCloud.initialize("aSH73OISSuMwNyQKbjEqI09G-MdYXbMMI", "Mf1sKtWCiygfeQJAd9L8QHxv", "lc" +
    //                             "-gluttony.s3" +
    //                             ".amazonaws.com");
    //                     Map<String, Object> meta = new HashMap<String, Object>();
    //                     meta.put(fileItem.getFieldName(), fileItem);
    //                     meta.put("mime_type", "application/json");
    //                     LCFile file = new LCFile("resume.txt", "LeanCloud".getBytes().toString(), meta);
    //                     file.saveInBackground().blockingSubscribe();
    //
    //                 }
    //             }
    //
    //         } catch (Exception e) {
    //             e.printStackTrace();
    //         }
    //     }
    //     return R.ok();
    // }
}
