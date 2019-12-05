package com.karmanno.payments.service.impl;

import com.google.inject.Inject;
import com.karmanno.payments.dao.AccountDao;
import com.karmanno.payments.dao.CurrencyDao;
import com.karmanno.payments.dao.CurrencyPriceDao;
import com.karmanno.payments.dao.UserDao;
import com.karmanno.payments.domain.*;
import com.karmanno.payments.exception.AccountDoesNotExistException;
import com.karmanno.payments.exception.CurrencyDoesNotExistException;
import com.karmanno.payments.exception.InvalidFormatException;
import com.karmanno.payments.exception.UserDoesNotExistException;
import com.karmanno.payments.service.AccountService;
import com.karmanno.payments.util.CurrencyConverter;

import java.util.List;

public class AccountServiceImpl implements AccountService {
    private final AccountDao accountDao;
    private final CurrencyDao currencyDao;
    private final CurrencyPriceDao currencyPriceDao;
    private final UserDao userDao;

    @Inject
    public AccountServiceImpl(AccountDao accountDao,
                              CurrencyDao currencyDao,
                              CurrencyPriceDao currencyPriceDao,
                              UserDao userDao) {
        this.accountDao = accountDao;
        this.currencyDao = currencyDao;
        this.currencyPriceDao = currencyPriceDao;
        this.userDao = userDao;
    }

    @Override
    public Account create(Integer userId, String currencyCode) {
        Currency currency = currencyDao.findByCode(currencyCode);
        validateCurrency(currency);

        User user = userDao.findById(userId);
        validateUser(user);

        return accountDao.create(
                userId,
                currency.getId()
        );
    }

    @Override
    public Account update(String number, String currencyCode, String amount) {
        Currency currency = currencyDao.findByCode(currencyCode);
        validateCurrency(currency);

        Account account = accountDao.findByNumber(number);
        Amount currencyAmount;
        validateAccount(account);
        try {
            currencyAmount = Amount.fromString(amount);
        } catch (Exception e) {
            throw new InvalidFormatException();
        }

        Integer price = calculatePrice(account, currency, currencyAmount);
        return accountDao.putMoney(account.getNumber(), price);
    }

    private Integer calculatePrice(Account account, Currency currency, Amount currencyAmount) {
        if (currency.getCode().equals(account.getCurrency().getCode())) {
            return currencyAmount.getMajor() * currency.getMinorUnits() + currencyAmount.getMinor();
        } else {
            CurrencyPrice currencyPrice = currencyPriceDao.get(currency.getId(), account.getCurrency().getId());
            Amount convertedAmount = CurrencyConverter.convert(currencyAmount, currency, account.getCurrency(), currencyPrice);
            return convertedAmount.getMajor() * account.getCurrency().getMinorUnits() + convertedAmount.getMinor();
        }
    }

    @Override
    public List<Account> findAllByUser(Integer userId) {
        User user = userDao.findById(userId);
        validateUser(user);
        return accountDao.findAllByUser(userId);
    }

    private void validateUser(User user) {
        if (user == null)
            throw new UserDoesNotExistException();
    }

    private void validateCurrency(Currency currency) {
        if (currency == null)
            throw new CurrencyDoesNotExistException();
    }

    private void validateAccount(Account account) {
        if (account == null)
            throw new AccountDoesNotExistException();
    }
}
