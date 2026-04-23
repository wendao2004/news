package com.message.news.service;

import java.util.Map;

import com.message.news.pojo.User;

public interface UserService {
    // 根据用户名查询用户
    User findByUserName(String username);

    // 注册用户
    void register(String username, String password);

    // 登录方法：成功=Token，失败=错误提示
    String login(String username, String password);

    // 更新用户信息
    void update(User user);

    // 更新用户头像
    void updateAvatar(String avatarUrl);

    // 更新用户密码
    void updatePassword(Integer id, String newPassword);

    /**
     * 扩展登录方法
     * 
     * @param loginType  登录方式（password/verify_code/wechat等）
     * @param principal  登录主体
     * @param credential 登录凭证
     * @return 登录结果（包含Token、日志等）
     */
    // 登录方法：成功=Token，失败=错误提示
    Map<String, Object> extendLogin(String loginType, String principal, String credential);

}
