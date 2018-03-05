package com.miaosha.dao;

import com.miaosha.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by enum on 2018/3/4.
 */
@Mapper
public interface UserDao {

    @Select("select * from user where id = #{id}")
    User getByUserId(@Param("id") int id);

    @Insert("insert into user(id, name) values(#{id}, #{name})")
    int insert(User user);
}
