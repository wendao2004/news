package com.message.news.pojo;

import lombok.Data;
import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
 * 用户表实体类
 */
@Data
public class User {
    @NotNull
    // ID
    private Integer id;
    // 用户名
    private String username;
    // 密码
    private String password;

    @NotEmpty
    @Pattern(regexp = "^\\S{1,10}$", message = "昵称必须是1-10个非空字符")
    // 昵称
    private String nickname;
    
    @NotEmpty
    @Email
    // 邮箱
    private String email;
    // 头像
    private String userPic;
    // 创建时间
    private LocalDateTime createTime;
    // 修改时间
    private LocalDateTime updateTime;
}