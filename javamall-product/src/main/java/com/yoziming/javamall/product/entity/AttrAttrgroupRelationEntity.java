package com.yoziming.javamall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 屬性&屬性分組關聯
 *
 * @author yoziming
 * @date 2022-01-20 15:38:28
 */
@Data
@TableName("t_product_attr_attrgroup_relation")
public class AttrAttrgroupRelationEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 屬性id
     */
    private Long attrId;
    /**
     * 屬性分組id
     */
    private Long attrGroupId;
    /**
     * 屬性組內排序
     */
    private Integer attrSort;

}
