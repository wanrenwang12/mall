package io.winters.mall.web;


import io.winters.mall.common.Constants;
import io.winters.mall.common.ServiceResultEnum;
import io.winters.mall.config.annotation.TokenToUser;
import io.winters.mall.domain.UserDO;
import io.winters.mall.service.UserService;
import io.winters.mall.util.BeanUtil;
import io.winters.mall.util.PhoneUtil;
import io.winters.mall.web.dto.UserLoginDTO;
import io.winters.mall.web.dto.UserRegisterDTO;
import io.winters.mall.web.dto.UserUpdateDTO;
import io.winters.mall.web.vo.Result;
import io.winters.mall.web.vo.ResultGenerator;
import io.winters.mall.web.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class UserAPI {

    private PhoneUtil phoneUtil;
    private ResultGenerator generator;
    private UserService userService;

    @Autowired
    public UserAPI(PhoneUtil phoneUtil, ResultGenerator generator, UserService userService) {
        this.phoneUtil = phoneUtil;
        this.generator = generator;
        this.userService = userService;
    }

    @PostMapping("/user/register")
    public Result register(@RequestBody @Valid UserRegisterDTO userRegisterDTO){
        if (phoneUtil.isNotPhone(userRegisterDTO.getLoginName())){
            return generator.genFailResult("LOGIN_NAME_IS_NOT_PHONE");
        }

        String registerResult = userService.register(userRegisterDTO.getLoginName(), userRegisterDTO.getPassword());
        log.info("register api,loginName={},loginResult={}", userRegisterDTO.getLoginName(), registerResult);

        if ("SUCCESS".equals(registerResult)) {
            return generator.genSuccessResult();
        }

        return generator.genFailResult(registerResult);
    }



    @PostMapping("/user/login")
    public Result login(@RequestBody @Valid UserLoginDTO userLoginDTO){
        if (!phoneUtil.isPhone(userLoginDTO.getLoginName())){
            return generator.genFailResult(ServiceResultEnum.LOGIN_NAME_IS_NOT_PHONE.getResult());
        }
        String loginResult = userService.login(userLoginDTO.getLoginName(), userLoginDTO.getPassword());

        log.info("login api,loginName={},loginResult={}", userLoginDTO.getLoginName(), loginResult);

        //????????????
        if (!StringUtils.isEmpty(loginResult) && loginResult.length() == Constants.TOKEN_LENGTH) {
            Result result = generator.genSuccessResult();
            result.setData(loginResult);
            return result;
        }
        //????????????
        return generator.genFailResult(loginResult);
    }

    @PostMapping("/user/logout")
    @Transactional
    public Result<String> logout(@TokenToUser UserDO loginUser) {
        Boolean logoutResult = userService.logout(loginUser.getUserId());

        log.info("logout api,loginUser={}", loginUser.getUserId());

        //????????????
        if (logoutResult) {
            return generator.genSuccessResult();
        }
        //????????????
        return generator.genFailResult("logout error");
    }

    //?????????????????????????????????logout????????????????????????@TokenToUser?????????
    @GetMapping("/user/info")
    public Result<UserVO> getUserDetail(@TokenToUser UserDO loginUser) {
        //????????????????????????
        UserVO UserVO = new UserVO();
        BeanUtil.copyProperties(loginUser, UserVO);
        return generator.genSuccessResult(UserVO);
    }

    @PutMapping("/user/info")
    public Result updateInfo(@RequestBody UserUpdateDTO userUpdateDTO, @TokenToUser UserDO loginUser) {
        Boolean flag = userService.updateUserInfo(userUpdateDTO, loginUser.getLoginName());
        if (flag) {
            //????????????
            return generator.genSuccessResult();
        } else {
            //????????????
            return generator.genFailResult("????????????");
        }
    }


}
