package com.message.news.interceptors;

import com.message.news.pojo.Result;
import com.message.news.utils.JwtUtil;
import com.message.news.utils.ThreadLocalUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    // SpringBoot自带的JSON工具，无需额外依赖
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 1. 获取请求头Token
        String token = request.getHeader("Authorization");

        // 2. 无Token 直接拦截
        if (token == null || token.trim().isEmpty()) {
            response.setContentType("application/json;charset=utf-8");
            Result result = Result.fail("请先登录");
            // 转为JSON返回
            response.getWriter().write(objectMapper.writeValueAsString(result));
            return false;
        }

        try {
            // 🔥 仅修改：接收解析后的用户数据（原有逻辑不动）
            Map<String, Object> claims = JwtUtil.parseToken(token);
            // 🔥 仅新增：把用户名存入Request请求域（原生方式，无任何侵入）
            request.setAttribute("loginUsername", claims.get("username"));
            ThreadLocalUtil.set(claims.get(token));
            // 校验通过 放行
            return true;
        } catch (Exception e) {
            // 4. Token无效/过期 拦截
            response.setContentType("application/json;charset=utf-8");
            Result result = Result.fail("登录已过期，请重新登录");
            response.getWriter().write(objectMapper.writeValueAsString(result));
            return false;
        }
    }
}