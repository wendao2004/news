package com.message.news.controller;

import com.message.news.pojo.Result;
import com.message.news.pojo.User;
import com.message.news.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        // 查询用户
        User u = userService.findByUserName(user.getUsername());
        if(u == null){
            userService.register(user.getUsername(), user.getPassword());
            return Result.success();
        }else{
            // 绝对用 fail，不是 error！
            return Result.fail("用户名已存在");
        }
    }
}