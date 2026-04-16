package com.message.news.service;

import com.message.news.pojo.User;

/**
 * 用户查询接口（独立出来，只负责查询，不依赖登录策略）
 */
public interface UserQueryService {
    User findByUserName(String username);
}