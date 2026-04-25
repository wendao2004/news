package com.message.news.controller;

import com.message.news.pojo.Category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.message.news.pojo.Result;
import com.message.news.service.CategoryService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // 添加分类
    @PostMapping
    public Result addCategory(@RequestBody Category category, HttpServletRequest request) {
        categoryService.addCategory(category, request);
        return Result.success();
    }

    // 获取所有分类
    @GetMapping
    public Result<List<Category>> list(HttpServletRequest request) {
        List<Category> cs = categoryService.list(request);
        return Result.success(cs);
    }

    // 根据id查询分类
    @GetMapping("/detail")
    public Result<Category> detail(Integer id) {
        Category category = categoryService.findById(id);
        return Result.success(category);
    }

    // 修改分类
    @PostMapping("/update")
    public Result update(@RequestBody @Validated Category category, HttpServletRequest request) {
        Category category2 = categoryService.update(category, request);
        return Result.success(category2);
    }

    // 删除分类
    @PostMapping("/delete")
    public Result delete(Integer id, HttpServletRequest request) {
        Category category = categoryService.delete(id, request);
        return Result.success(category);
    }
}