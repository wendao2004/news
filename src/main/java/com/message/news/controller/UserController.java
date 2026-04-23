package com.message.news.controller;

import com.message.news.pojo.Result;
import com.message.news.pojo.User;
import com.message.news.service.UserService;
import com.message.news.utils.JwtUtil;
import com.message.news.utils.Md5Util;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    // 获取用户信息接口（需要登录验证，前端传Token）
    @PostMapping("/userInfo")
    public Result<User> userInfo(@RequestHeader(name = "Authorization") String token) {
        Map<String, Object> map = JwtUtil.parseToken(token); // 使用JwtUtil工具类解析token，获取包含用户信息的Map集合
        String username = (String) map.get("username"); // 从Map中获取username字段，并将其转换为String类型
        User user = userService.findByUserName(username); // 通过userService的findByUserName方法根据用户名查询用户信息
        return Result.success(user); // 返回查询成功的用户信息，使用Result.success方法封装结果
    }

    // ========== 新增：更新用户信息接口 ==========
    @PostMapping("/update")
    public Result update(@RequestBody @Validated User user) {
        userService.update(user);
        return Result.success();
    }

    // ========== 新增：更新用户头像接口 ==========
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl) {
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    // 更新用户密码
    @PostMapping("/updatePassword")
    // 🔥 新增：注入 HttpServletRequest
    public Result updatePassword(@RequestBody Map<String, String> params, HttpServletRequest request) {
        String oldPassword = params.get("oldPassword");
        String newPassword = params.get("newPassword");
        String confirmPassword = params.get("confirmPassword");

        if (!StringUtils.hasLength(oldPassword) || !StringUtils.hasLength(newPassword)
                || !StringUtils.hasLength(confirmPassword)) {
            return Result.fail("请提供完整的密码信息");
        }

        // 🔥 核心：从Request获取登录用户名（替代ThreadLocal）
        String username = (String) request.getAttribute("loginUsername");
        User loginUser = userService.findByUserName(username);

        // 验证旧密码
        if (!loginUser.getPassword().equals(Md5Util.encrypt(oldPassword))) {
            return Result.fail("旧密码错误");
        }
        // 验证两次密码
        if (!newPassword.equals(confirmPassword)) {
            return Result.fail("新密码和确认密码不一致");
        }

        // 更新密码
        userService.updatePassword(loginUser.getId(), Md5Util.encrypt(newPassword));
        return Result.success();
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