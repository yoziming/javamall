package yozi.mall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 商品三級分類
 * 
 * @author yozi
 * @email yoziming@gmail.com
 * @date 2022-01-11 20:06:30
 */
@Data
@TableName("pms_category")
public class CategoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 分類id
	 */
	@TableId
	private Long catId;
	/**
	 * 分類名稱
	 */
	private String name;
	/**
	 * 父分類id
	 */
	private Long parentCid;
	/**
	 * 層級
	 */
	private Integer catLevel;
	/**
	 * 是否顯示[0-不顯示，1顯示]
	 */
	private Integer showStatus;
	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * 圖標地址
	 */
	private String icon;
	/**
	 * 計量單位
	 */
	private String productUnit;
	/**
	 * 商品數量
	 */
	private Integer productCount;

}
