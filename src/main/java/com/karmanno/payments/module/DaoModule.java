package com.karmanno.payments.module;

import com.google.inject.AbstractModule;
import com.google.inject.Scope;
import com.google.inject.Scopes;
import com.karmanno.payments.dao.*;
import com.karmanno.payments.dao.impl.*;
import com.karmanno.payments.service.*;
import com.karmanno.payments.service.impl.*;

public class DaoModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(UserService.class).to(UserServiceImpl.class);
        bind(UserDao.class).to(UserDaoImpl.class);

        bind(CurrencyService.class).to(CurrencyServiceImpl.class);
        bind(CurrencyDao.class).to(CurrencyDaoImpl.class);

        bind(AccountService.class).to(AccountServiceImpl.class);
        bind(AccountDao.class).to(AccountDaoImpl.class);

        bind(CurrencyPriceService.class).to(CurrencyPriceServiceImpl.class);
        bind(CurrencyPriceDao.class).to(CurrencyPriceDaoImpl.class);

        bind(PaymentService.class).to(PaymentServiceImpl.class);
        bind(PaymentDao.class).to(PaymentDaoImpl.class);

        bind(PaymentProcessor.class).to(PaymentProcessorImpl.class);
    }
}
