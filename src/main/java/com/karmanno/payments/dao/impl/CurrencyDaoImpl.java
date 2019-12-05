package com.karmanno.payments.dao.impl;

import com.google.inject.Inject;
import com.karmanno.payments.dao.CurrencyDao;
import com.karmanno.payments.domain.Currency;
import com.karmanno.payments.mapper.CurrencyMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class CurrencyDaoImpl implements CurrencyDao {
    private final CurrencyMapper currencyMapper;
    private final SqlSessionFactory sqlSessionFactory;

    @Inject
    public CurrencyDaoImpl(CurrencyMapper currencyMapper,
                           SqlSessionFactory sqlSessionFactory) {
        this.currencyMapper = currencyMapper;
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public boolean existsByCode(String code) {
        return currencyMapper.existsByCode(code);
    }

    @Override
    public Currency create(Currency currency) {
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        int rowsUpdated = currencyMapper.insertNewCurrency(currency);
        if (rowsUpdated == 1) {
            return currencyMapper.selectByCode(currency.getCode());
        }
        sqlSession.close();
        throw new RuntimeException("Could not save currency");
    }

    @Override
    public Currency findByCode(String code) {
        return currencyMapper.selectByCode(code);
    }
}
