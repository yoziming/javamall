package yoziming.mall.common.feign.fallback;

import yoziming.mall.common.exception.BizCodeEnum;
import yoziming.mall.common.feign.SeckillFeignService;
import yoziming.mall.common.utils.R;

/**
 * 熔斷或降級的實現
 */
public class SeckillFeignServiceFallBack implements SeckillFeignService {
    @Override
    public R getSkuSeckilInfo(Long skuId) {
        // 可以返回某些預設值
        // TOO_MANY_REQUEST(10002, "請求流量過大，請稍後再試"),
        return R.error(BizCodeEnum.TOO_MANY_REQUEST.getCode(), BizCodeEnum.TOO_MANY_REQUEST.getMessage());
    }
}
