package yozi.mall.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import yozi.mall.common.exception.PhoneException;
import yozi.mall.common.exception.UsernameException;
import yozi.mall.common.utils.PageUtils;
import yozi.mall.common.utils.Query;
import yozi.mall.common.vo.MemberUserLoginVo;
import yozi.mall.common.vo.MemberUserRegisterVo;
import yozi.mall.common.vo.SocialUser;
import yozi.mall.member.dao.MemberDao;
import yozi.mall.member.dao.MemberLevelDao;
import yozi.mall.member.entity.MemberEntity;
import yozi.mall.member.entity.MemberLevelEntity;
import yozi.mall.member.service.MemberService;

import java.util.Map;

@Service("memberService")
public class MemberServiceImpl extends ServiceImpl<MemberDao, MemberEntity> implements MemberService {

    @Autowired
    MemberLevelDao memberLevelDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberEntity> page = this.page(
                new Query<MemberEntity>().getPage(params),
                new QueryWrapper<MemberEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void register(MemberUserRegisterVo vo) {
        MemberEntity memberEntity = new MemberEntity();

        // 設置等級為普通會員
        MemberLevelEntity levelEntity = memberLevelDao.getDefaultLevel();
        memberEntity.setLevelId(levelEntity.getId());

        // 檢查用戶名和手機號是否唯一，可能拋出異常
        checkPhoneUnique(vo.getPhone());
        checkUserNameUnique(vo.getUserName());

        // 設定帳號密碼
        memberEntity.setNickname(vo.getUserName());
        memberEntity.setUsername(vo.getUserName());
        // 密碼進行MD5加密
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode(vo.getPassword());
        memberEntity.setPassword(encode);
        memberEntity.setMobile(vo.getPhone());
        memberEntity.setGender(0);
        // memberEntity.setCreateTime(new Date());
        // 保存
        baseMapper.insert(memberEntity);
    }

    @Override
    public void checkPhoneUnique(String phone) throws PhoneException {
        Long phoneCount = baseMapper.selectCount(new QueryWrapper<MemberEntity>().eq("mobile", phone));
        if (phoneCount > 0) {
            throw new PhoneException();
        }
    }

    @Override
    public void checkUserNameUnique(String userName) throws UsernameException {
        Long usernameCount = baseMapper.selectCount(new QueryWrapper<MemberEntity>().eq("username", userName));
        if (usernameCount > 0) {
            throw new UsernameException();
        }
    }

    /**
     * 本地登入
     */
    @Override
    public MemberEntity login(MemberUserLoginVo vo) {

        String loginacct = vo.getLoginacct();
        String password = vo.getPassword();

        // 去數據庫查詢 SELECT * FROM ums_member WHERE username = ? OR mobile = ?
        MemberEntity memberEntity = baseMapper.selectOne(new QueryWrapper<MemberEntity>()
                .eq("username", loginacct).or().eq("mobile", loginacct));

        if (memberEntity == null) {
            // 登入失敗
            return null;
        } else {
            // 獲取到數據庫里的password
            String password1 = memberEntity.getPassword();
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            // 進行密碼匹配
            boolean matches = passwordEncoder.matches(password, password1);
            if (matches) {
                // 登入成功
                return memberEntity;
            }
        }
        return null;
    }

    @Override
    public MemberEntity login(SocialUser socialUser) throws Exception {
        return null;
        //
        //     //具有登入和註冊邏輯
        //     String uid = socialUser.getUid();
        //
        //     //1、判斷當前社交用戶是否已經登入過系統
        //     MemberEntity memberEntity = baseMapper.selectOne(new QueryWrapper<MemberEntity>().eq("social_uid", uid));
        //
        //     if (memberEntity != null) {
        //         //這個用戶已經註冊過
        //         //更新用戶的訪問令牌的時間和access_token
        //         MemberEntity update = new MemberEntity();
        //         update.setId(memberEntity.getId());
        //         update.setAccessToken(socialUser.getAccess_token());
        //         update.setExpiresIn(socialUser.getExpires_in());
        //         baseMapper.updateById(update);
        //
        //         memberEntity.setAccessToken(socialUser.getAccess_token());
        //         memberEntity.setExpiresIn(socialUser.getExpires_in());
        //         return memberEntity;
        //     } else {
        //         //2、沒有查到當前社交用戶對應的記錄我們就需要註冊一個
        //         MemberEntity register = new MemberEntity();
        //         //3、查詢當前社交用戶的社交賬號信息（昵稱、性別等）
        //         // 遠程調用，不影響結果
        //         try {
        //             Map<String, String> query = new HashMap<>();
        //             query.put("access_token", socialUser.getAccess_token());
        //             query.put("uid", socialUser.getUid());
        //             HttpResponse response = HttpUtils.doGet("https://api.weibo.com", "/2/users/show.json", "get", new
        //             HashMap<String, String>(), query);
        //
        //             if (response.getStatusLine().getStatusCode() == 200) {
        //                 //查詢成功
        //                 String json = EntityUtils.toString(response.getEntity());
        //                 JSONObject jsonObject = JSON.parseObject(json);
        //                 String name = jsonObject.getString("name");
        //                 String gender = jsonObject.getString("gender");
        //                 String profileImageUrl = jsonObject.getString("profile_image_url");
        //
        //                 register.setNickname(name);
        //                 register.setGender("m".equals(gender) ? 1 : 0);
        //                 register.setHeader(profileImageUrl);
        //             }
        //         }catch (Exception e){}
        //         register.setCreateTime(new Date());
        //         register.setSocialUid(socialUser.getUid());
        //         register.setAccessToken(socialUser.getAccess_token());
        //         register.setExpiresIn(socialUser.getExpires_in());
        //
        //         //把用戶信息插入到數據庫中
        //         baseMapper.insert(register);
        //         return register;
        //     }
    }

}