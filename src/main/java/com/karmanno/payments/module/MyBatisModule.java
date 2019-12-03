package com.karmanno.payments.module;

import com.google.inject.name.Names;
import com.karmanno.payments.dao.UserDao;
import com.karmanno.payments.dao.UserDaoImpl;
import com.karmanno.payments.domain.Currency;
import com.karmanno.payments.domain.User;
import com.karmanno.payments.mapper.CurrencyMapper;
import com.karmanno.payments.mapper.UserMapper;
import org.apache.ibatis.session.AutoMappingBehavior;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.guice.datasource.builtin.PooledDataSourceProvider;
import org.mybatis.guice.datasource.helper.JdbcHelper;

import java.util.Properties;

public class MyBatisModule extends org.mybatis.guice.MyBatisModule {
    @Override
    protected void initialize() {
        install(JdbcHelper.H2_IN_MEMORY_NAMED);

        bindDataSourceProviderType(PooledDataSourceProvider.class);
        bindTransactionFactoryType(JdbcTransactionFactory.class);

        addSimpleAlias(User.class);
        addSimpleAlias(Currency.class);
        addMapperClass(UserMapper.class);
        addMapperClass(CurrencyMapper.class);
        autoMappingBehavior(AutoMappingBehavior.FULL);

        Names.bindProperties(binder(), createProperties());

        bind(UserDao.class).to(UserDaoImpl.class);
    }

    private static Properties createProperties() {
        Properties myBatisProperties = new Properties();
        myBatisProperties.setProperty("mybatis.environment.id", "payments");
        myBatisProperties.setProperty("JDBC.username", "sa");
        myBatisProperties.setProperty("JDBC.password", "");
        myBatisProperties.setProperty("JDBC.autoCommit", "false");
        return myBatisProperties;
    }
}
