package com.message.news.controller;

import com.message.news.pojo.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.message.news.pojo.Result;
import com.message.news.service.CategoryService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // 新增 HttpServletRequest 参数
    @PostMapping
    public Result addCategory(@RequestBody Category category, HttpServletRequest request) {
        categoryService.addCategory(category, request);
        return Result.success();
    }
}