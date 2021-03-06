package com.yoziming.javamall.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yoziming.common.utils.PageUtils;
import com.yoziming.common.utils.Query;
import com.yoziming.javamall.member.dao.MemberReceiveAddressDao;
import com.yoziming.javamall.member.entity.MemberReceiveAddressEntity;
import com.yoziming.javamall.member.service.MemberReceiveAddressService;
import com.yoziming.javamall.member.vo.AddressVo;
import com.yoziming.javamall.ware.vo.FareVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
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
        return this.list(new QueryWrapper<MemberReceiveAddressEntity>().eq("member_id", memberId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAddress(AddressVo addressVo) {
        MemberReceiveAddressEntity memberReceiveAddressEntity = new MemberReceiveAddressEntity();

        BeanUtils.copyProperties(addressVo, memberReceiveAddressEntity);
        memberReceiveAddressEntity.setMemberId(addressVo.getMemberId());
        if (ObjectUtils.isEmpty(addressVo.getDefaultStatus())) {
            // ?????? ?????????????????????????????????
            memberReceiveAddressEntity.setDefaultStatus(0);
            this.save(memberReceiveAddressEntity);
            return;
        }
        // ???????????????????????????????????????????????????
        UpdateWrapper<MemberReceiveAddressEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("member_id", addressVo.getMemberId())
                .eq("default_status", 1)
                .set("default_status", 0);
        this.update(updateWrapper);
        this.save(memberReceiveAddressEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setDefault(AddressVo addressVo) {
        // ????????????????????????????????????
        QueryWrapper<MemberReceiveAddressEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("member_id", addressVo.getMemberId())
                .eq("default_status", 1);
        MemberReceiveAddressEntity one = this.getOne(queryWrapper);
        if (!ObjectUtils.isEmpty(one)) {
            // ??????????????????????????????????????????
            one.setDefaultStatus(0);
            this.updateById(one);
        }
        // ????????????????????????
        UpdateWrapper<MemberReceiveAddressEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("default_status", 1)
                .eq("id", addressVo.getId());
        this.update(updateWrapper);

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeAddress(AddressVo addressVo) {

        removeById(addressVo.getId());

        //?????????????????? ??????????????????????????????????????????
        if (addressVo.getDefaultStatus().equals(1)) {
            baseMapper.updateDefaultStatus(addressVo.getMemberId());
        }
    }

    @Override
    public FareVo getFare(Long addrId) {

        FareVo fareVo = new FareVo();

        MemberReceiveAddressEntity memberReceiveAddressEntity = this.getById(addrId);
        if (memberReceiveAddressEntity != null) {
            //???????????????????????????????????????
            String phone = memberReceiveAddressEntity.getPhone();
            //1008000
            // String substring = phone.substring(phone.length() - 1, phone.length());
            // BigDecimal bigDecimal = new BigDecimal(substring);
            fareVo.setAddress(memberReceiveAddressEntity);
            fareVo.setFare(BigDecimal.ZERO);
            return fareVo;
        }
        return null;
    }

}