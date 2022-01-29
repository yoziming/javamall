package yozi.mall.product.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 屬性分組
 *
 * @author yozi
 * @email yoziming@gmail.com
 * @date 2022-01-11 20:06:30
 */
@Data
@TableName("pms_attr_group")
public class AttrGroupEntity implements Serializable {
    private static final long serialVersionUID = 1L;

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
    private String descript;
    /**
     * 組圖標
     */
    private String icon;
    /**
     * 所屬分類id
     */
    private Long catalogId;

    /**
     * 商品屬性所在的路徑(電器-大家電-電視)
     */
    @TableField(exist = false)
    private Long[] catalogPath;
}
