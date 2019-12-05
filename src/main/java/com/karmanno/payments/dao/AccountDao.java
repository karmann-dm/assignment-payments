package com.karmanno.payments.dao;

import com.karmanno.payments.domain.Account;

import java.util.List;

public interface AccountDao {
    Account create(Integer userId, Integer currencyId);
    Account putMoney(String accountNumber, Integer price);
    List<Account> findAllByUser(Integer userId);
    Account findByNumber(String number);
}
