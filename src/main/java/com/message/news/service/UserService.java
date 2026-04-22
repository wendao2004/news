package com.message.news.service;

import java.util.Map;

import com.message.news.pojo.User;

public interface UserService {
    User findByUserName(String username);
    // 注册用户
    void register(String username, String password);

    String login(String username, String password);

    /**
     * 扩展登录方法
     * 
     * @param loginType  登录方式（password/verify_code/wechat等）
     * @param principal  登录主体
     * @param credential 登录凭证
     * @return 登录结果（包含Token、日志等）
     */
    Map<String, Object> extendLogin(String loginType, String principal, String credential);
    void update(User user);
}
