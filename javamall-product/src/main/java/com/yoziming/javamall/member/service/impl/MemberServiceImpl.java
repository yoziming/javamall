package com.yoziming.javamall.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yoziming.common.utils.PageUtils;
import com.yoziming.common.utils.Query;
import com.yoziming.javamall.member.dao.MemberDao;
import com.yoziming.javamall.member.entity.MemberEntity;
import com.yoziming.javamall.member.exception.PhoneExistException;
import com.yoziming.javamall.member.exception.UsernameExistException;
import com.yoziming.javamall.member.service.MemberService;
import com.yoziming.javamall.member.vo.MemberLoginVo;
import com.yoziming.javamall.member.vo.MemberRegistVo;
import com.yoziming.javamall.member.vo.PasswordFindVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Slf4j
@Service("memberService")
public class MemberServiceImpl extends ServiceImpl<MemberDao, MemberEntity> implements MemberService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<MemberEntity> wrapper = new QueryWrapper<MemberEntity>();
        wrapper.orderByDesc("create_time");
        if (!StringUtils.isEmpty(params.get("memberUsername"))) {
            wrapper.eq("username", params.get("memberUsername"));
        }
        IPage<MemberEntity> page = this.page(
                new Query<MemberEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

    @Override
    public void regist(MemberRegistVo vo) {
        MemberEntity memberEntity = new MemberEntity();
        //設置預設等級
        memberEntity.setLevelId(1l);

        //檢查用戶名和手機號是否唯一,為了讓controller能感知異常，異常機制
        checkPhoneUnique(vo.getPhone());
        checkUsernameUnique(vo.getUsername());

        memberEntity.setMobile(vo.getPhone());
        memberEntity.setUsername(vo.getUsername());
        memberEntity.setNickname(vo.getUsername());

        //密碼進行加密存儲。加鹽：如$1$+8位字符，但可以使用BCryptPasswordEncoder已加鹽+可根據明文暗文解析
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode(vo.getPassword());
        memberEntity.setPassword(encode);

        //其他的預設訊息

        baseMapper.insert(memberEntity);
    }

    @Override
    public void checkPhoneUnique(String phone) throws PhoneExistException {
        Integer mobile = baseMapper.selectCount(new QueryWrapper<MemberEntity>().eq("mobile", phone));
        if (mobile > 0) {
            throw new PhoneExistException();
        }
    }

    @Override
    public void checkUsernameUnique(String username) throws UsernameExistException {
        Integer count = baseMapper.selectCount(new QueryWrapper<MemberEntity>().eq("username", username));
        if (count > 0) {
            throw new UsernameExistException();
        }
    }

    @Override
    public MemberEntity login(MemberLoginVo vo) {
        String loginacct = vo.getLoginacct();
        String password = vo.getPassword();

        //1、去數據庫查詢賬號
        MemberEntity entity = baseMapper.selectOne(new QueryWrapper<MemberEntity>().eq("username", loginacct)
                .or().eq("mobile", loginacct));
        if (entity == null) {
            //登入失敗
            return null;
        } else {
            //獲取數據庫加密的密碼
            String passwordDb = entity.getPassword();
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            //密碼匹配
            boolean matches = passwordEncoder.matches(password, passwordDb);
            if (matches) {
                return entity;
            } else {
                return null;
            }
        }
    }

    @Override
    public void findPassword(PasswordFindVo vo) throws RuntimeException {
        String phone = vo.getPhone();
        Integer mobile = baseMapper.selectCount(new QueryWrapper<MemberEntity>().eq("mobile", phone));
        if (mobile < 0) {
            throw new RuntimeException("郵箱不存在！");
        }
        // 密碼加密
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodePass = passwordEncoder.encode(vo.getPassword());
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setPassword(encodePass);
        UpdateWrapper<MemberEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("password", encodePass).eq("mobile", phone);
        baseMapper.update(memberEntity, updateWrapper);
    }

}