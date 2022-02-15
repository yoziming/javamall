package yoziming.mall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yoziming.mall.common.feign.MemberFeignService;
import yoziming.mall.common.utils.PageUtils;
import yoziming.mall.common.utils.Query;
import yoziming.mall.ware.dao.WareInfoDao;
import yoziming.mall.ware.entity.WareInfoEntity;
import yoziming.mall.ware.service.WareInfoService;
import yoziming.mall.ware.vo.FareVo;

import java.math.BigDecimal;
import java.util.Map;

@Service("wareInfoService")
public class WareInfoServiceImpl extends ServiceImpl<WareInfoDao, WareInfoEntity> implements WareInfoService {
    @Autowired
    MemberFeignService memberFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<WareInfoEntity> queryWrapper = new QueryWrapper<>();
        String key = (String) params.get("key");
        if (StringUtils.isNotBlank(key)) {
            queryWrapper.eq("id", key)
                    .or().like("name", key)
                    .or().like("address", key)
                    .or().like("areacode", key);
        }
        IPage<WareInfoEntity> page = this.page(
                new Query<WareInfoEntity>().getPage(params),
                queryWrapper
        );
        return new PageUtils(page);
    }

    /**
     * 計算運費
     *
     * @param addrId
     * @return
     */
    @Override
    public FareVo getFare(Long addrId) {

        FareVo fareVo = new FareVo();

        // // 地址的詳細信息
        // R addrInfo = memberFeignService.info(addrId);
        //
        // MemberAddressVo memberAddressVo = addrInfo.getData("memberReceiveAddress", new
        //         TypeReference<MemberAddressVo>() {
        //         });
        //
        // if (memberAddressVo != null) {
        // String phone = memberAddressVo.getPhone();
        // //截取用戶手機號碼最後一位作為我們的運費計算
        // //1558022051
        // String fare = phone.substring(phone.length() - 1);
        BigDecimal bigDecimal = new BigDecimal(1);
        fareVo.setFare(bigDecimal);
        // fareVo.setAddress(memberAddressVo);

        return fareVo;
        // }
        // return null;
    }

}