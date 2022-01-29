package yozi.mall.product.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Catalogs2Vo {

    /**
     * 一級父分類的id
     */
    private String catalog1Id;

    /**
     * 三級子分類
     */
    private List<Category3Vo> catalog3List;

    private String id;

    private String name;

    /**
     * 三級分類vo
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Category3Vo {

        /**
         * 父分類、二級分類id
         */
        private String catalog2Id;

        private String id;

        private String name;
    }
}
