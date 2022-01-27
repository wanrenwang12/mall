package io.winters.mall.config.handler;


import io.winters.mall.common.Constants;
import io.winters.mall.common.ServiceResultEnum;
import io.winters.mall.config.annotation.TokenToUser;
import io.winters.mall.domain.UserDO;
import io.winters.mall.domain.UserDORepo;
import io.winters.mall.domain.UserTokenDO;
import io.winters.mall.domain.UserTokenDORepo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class TokenToUserMethodArgumentResolver implements HandlerMethodArgumentResolver{

    private UserDORepo userDORepo;

    private UserTokenDORepo userTokenDORepo;

    @Autowired
    public TokenToUserMethodArgumentResolver(UserDORepo userDORepo, UserTokenDORepo userTokenDORepo) {
        this.userDORepo = userDORepo;
        this.userTokenDORepo = userTokenDORepo;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(TokenToUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String token = webRequest.getHeader("token");
        if (StringUtils.isBlank(token) || token.length() != Constants.TOKEN_LENGTH) {
            throw new RuntimeException(ServiceResultEnum.NOT_LOGIN_ERROR.getResult());
        }

        UserTokenDO userToken = userTokenDORepo.findUserTokenDOByToken(token);
        if (userToken == null || userToken.getExpireTime().getTime() <= System.currentTimeMillis()) {
            throw new RuntimeException(ServiceResultEnum.TOKEN_EXPIRE_ERROR.getResult());
        }
        UserDO userDO = userDORepo.findUserDOByUserId(userToken.getUserId());
        if (userDO == null) {
            throw new RuntimeException(ServiceResultEnum.USER_NULL_ERROR.getResult());
        }
//      if (userDO.getLockedFlag().intValue() == 1) {
//          throw new RuntimeException(ServiceResultEnum.LOGIN_USER_LOCKED_ERROR.getResult());
//      }
        return userDO;
    }


}
