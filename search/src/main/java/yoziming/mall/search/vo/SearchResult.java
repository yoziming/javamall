package yoziming.mall.search.vo;

import lombok.Data;
import yoziming.mall.common.es.SkuEsModel;

import java.util.ArrayList;
import java.util.List;

@Data
public class SearchResult {
    private List<SkuEsModel> products;// es檢索到的所有商品信息

    /**
     * 分頁信息
     */
    private Integer pageNum;// 當前頁碼
    private Long total;// 總記錄數
    private Integer totalPages;// 總頁碼
    private List<Integer> pageNavs;// 允許的頁數集合[1、2、3、4、5]

    private List<BrandVo> brands;// 當前查詢到的結果所有涉及到的品牌
    private List<CatalogVo> catalogs;// 當前查詢到的結果所有涉及到的分類
    /**
     * attrs=1_anzhuo&attrs=5_其他:1080P
     */
    private List<AttrVo> attrs;// 當前查詢到的結果所有涉及到的屬性【符合檢索條件的，可檢索的屬性】

    // ============================以上是要返回的數據====================================

    //麵包屑導航數據
    private List<NavVo> navs = new ArrayList<>();
    private List<Long> attrIds = new ArrayList<>();

    @Data
    public static class NavVo {
        private String navName;// 屬性名
        private String navValue;//屬性值
        private String link;// 取消了之後要調到那個地方
    }

    @Data
    public static class BrandVo {
        private Long brandId;//
        private String brandName;//
        private String brandImg;//
    }

    @Data
    public static class CatalogVo {
        private Long catalogId;//
        private String catalogName;//
    }

    @Data
    public static class AttrVo {
        private Long attrId;// 允許檢索的 屬性Id
        private String attrName;// 允許檢索的 屬性名
        private List<String> attrValue;// 屬性值【多個】
    }
}
