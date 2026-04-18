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
        if (u == null) {
            userService.register(user.getUsername(), user.getPassword());
            return Result.success();
        } else {
            // 绝对用 fail，不是 error！
            return Result.fail("用户名已存在");
        }
    }

    // ========== 新增：登录接口 ==========
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        // 调用登录方法：成功=Token，失败=错误提示
        String result = userService.login(user.getUsername(), user.getPassword());

        // 判断：如果返回的是错误文字，就失败；否则就是Token，成功
        if (result.equals("用户名不存在") || result.equals("密码错误")) {
            return Result.fail(result);
        } else {
            // 成功：把Token返回给前端
            return Result.success(result);
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