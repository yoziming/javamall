package yoziming.mall.order.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 訂單退貨申請
 *
 * @author yoziming
 * @email yoziming@gmail.com
 * @date 2022-01-11 21:43:36
 */
@Data
@TableName("oms_order_return_apply")
public class OrderReturnApplyEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * order_id
     */
    private Long orderId;
    /**
     * 退貨商品id
     */
    private Long skuId;
    /**
     * 訂單編號
     */
    private String orderSn;
    /**
     * 申請時間
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 會員用户名
     */
    private String memberUsername;
    /**
     * 退款金額
     */
    private BigDecimal returnAmount;
    /**
     * 退貨人姓名
     */
    private String returnName;
    /**
     * 退貨人電話
     */
    private String returnPhone;
    /**
     * 申請狀態[0->待處理；1->退貨中；2->已完成；3->已拒絕]
     */
    private Integer status;
    /**
     * 處理時間
     */
    private Date handleTime;
    /**
     * 商品圖片
     */
    private String skuImg;
    /**
     * 商品名稱
     */
    private String skuName;
    /**
     * 商品品牌
     */
    private String skuBrand;
    /**
     * 商品銷售屬性(JSON)
     */
    private String skuAttrsVals;
    /**
     * 退貨數量
     */
    private Integer skuCount;
    /**
     * 商品單價
     */
    private BigDecimal skuPrice;
    /**
     * 商品實際支付單價
     */
    private BigDecimal skuRealPrice;
    /**
     * 原因
     */
    private String reason;
    /**
     * 描述
     */
    private String description述;
    /**
     * 憑證圖片，以逗號隔開
     */
    private String descPics;
    /**
     * 處理備註
     */
    private String handleNote;
    /**
     * 處理人員
     */
    private String handleMan;
    /**
     * 收貨人
     */
    private String receiveMan;
    /**
     * 收貨時間
     */
    private Date receiveTime;
    /**
     * 收貨備註
     */
    private String receiveNote;
    /**
     * 收貨電話
     */
    private String receivePhone;
    /**
     * 公司收貨地址
     */
    private String companyAddress;

}
