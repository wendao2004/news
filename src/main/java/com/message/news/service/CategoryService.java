package com.message.news.service;

import com.message.news.pojo.Category;
import jakarta.servlet.http.HttpServletRequest;

public interface CategoryService {
    // 新增 request 参数
    void addCategory(Category category, HttpServletRequest request);
}