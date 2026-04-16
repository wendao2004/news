package com.message.news.login.handler;

import com.message.news.pojo.User;

/**
 * 登录后处理器接口（对扩展开放：未来加Token生成/日志记录/通知只需新增实现类）
 */
public interface LoginPostHandler {
    /**
     * 执行登录后处理
     * @param user 登录成功的用户
     * @return 处理结果（如Token、日志ID等）
     */
    Object handle(User user);
}