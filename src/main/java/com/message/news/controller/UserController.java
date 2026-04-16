package com.message.news.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.message.news.pojo.Result;
import com.message.news.pojo.User;
import com.message.news.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result register(String username, String password) {
        //查询用户
        User u = userService.findByUserName(username);
        if(u ==null){
            // 没有注册过
            userService.register(username, password);
            return Result.success();
        }else{
            return Result.error("用户名已存在");
        }
    }
}
