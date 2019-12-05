package com.karmanno.payments.dao.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.karmanno.payments.dao.UserDao;
import com.karmanno.payments.domain.User;
import com.karmanno.payments.mapper.UserMapper;
import lombok.SneakyThrows;

import java.util.List;

@Singleton
public class UserDaoImpl implements UserDao {
    private final UserMapper userMapper;

    @Inject
    public UserDaoImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
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

    @Override
    public User findById(Integer id) {
        return userMapper.selectById(id);
    }
}
