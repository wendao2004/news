package com.message.news.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.message.news.pojo.Article;

@Mapper
public interface ArticleMapper {

    // 新增：添加文章
    @Insert("insert into article(title, content,cover_img, state,category_id,create_user,create_time,update_time) " +
            "values(#{title}, #{content}, #{coverImg}, #{state}, #{categoryId}, #{createUser}, #{createTime}, #{updateTime})")
    void add(Article article);

    // 新增：获取文章列表
    @Select("SELECT a.*, c.category_name FROM article a " +
            "LEFT JOIN category c ON a.category_id = c.id " +
            "ORDER BY a.create_time DESC")
    List<Article> selectArticleList();
}
