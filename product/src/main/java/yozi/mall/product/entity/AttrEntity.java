package yozi.mall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 商品屬性
 * 
 * @author yozi
 * @email yoziming@gmail.com
 * @date 2022-01-11 20:06:30
 */
@Data
@TableName("pms_attr")
public class AttrEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 屬性id
	 */
	@TableId
	private Long attrId;
	/**
	 * 屬性名
	 */
	private String attrName;
	/**
	 * 是否需要檢索[0-不需要，1-需要]
	 */
	private Integer searchType;
	/**
	 * 屬性圖標
	 */
	private String icon;
	/**
	 * 可選值列表[用逗號分隔]
	 */
	private String valueSelect;
	/**
	 * 屬性類型[0-銷售屬性，1-基本屬性，2-既是銷售屬性又是基本屬性]
	 */
	private Integer attrType;
	/**
	 * 啓用狀態[0 - 禁用，1 - 啓用]
	 */
	private Long enable;
	/**
	 * 所屬分類
	 */
	private Long catelogId;
	/**
	 * 快速展示【是否展示在介紹上；0-否 1-是】，在sku中仍然可以調整
	 */
	private Integer showDesc;
	/**
	 * 單選 多選[0 1]
	 */
	private String valueType;

}
