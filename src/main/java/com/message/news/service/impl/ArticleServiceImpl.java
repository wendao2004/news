package com.message.news.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.message.news.mapper.ArticleMapper;
import com.message.news.pojo.Article;
import com.message.news.pojo.User;
import com.message.news.service.ArticleService;
import com.message.news.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private UserService userService;
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public void add(Article article, HttpServletRequest request) {

        // 1. 从拦截器获取登录用户名
        String username = (String) request.getAttribute("loginUsername");
        // 2. 查询用户ID
        User loginUser = userService.findByUserName(username);

        // 设置创建时间和更新时间
        article.setCreateTime(LocalDateTime.now());

        // 设置更新时间
        article.setUpdateTime(LocalDateTime.now());

        // 设置封面图片
        article.setCoverImg("");

        // 赋值创建人
        article.setCreateUser(loginUser.getId());

        // 调用mapper层添加文章
        articleMapper.add(article);
    }

    // 获取文章列表
    @Override
    public List<Article> getArticleList() {
        return articleMapper.selectArticleList();
    }
}
