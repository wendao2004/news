package com.message.news.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.message.news.mapper.UserMapper;
import com.message.news.pojo.User;
import com.message.news.service.UserService;
import com.message.news.utils.Md5Util;

@Service
public class UserServiceImpl implements UserService {
    @Autowired 
    private UserMapper userMapper;

    // 实现根据用户名查询用户接口
    @Override
    public User findByUserName(String username) {
        User u = userMapper.findByUserName(username);
        return u;
    }

    // 实现注册用户接口
    @Override
    public void register(String username, String password) {
        // 加密
        String md5String = Md5Util.encrypt(password);
        
        // 添加用户
        userMapper.add(username, md5String);
    }
}
