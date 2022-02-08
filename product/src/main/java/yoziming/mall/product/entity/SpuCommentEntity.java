package yoziming.mall.product.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品評價
 *
 * @author yoziming
 * @email yoziming@gmail.com
 * @date 2022-01-11 20:06:30
 */
@Data
@TableName("pms_spu_comment")
public class SpuCommentEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * sku_id
     */
    private Long skuId;
    /**
     * spu_id
     */
    private Long spuId;
    /**
     * 商品名字
     */
    private String spuName;
    /**
     * 會員暱稱
     */
    private String memberNickName;
    /**
     * 星級
     */
    private Integer star;
    /**
     * 會員ip
     */
    private String memberIp;
    /**
     * 創建時間
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 顯示狀態[0-不顯示，1-顯示]
     */
    private Integer showStatus;
    /**
     * 購買時屬性組合
     */
    private String spuAttributes;
    /**
     * 點贊數
     */
    private Integer likesCount;
    /**
     * 回覆數
     */
    private Integer replyCount;
    /**
     * 評論圖片/視頻[json數據；[{type:文件類型,url:資源路徑}]]
     */
    private String resources;
    /**
     * 內容
     */
    private String content;
    /**
     * 用户頭像
     */
    private String memberIcon;
    /**
     * 評論類型[0 - 對商品的直接評論，1 - 對評論的回覆]
     */
    private Integer commentType;

}
