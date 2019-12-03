package com.karmanno.payments.module;

import com.google.inject.AbstractModule;
import com.karmanno.payments.dao.UserDao;
import com.karmanno.payments.dao.UserDaoImpl;
import com.karmanno.payments.service.UserService;
import com.karmanno.payments.service.impl.UserServiceImpl;

public class UserModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(UserService.class).to(UserServiceImpl.class);
        bind(UserDao.class).to(UserDaoImpl.class);
    }
}
