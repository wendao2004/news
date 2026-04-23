package com.message.news.mapper;

import com.message.news.pojo.Category;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper {
    // 插入全部必填字段
    @Insert("insert into category(category_name, category_alias, create_time, update_time, create_user) " +
            "values(#{categoryName}, #{categoryAlias}, #{createTime}, #{updateTime}, #{createUser})")
    void addCategory(Category category);
}