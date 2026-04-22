package com.message.news.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.message.news.pojo.User;

@Mapper
public interface UserMapper {

    @Select("select * from user where username=#{username}")
    // 根据用户名查询用户
    User findByUserName(String username);

    @Insert("insert into user(username,password,create_time,update_time) values(#{username},#{password},now(),now())")
    // 添加用户
    void add(String username, String password);

    // 更新用户信息
    @Update("update user set nickname=#{nickname},email=#{email},update_time=#{updateTime} where id=#{id}")
    void update(User user);
}
