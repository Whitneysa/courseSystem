package com.course.mapper;

import com.course.pojo.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Select("select * from user where id = #{id}")
    User getUserById(@Param("id") Long id);

    @Insert("insert into user(id, username, password, role) values(#{id}, #{username}, #{password}, #{role})")
    void inert(User user);


    @Select("select * from user where username = #{username}")
    User getUserByUsername(@Param("username") String username);

    @Update("update user set is_online = 1 where id = #{id}")
    void update(@Param("id") Long id);
}
