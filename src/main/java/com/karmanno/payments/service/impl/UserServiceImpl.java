package com.karmanno.payments.service.impl;

import com.google.inject.Inject;
import com.karmanno.payments.dao.UserDao;
import com.karmanno.payments.domain.User;
import com.karmanno.payments.exception.UserExistException;
import com.karmanno.payments.exception.UsernameIsEmptyException;
import com.karmanno.payments.exception.UsernameIsNullException;
import com.karmanno.payments.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @Inject
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User create(String username) {
        validateUsername(username);
        return userDao.create(username);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    private void validateUsername(String username) {
        if (username == null) {
            throw new UsernameIsNullException();
        }
        if (username.isEmpty()) {
            throw new UsernameIsEmptyException();
        }
        if (userDao.existsByUsername(username)) {
            throw new UserExistException();
        }
    }
}
