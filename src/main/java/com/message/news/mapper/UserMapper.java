package com.message.news.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.message.news.pojo.User;

@Mapper
public class UserMapper {

    @Insert("insert into user(username,password,created_time,updated_time) values(#{username},#{password},now(),now())")
    // 添加用户
    public void add(String username, String password) {

    }

    @Select("select * from user where username=#{username}")
    public
    // 根据用户名查询用户
    User findByUserName(String username) {
        return null;
    }

}
