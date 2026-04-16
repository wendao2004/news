package com.message.news.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.message.news.login.handler.LoginPostHandler;
import com.message.news.login.strategy.LoginStrategy;
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

    @Override
    public String login(String username, String password) {
        // 根据用户名查询用户
        User existUser = findByUserName(username);
        if (existUser == null) {
            return "用户名不存在";
        }
        // 密码校验
        String inputPasswordEncoded = Md5Util.encrypt(password);
        if (!existUser.getPassword().equals(inputPasswordEncoded)) {
            return "密码错误";
        }
        return "登录成功";
    }

    // 所有登录策略（Spring自动注入所有实现类）
    @Autowired(required = false)
    private List<LoginStrategy> loginStrategies;

    //所有登录后处理器（Spring自动注入所有实现类）
    @Autowired(required = false)
    private List<LoginPostHandler> loginPostHandlers;

    // ========== 新增：扩展登录实现 ==========
    @Override
    public Map<String, Object> extendLogin(String loginType, String principal, String credential) {
        Map<String, Object> result = new HashMap<>();
        // 1. 根据登录类型找到对应的登录策略
        LoginStrategy strategy = loginStrategies.stream()
                .filter(s -> s.getLoginType().equals(loginType))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("不支持的登录方式：" + loginType));

        // 2. 执行登录验证
        User loginUser = strategy.login(principal, credential);
        result.put("user", loginUser);

        // 3. 执行所有登录后处理（新增处理器只需加新类，这里代码不用动）
        if (loginPostHandlers != null) {
            for (LoginPostHandler handler : loginPostHandlers) {
                Object handleResult = handler.handle(loginUser);
                // 把处理器类名作为Key，结果作为Value存入返回Map
                result.put(handler.getClass().getSimpleName(), handleResult);
            }
        }

        return result;
    }
}
