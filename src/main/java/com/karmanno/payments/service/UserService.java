package com.karmanno.payments.service;

import com.karmanno.payments.domain.User;

import java.util.List;

public interface UserService {
    User create(String username);
    List<User> findAll();
}
