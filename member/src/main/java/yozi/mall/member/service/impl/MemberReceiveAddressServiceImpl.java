package yozi.mall.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import yozi.mall.common.utils.PageUtils;
import yozi.mall.common.utils.Query;
import yozi.mall.member.dao.MemberReceiveAddressDao;
import yozi.mall.member.entity.MemberReceiveAddressEntity;
import yozi.mall.member.service.MemberReceiveAddressService;

import java.util.List;
import java.util.Map;

@Service("memberReceiveAddressService")
public class MemberReceiveAddressServiceImpl extends ServiceImpl<MemberReceiveAddressDao, MemberReceiveAddressEntity> implements MemberReceiveAddressService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberReceiveAddressEntity> page = this.page(
                new Query<MemberReceiveAddressEntity>().getPage(params),
                new QueryWrapper<MemberReceiveAddressEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<MemberReceiveAddressEntity> getAddress(Long memberId) {

        List<MemberReceiveAddressEntity> addressList = this.baseMapper.selectList
                (new QueryWrapper<MemberReceiveAddressEntity>().eq("member_id", memberId));

        return addressList;
    }

}