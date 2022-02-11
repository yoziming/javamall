package com.yoziming.javamall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yoziming.common.utils.PageUtils;
import com.yoziming.javamall.member.entity.MemberEntity;
import com.yoziming.javamall.member.exception.PhoneExistException;
import com.yoziming.javamall.member.exception.UsernameExistException;
import com.yoziming.javamall.member.vo.MemberLoginVo;
import com.yoziming.javamall.member.vo.MemberRegistVo;
import com.yoziming.javamall.member.vo.PasswordFindVo;

import java.util.Map;

/**
 * 會員
 *
 * @author yoziming
 * @date 2022-01-20 17:30:33
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void regist(MemberRegistVo vo);

    void checkPhoneUnique(String phone) throws PhoneExistException;

    void checkUsernameUnique(String username) throws UsernameExistException;

    MemberEntity login(MemberLoginVo vo);

    void findPassword(PasswordFindVo vo) throws RuntimeException;
}

