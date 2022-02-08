package yoziming.mall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * spu圖片
 *
 * @author yoziming
 * @email yoziming@gmail.com
 * @date 2022-01-11 20:06:30
 */
@Data
@TableName("pms_spu_images")
public class SpuImagesEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * spu_id
     */
    private Long spuId;
    /**
     * 圖片名
     */
    private String imgName;
    /**
     * 圖片地址
     */
    private String imgUrl;
    /**
     * 順序
     */
    private Integer imgSort;
    /**
     * 是否預設圖
     */
    private Integer defaultImg;

}
