package com.message.news.login.strategy;

import com.message.news.pojo.User;

/**
 * 登录策略接口（对扩展开放：未来加验证码/微信/QQ登录只需新增实现类）
 */
public interface LoginStrategy {
    /**
     * 登录方式标识（用于区分不同策略）
     */
    String getLoginType();

    /**
     * 执行登录验证
     * @param principal 登录主体（用户名/手机号/第三方ID）
     * @param credential 登录凭证（密码/验证码/第三方Token）
     * @return 登录成功的用户
     */
    User login(String principal, String credential);
}