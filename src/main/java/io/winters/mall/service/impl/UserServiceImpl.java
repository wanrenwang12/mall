package io.winters.mall.service.impl;

import io.winters.mall.common.ServiceResultEnum;
import io.winters.mall.domain.*;
import io.winters.mall.service.UserService;
import io.winters.mall.util.NumberUtil;
import io.winters.mall.util.TokenUtil;
import io.winters.mall.web.dto.UserUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import java.util.Date;

@Service
@Component
public class UserServiceImpl implements UserService {




    private UserDORepo userDORepo;
    private UserTokenDORepo userTokenDORepo;


    @Autowired
    public UserServiceImpl(UserDORepo userDORepo, UserTokenDORepo userTokenDORepo) {
        this.userDORepo = userDORepo;
        this.userTokenDORepo = userTokenDORepo;
    }

    @Override
    public String register(String loginName, String password) {

        if (userDORepo.findUserDOByLoginName(loginName) != null){
            return "SAME_LOGIN_NAME_EXIST";
        }

        UserDO userDO = new UserDO();
        userDO.setLoginName(loginName);
        userDO.setNickName(loginName);
        userDO.setPassword(password);
        byte temp = 0;
        userDO.setLockedFlag(temp);
        if(userDORepo.save(userDO) != null) {
            return "SUCCESS";
        }

        return "DB_ERROR";
    }



    @Override
    public String login(String loginName, String password){

        UserDO user = userDORepo.findUserDOByLoginNameAndPassword(loginName, password);
        if (user == null){
            return ServiceResultEnum.LOGIN_ERROR.getResult();
        }
        if (user.getLockedFlag() == 1) {
            return ServiceResultEnum.LOGIN_USER_LOCKED_ERROR.getResult();
        }

        //登录后即执行修改token的操作
        String token = getNewToken(System.currentTimeMillis() + "", user.getUserId());
        UserTokenDO UserToken = userTokenDORepo.findUserTokenDOByUserId(user.getUserId());
        //当前时间
        Date now = new Date();
        //过期时间
        Date expireTime = new Date(now.getTime() + 2 * 24 * 3600 * 1000);//过期时间 48 小时
        if (UserToken == null) {
            UserToken = new UserTokenDO();
        }
        UserToken.setUserId(user.getUserId());
        UserToken.setToken(token);
        UserToken.setUpdateTime(now);
        UserToken.setExpireTime(expireTime);
        //新增一条token数据
        if (userTokenDORepo.save(UserToken) != null) {
            //新增成功后返回
            return token;
        }

        return ServiceResultEnum.LOGIN_ERROR.getResult();

    }

    @Override
    public Boolean logout(Long userId) {
        try{
            userTokenDORepo.deleteUserTokenDOByUserId(userId);
        } catch(Exception e){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    /**
     *
     * @param updateUser
     * @param userName
     * @return
     */
    @Override
    public Boolean updateUserInfo(UserUpdateDTO updateUser, String userName) {
        UserDO user = userDORepo.findUserDOByLoginName(userName);
        if (user == null) {
            return Boolean.FALSE;
            //NewBeeMallException.fail(ServiceResultEnum.DATA_NOT_EXIST.getResult());
        }
        user.setNickName(updateUser.getNickName());
        //user.setPasswordMd5(mallUser.getPasswordMd5());
        //若密码为空字符，则表明用户不打算修改密码，使用原密码保存
        if (updateUser.getPassword() != null){
            user.setPassword(updateUser.getPassword());
        }
        else{
            user.setPassword(user.getPassword());
        }
        user.setIntroduceSign(updateUser.getIntroduceSign());
        if (userDORepo.save(user) != null) {
            return true;
        }
        return false;
    }

    /**
     * 获取token值
     *
     * @param timeStr
     * @param userId
     * @return
     */
    private String getNewToken(String timeStr, Long userId) {
        String src = timeStr + userId + NumberUtil.genRandomNum(4);
        return TokenUtil.genToken(src);

    }

}
