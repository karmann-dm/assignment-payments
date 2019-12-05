package com.karmanno.payments.dao.impl;

import com.google.inject.Inject;
import com.karmanno.payments.dao.CurrencyPriceDao;
import com.karmanno.payments.domain.CurrencyPrice;
import com.karmanno.payments.mapper.CurrencyPriceMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class CurrencyPriceDaoImpl implements CurrencyPriceDao {
    private final CurrencyPriceMapper currencyPriceMapper;
    private final SqlSessionFactory sqlSessionFactory;

    @Inject
    public CurrencyPriceDaoImpl(CurrencyPriceMapper currencyPriceMapper, SqlSessionFactory sqlSessionFactory) {
        this.currencyPriceMapper = currencyPriceMapper;
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public CurrencyPrice create(Integer currencyFrom, Integer currencyTo, Integer price) {
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        int insertedRows = currencyPriceMapper.insertNewPrice(currencyFrom, currencyTo, price);
        if (insertedRows == 1) {
            return currencyPriceMapper.selectByCurrencies(currencyFrom, currencyTo);
        }
        sqlSession.close();
        throw new RuntimeException("Could not create currency");
    }

    @Override
    public CurrencyPrice edit(Integer currencyFrom, Integer currencyTo, Integer price) {
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        int updatedRows = currencyPriceMapper.updatePrice(currencyFrom, currencyTo, price);
        if (updatedRows == 1) {
            return currencyPriceMapper.selectByCurrencies(currencyFrom, currencyTo);
        }
        sqlSession.close();
        throw new RuntimeException("Could not update currency");
    }

    @Override
    public CurrencyPrice get(Integer currencyFrom, Integer currencyTo) {
        return currencyPriceMapper.selectByCurrencies(currencyFrom, currencyTo);
    }
}
