package io.winters.mall.service.impl;

import io.winters.mall.domain.*;
import io.winters.mall.service.UserService;
import io.winters.mall.util.NumberUtil;
import io.winters.mall.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import io.winters.mall.domain.UserDO;
import io.winters.mall.domain.UserDORepo;

import java.util.Date;

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
        userDO.setPasswordMd5(password);
        if(userDORepo.save(userDO) != null) {
            return "SUCCESS";
        }

        return "DB_ERROR";
    }

    @Override
    public String login(String loginName, String passwordMD5){

        UserDO user = userDORepo.findUserDOByLoginNameAndPassword(loginName, passwordMD5);
        if (user != null) {
            if (user.getLockedFlag() == 1) {
                return "LOGIN_USER_LOCKED_ERROR";
            }
            //登录后即执行修改token的操作
            String token = getNewToken(System.currentTimeMillis() + "", user.getUserId());
            UserTokenDO UserToken = userTokenDORepo.findByUserId(user.getUserId());
            //当前时间
            Date now = new Date();
            //过期时间
            Date expireTime = new Date(now.getTime() + 2 * 24 * 3600 * 1000);//过期时间 48 小时
            if (UserToken == null) {
                UserToken = new UserTokenDO();
                UserToken.setUserId(user.getUserId());
                UserToken.setToken(token);
                UserToken.setUpdateTime(now);
                UserToken.setExpireTime(expireTime);
                //新增一条token数据
                if (userTokenDORepo.insertSelective(UserToken) > 0) {
                    //新增成功后返回
                    return token;
                }
            } else {
                UserToken.setToken(token);
                UserToken.setUpdateTime(now);
                UserToken.setExpireTime(expireTime);
                //更新
                if (userTokenDORepo.updateByPrimaryKeySelective(UserToken) > 0) {
                    //修改成功后返回
                    return token;
                }
            }

        }
        return "LOGIN_ERROR";


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
