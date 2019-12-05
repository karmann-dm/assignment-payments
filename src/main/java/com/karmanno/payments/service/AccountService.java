package com.karmanno.payments.service;

import com.karmanno.payments.domain.Account;

import java.util.List;

public interface AccountService {
    Account create(Integer userId, String currencyCode);
    Account update(String number, String currencyCode, String amount);
    List<Account> findAllByUser(Integer userId);
}
