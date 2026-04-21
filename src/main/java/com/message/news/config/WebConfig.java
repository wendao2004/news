package com.message.news.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.message.news.interceptors.LoginInterceptor;

// 配置拦截器
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 登录注册等公共路径不需要拦截，其他路径需要登录验证
        registry.addInterceptor(loginInterceptor)
                .excludePathPatterns("/user/login", "/user/register"); // 排除登录注册等公共路径
    }
}
