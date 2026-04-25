package com.message.news.mapper;

import com.message.news.pojo.Category;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CategoryMapper {
    // 插入全部必填字段
    @Insert("insert into category(category_name, category_alias, create_time, update_time, create_user) " +
            "values(#{categoryName}, #{categoryAlias}, #{createTime}, #{updateTime}, #{createUser})")
    void addCategory(Category category);

    // 新增：获取所有分类
    @Select("select * from category where create_user = #{id}")
    List<Category> list(Integer id);

    // 新增：根据id获取分类
    @Select("select * from category where id = #{id}")
    Category findById(Integer id);

    // 新增：更新分类
    @Select("update category set category_name = #{categoryName}, category_alias = #{categoryAlias} where id = #{id}")
    void update(Category category);

    // 新增：删除分类
    @Select("delete from category where id = #{id}")
    void delete(Integer id);
}