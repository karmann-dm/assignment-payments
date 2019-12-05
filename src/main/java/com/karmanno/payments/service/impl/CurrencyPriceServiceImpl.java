package com.karmanno.payments.service.impl;

import com.google.inject.Inject;
import com.karmanno.payments.dao.CurrencyDao;
import com.karmanno.payments.dao.CurrencyPriceDao;
import com.karmanno.payments.domain.Amount;
import com.karmanno.payments.domain.Currency;
import com.karmanno.payments.domain.CurrencyPrice;
import com.karmanno.payments.exception.CurrencyDoesNotExistException;
import com.karmanno.payments.exception.InvalidFormatException;
import com.karmanno.payments.exception.PriceDoesNotExistException;
import com.karmanno.payments.service.CurrencyPriceService;

import java.util.Optional;

public class CurrencyPriceServiceImpl implements CurrencyPriceService {
    private final CurrencyDao currencyDao;
    private final CurrencyPriceDao currencyPriceDao;

    @Inject
    public CurrencyPriceServiceImpl(CurrencyDao currencyDao, CurrencyPriceDao currencyPriceDao) {
        this.currencyDao = currencyDao;
        this.currencyPriceDao = currencyPriceDao;
    }

    @Override
    public CurrencyPrice create(String from, String to, String price) {
        Currency fromCurrency = currencyDao.findByCode(from);
        Currency toCurrency = currencyDao.findByCode(to);
        validateCurrency(fromCurrency);
        validateCurrency(toCurrency);
        Amount amount = validateAmount(price);
        Integer calculatedPrice = amount.getMajor() * fromCurrency.getMinorUnits() + amount.getMinor();
        return currencyPriceDao.create(fromCurrency.getId(), toCurrency.getId(), calculatedPrice);
    }

    @Override
    public CurrencyPrice edit(String from, String to, String price) {
        Currency fromCurrency = currencyDao.findByCode(from);
        Currency toCurrency = currencyDao.findByCode(to);
        validateCurrency(fromCurrency);
        validateCurrency(toCurrency);
        Amount amount = validateAmount(price);
        Integer calculatedPrice = amount.getMajor() * fromCurrency.getMinorUnits() + amount.getMinor();
        return currencyPriceDao.edit(fromCurrency.getId(), toCurrency.getId(), calculatedPrice);
    }

    @Override
    public CurrencyPrice get(String from, String to) {
        Currency fromCurrency = currencyDao.findByCode(from);
        Currency toCurrency = currencyDao.findByCode(to);
        validateCurrency(fromCurrency);
        validateCurrency(toCurrency);
        CurrencyPrice price = currencyPriceDao.get(fromCurrency.getId(), toCurrency.getId());
        if (price == null)
            throw new PriceDoesNotExistException();
        return price;
    }

    private void validateCurrency(Currency currency) {
        if (currency == null)
            throw new CurrencyDoesNotExistException();
    }

    private Amount validateAmount(String amountStr) {
        Amount amount;
        try {
            amount = Amount.fromString(amountStr);
        } catch (Exception e) {
            throw new InvalidFormatException();
        }
        return amount;
    }
}
