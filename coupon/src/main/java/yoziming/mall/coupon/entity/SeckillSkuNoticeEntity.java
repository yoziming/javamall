package yoziming.mall.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 秒殺商品通知訂閲
 *
 * @author yoziming
 * @email yoziming@gmail.com
 * @date 2022-01-11 21:40:27
 */
@Data
@TableName("sms_seckill_sku_notice")
public class SeckillSkuNoticeEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * member_id
     */
    private Long memberId;
    /**
     * sku_id
     */
    private Long skuId;
    /**
     * 活動場次id
     */
    private Long sessionId;
    /**
     * 訂閲時間
     */
    private Date subcribeTime;
    /**
     * 發送時間
     */
    private Date sendTime;
    /**
     * 通知方式[0-短信，1-郵件]
     */
    private Integer noticeType;

}
