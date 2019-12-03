package com.karmanno.payments.mapper;

import com.karmanno.payments.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper {
    @Select("select exists (select 1 from users where users.username = #{username})")
    boolean existsByUsername(@Param("username") String username);

    @Insert("insert into users values (#{id}, #{username})")
    @Options(keyColumn = "id", useGeneratedKeys = true)
    int insertNewUser(User user);

    @Select("select * from users")
    List<User> findAll();

    @Select("select * from users where users.username = #{username}")
    User selectByUsername(@Param("username") String username);
}
