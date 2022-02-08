package yoziming.mall.product.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import yoziming.mall.product.entity.AttrEntity;

import java.util.List;

@Data
public class AttrGroupWithAttrsVo {

    /**
     * 分組id
     */
    @TableId
    private Long attrGroupId;
    /**
     * 組名
     */
    private String attrGroupName;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 描述
     */
    private String decript;
    /**
     * 組圖標
     */
    private String icon;
    /**
     * 所屬分類id
     */
    private Long catalogId;

    private List<AttrEntity> attrs;

}
