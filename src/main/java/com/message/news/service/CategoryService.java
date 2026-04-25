package com.message.news.service;

import java.util.List;

import com.message.news.pojo.Category;
import jakarta.servlet.http.HttpServletRequest;

public interface CategoryService {
    // 新增：添加分类
    void addCategory(Category category, HttpServletRequest request);

    // 新增：获取所有分类
    List<Category> list(HttpServletRequest request);

    // 新增：根据id查询分类
    Category findById(Integer id);

    // 修改分类
    Category update(Category category, HttpServletRequest request);

    // 删除分类
    Category delete(Integer id, HttpServletRequest request);
}