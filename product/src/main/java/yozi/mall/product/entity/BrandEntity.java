package yozi.mall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 品牌
 *
 * @author yozi
 * @email yoziming@gmail.com
 * @date 2022-01-11 20:06:30
 */
@Data
@TableName("pms_brand")
public class BrandEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 品牌id
     */
    @TableId
    private Long brandId;
    /**
     * 品牌名
     */

    @NotBlank
    private String name;
    /**
     * 品牌logo地址
     */
    private String logo;
    /**
     * 介紹
     */
    private String descript;
    /**
     * 顯示狀態[0-不顯示；1-顯示]
     */
    private Integer showStatus;
    /**
     * 檢索首字母
     */
    @NotBlank
    @Pattern(regexp = "^[a-zA-z]$", message = "檢索首字母只能為一個英文字")
    private String firstLetter;
    /**
     * 排序
     */
    @NotNull
    @Min(0)
    private Integer sort;

}
