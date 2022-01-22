package io.winters.mall.config;

import io.winters.mall.config.handler.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {



    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new AuthInterceptor())
                .addPathPatterns("/api/v1")
                .excludePathPatterns("/user/register")  //过滤掉登录页面
                .excludePathPatterns("/static/**") // 过滤静态资源
                .excludePathPatterns("/user/login");  //过滤掉注册页面

    }
}