package com.message.news.login.strategy;

import com.message.news.mapper.UserMapper;
import com.message.news.pojo.User;
import com.message.news.utils.Md5Util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 密码登录策略（对修改关闭：原有密码登录逻辑不动）
 */
@Component
public class PasswordLoginStrategy implements LoginStrategy {
    @Autowired
    private UserMapper userMapper;

    @Override
    public String getLoginType() {
        return "password"; // 登录方式标识
    }

    @Override
    public User login(String username, String password) {
        // 1. 查用户
        User existUser = userMapper.findByUserName(username);
        if (existUser == null) {
            throw new RuntimeException("用户名不存在");
        }

        // 2. 密码加密对比
        String inputPasswordEncoded = Md5Util.encrypt(password);
        if (!existUser.getPassword().equals(inputPasswordEncoded)) {
            throw new RuntimeException("密码错误");
        }

        // 3. 返回登录成功的用户
        return existUser;
    }
}