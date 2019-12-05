package com.karmanno.payments.dao;

import com.karmanno.payments.domain.Currency;

import java.util.List;

public interface CurrencyDao {
    boolean existsByCode(String code);
    Currency create(Currency currency);
    Currency findByCode(String code);
}
