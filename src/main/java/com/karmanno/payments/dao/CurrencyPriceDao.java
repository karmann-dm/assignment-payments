package com.karmanno.payments.dao;

import com.karmanno.payments.domain.CurrencyPrice;

public interface CurrencyPriceDao {
    CurrencyPrice create(Integer currencyFrom, Integer currencyTo, Integer price);
    CurrencyPrice edit(Integer currencyFrom, Integer currencyTo, Integer price);
    CurrencyPrice get(Integer currencyFrom, Integer currencyTo);
}