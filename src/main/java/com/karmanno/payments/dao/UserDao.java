package com.karmanno.payments.dao;

import com.karmanno.payments.domain.User;

import java.util.List;

public interface UserDao {
    boolean existsByUsername(String username);
    User create(String username);
    List<User> findAll();
}
