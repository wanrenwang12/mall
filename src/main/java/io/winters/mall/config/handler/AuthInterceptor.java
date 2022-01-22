package io.winters.mall.config.handler;

import io.winters.mall.domain.UserDORepo;
import io.winters.mall.domain.UserTokenDORepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private UserDORepo userDORepo;
    @Autowired
    private UserTokenDORepo userTokenDORepo;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        System.out.println("preHandle");
        HttpSession session = request.getSession();
        Object loginName = session.getAttribute("loginName");

        //不知道对不对
        Long UserId = userDORepo.findUserDOByLoginName((String) loginName).getUserId();
        String userToken= userTokenDORepo.findUserTokenDOByUserId(UserId).getToken();
        if (session.getAttribute("token") != userToken) {  //校验登录标记
            request.getRequestDispatcher("/login").forward(request, response);  //对于未登录的用户跳转到登录页面
            return false;
        }
        return true;
    }
}