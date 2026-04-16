package com.message.news.pojo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 分类表实体类
 */
@Data
public class Category {
    // ID
    private Integer id;
    // 分类名称
    private String categoryName;
    // 分类别名
    private String categoryAlias;
    // 创建人ID（关联user表id）
    private Integer createUser;
    // 创建时间
    private LocalDateTime createTime;
    // 修改时间
    private LocalDateTime updateTime;
}