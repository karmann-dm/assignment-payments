package com.karmanno.payments.dao;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.karmanno.payments.domain.User;
import com.karmanno.payments.mapper.UserMapper;
import lombok.SneakyThrows;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

@Singleton
public class UserDaoImpl implements UserDao {
    private final UserMapper userMapper;
    private final SqlSessionFactory sqlSessionFactory;

    @Inject
    public UserDaoImpl(UserMapper userMapper, SqlSessionFactory sqlSessionFactory) {
        this.userMapper = userMapper;
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    @SneakyThrows
    public boolean existsByUsername(String username) {
        return userMapper.existsByUsername(username);
    }

    @Override
    @SneakyThrows
    public User create(String username) {
        int rowsInserted = userMapper.insertNewUser(new User().setUsername(username));
        if (rowsInserted == 1) {
            return userMapper.selectByUsername(username);
        }
        throw new RuntimeException("User was not saved");
    }

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }
}
