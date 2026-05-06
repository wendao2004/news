package com.message.news.pojo;

import lombok.Data;
import java.time.LocalDateTime;

import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.Pattern;

/**
 * 文章表实体类
 */
@Data
public class Article {
    // ID
    private Integer id;
    // 文章标题
    @Pattern(regexp = "^[\\u4e00-\\u9fa5a-zA-Z0-9]{1,50}$", message = "文章标题格式不正确")
    private String title;
    // 文章内容
    private String content;

    // 文章封面
    @URL(message = "文章封面格式不正确")
    private String coverImg;
    // 文章状态：已发布/草稿
    private String state;
    
    // 文章分类ID（关联category表id）
    private Integer categoryId;
    // 创建人ID（关联user表id）
    private Integer createUser;
    // 创建时间
    private LocalDateTime createTime;
    // 修改时间
    private LocalDateTime updateTime;
}