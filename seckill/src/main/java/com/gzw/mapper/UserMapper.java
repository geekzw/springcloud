package com.gzw.mapper;

import com.gzw.daomain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by gujian on 2017/10/11.
 */
@Mapper
public interface UserMapper {
    @Select("select * from t_user where username= #{username}")
    User findByName(@Param("username") String username);

    @Insert("insert into t_user(username,password) values(#{username},#{password})")
    int register(@Param("username") String username,@Param("password") String password);
}
