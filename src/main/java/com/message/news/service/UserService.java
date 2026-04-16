package com.message.news.service;

import com.message.news.pojo.User;

public interface UserService {

    // 根据用户名查询用户
    User findByUserName(String username);

    // 注册用户
    void register(String username, String password);

    String login(String username, String password);

}
