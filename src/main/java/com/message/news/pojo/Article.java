package com.message.news.pojo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 文章表实体类
 */
@Data
public class Article {
    // ID
    private Integer id;
    // 文章标题
    private String title;
    // 文章内容
    private String content;
    // 文章封面
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