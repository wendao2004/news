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

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private UserService userService;

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
}