package com.message.news.controller;

import com.message.news.pojo.Result;
import com.message.news.pojo.User;
import com.message.news.service.UserService;

import java.util.Map;

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

      // ========== 新增：登录接口 ==========
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        // 调用登录方法
        String resultMsg = userService.login(user.getUsername(), user.getPassword());
        // 统一返回格式
        if ("登录成功".equals(resultMsg)) {
            return Result.success();
        } else {
            return Result.fail(resultMsg);
        }
    }

     // ========== 新增：扩展登录接口 ==========
    @PostMapping("/extend-login")
    public Result extendLogin(@RequestBody Map<String, String> loginParams) {
        try {
            String loginType = loginParams.get("loginType");
            String principal = loginParams.get("principal");
            String credential = loginParams.get("credential");
            Map<String, Object> result = userService.extendLogin(loginType, principal, credential);
            return Result.success(result);
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

}