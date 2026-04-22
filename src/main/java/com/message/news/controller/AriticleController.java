package com.message.news.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.message.news.pojo.Result;


import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/articles")
public class AriticleController {
    // 文章相关接口（CRUD、分页、搜索等）
    @GetMapping("/list")
    // @RequestHeader(name = "Authorization", required = false) String token,
    // HttpServletResponse response
    public Result<String> list() {
        // try {
        // // 验证token
        // Map<String, Object> claims = JwtUtil.parseToken(token); //
        // 解析token，验证有效性（过期/篡改等），如果无效会抛异常
        // return Result.success("文章列表接口");
        // } catch (Exception e) {
        // // 响应状态码401 Unauthorized + 错误信息
        // response.setStatus(401);
        // return Result.fail("Token无效");
        // }

        return Result.success("文章列表接口");
    }

}
