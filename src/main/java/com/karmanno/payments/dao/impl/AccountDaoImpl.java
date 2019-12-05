package com.karmanno.payments.dao.impl;

import com.google.inject.Inject;
import com.karmanno.payments.dao.AccountDao;
import com.karmanno.payments.domain.Account;
import com.karmanno.payments.mapper.AccountMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.TransactionIsolationLevel;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class AccountDaoImpl implements AccountDao {
    private final static int ACCOUNT_SIZE = 20;
    private final AccountMapper accountMapper;
    private final SqlSessionFactory sqlSessionFactory;

    @Inject
    public AccountDaoImpl(AccountMapper accountMapper, SqlSessionFactory sqlSessionFactory) {
        this.accountMapper = accountMapper;
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public Account create(Integer userId, Integer currencyId) {
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        String number = new Random().ints(0, 10)
                .limit(ACCOUNT_SIZE)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining(""));
        int insertedRows = accountMapper.insertNewAccount(
                new Account()
                    .setUserId(userId)
                    .setCurrencyId(currencyId)
                    .setNumber(number)
                    .setBalance(0)
        );
        if (insertedRows == 1) {
            return accountMapper.selectByNumber(number);
        }
        sqlSession.close();
        throw new RuntimeException("Could not save account");
    }

    @Override
    public Account putMoney(String number, Integer price) {
        SqlSession sqlSession = sqlSessionFactory.openSession(TransactionIsolationLevel.SERIALIZABLE);
        int updatedRows = accountMapper.updateAccount(
                price,
                number
        );
        if (updatedRows == 1) {
            return accountMapper.selectByNumber(number);
        }
        sqlSession.close();
        throw new RuntimeException("Could not put money");
    }

    @Override
    public Account findByNumber(String number) {
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        Account account = accountMapper.selectByNumber(number);
        sqlSession.close();
        return account;
    }

    @Override
    public List<Account> findAllByUser(Integer userId) {
        return accountMapper.selectByUserId(userId);
    }
}
