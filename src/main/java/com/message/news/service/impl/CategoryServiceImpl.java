package com.message.news.service.impl;

import com.message.news.mapper.CategoryMapper;
import com.message.news.pojo.Category;
import com.message.news.pojo.User;
import com.message.news.service.CategoryService;
import com.message.news.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private UserService userService;

    // 新增：添加分类
    @Override
    public void addCategory(Category category, HttpServletRequest request) {
        // 1. 从拦截器获取登录用户名
        String username = (String) request.getAttribute("loginUsername");
        // 2. 查询用户ID
        User loginUser = userService.findByUserName(username);

        // 3. 填充所有必填字段
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        category.setCreateUser(loginUser.getId()); // 赋值创建人

        // 4. 插入数据库
        categoryMapper.addCategory(category);
    }

    // 新增：获取所有分类
    @Override
    public List<Category> list(HttpServletRequest request) {
        // 1. 从拦截器获取登录用户名
        String username = (String) request.getAttribute("loginUsername");
        // 2. 查询用户ID
        User loginUser = userService.findByUserName(username);
        return categoryMapper.list(loginUser.getId());
    }

    // 新增：根据id查询分类
    @Override
    public Category findById(Integer id) {
        Category category = categoryMapper.findById(id);
        return category;
    }

    // 新增：更新分类
    @Override
    public Category update(Category category, HttpServletRequest request) {
        // 1. 从拦截器获取登录用户名
        String username = (String) request.getAttribute("loginUsername");
        // 2. 查询用户ID
        User loginUser = userService.findByUserName(username);

        // 3. 填充所有必填字段
        category.setCreateUser(loginUser.getId()); // 赋值更新人
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.update(category);
        return category;
    }

    // 删除分类
    @Override
    public Category delete(Integer id, HttpServletRequest request) {
        // 1. 根据**前端传入的分类ID**查询分类（修复核心错误！）
        Category category = categoryMapper.findById(id);
        // 2. 校验分类是否存在
        if (category == null) {
            throw new RuntimeException("分类不存在");
        }
        // 3. 执行删除
        categoryMapper.delete(id);
        // 4. 返回被删除的分类
        return category;
    }

}