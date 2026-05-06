package com.message.news.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.message.news.pojo.Article;
import com.message.news.pojo.Result;
import com.message.news.service.ArticleService;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/article")
public class AriticleController {

    @Autowired
    private ArticleService articleService;

    // 添加文章
    @PostMapping
    public Result<String> add(@RequestBody @Validated Article article, HttpServletRequest request) {
        articleService.add(article, request);
        return Result.success("文章添加成功");
    }

    // 获取文章列表
    @GetMapping("/list")
    public Result<List<Article>> getArticleList() {
        List<Article> list = articleService.getArticleList();
        return Result.success(list);
    }
}
