package com.message.news.service;

import java.util.List;

import com.message.news.pojo.Article;

import jakarta.servlet.http.HttpServletRequest;

public interface ArticleService {

    // 获取文章列表
    void add(Article article, HttpServletRequest request);

    // 获取文章列表
    List<Article> getArticleList();

}
