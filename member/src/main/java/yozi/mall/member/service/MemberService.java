package yozi.mall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import yozi.mall.common.exception.PhoneException;
import yozi.mall.common.exception.UsernameException;
import yozi.mall.common.utils.PageUtils;
import yozi.mall.common.vo.MemberUserLoginVo;
import yozi.mall.common.vo.MemberUserRegisterVo;
import yozi.mall.common.vo.SocialUser;
import yozi.mall.member.entity.MemberEntity;

import java.util.Map;

/**
 * 會員
 *
 * @author yozi
 * @email yoziming@gmail.com
 * @date 2022-01-11 21:42:05
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 用戶註冊
     *
     * @param vo
     */
    void register(MemberUserRegisterVo vo);

    /**
     * 判斷郵箱是否重複
     *
     * @param phone
     * @return
     */
    void checkPhoneUnique(String phone) throws PhoneException;

    /**
     * 判斷用戶名是否重複
     *
     * @param userName
     * @return
     */
    void checkUserNameUnique(String userName) throws UsernameException;

    /**
     * 用戶登入
     */
    MemberEntity login(MemberUserLoginVo vo);

    /**
     * 社交用戶的登入
     *
     * @param socialUser
     * @return
     */
    MemberEntity login(SocialUser socialUser) throws Exception;
}

