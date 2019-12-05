package com.karmanno.payments.service.impl;

import com.google.inject.Inject;
import com.karmanno.payments.dao.CurrencyDao;
import com.karmanno.payments.domain.Currency;
import com.karmanno.payments.exception.FullNameIncorrectException;
import com.karmanno.payments.exception.MinorUnitsIncorrectException;
import com.karmanno.payments.exception.MnemonicCodeIncorrectException;
import com.karmanno.payments.service.CurrencyService;

public class CurrencyServiceImpl implements CurrencyService {
    private final CurrencyDao currencyDao;

    @Inject
    public CurrencyServiceImpl(CurrencyDao currencyDao) {
        this.currencyDao = currencyDao;
    }

    @Override
    public Currency create(String currencyCode, String fullName, Integer minorUnits) {
        Currency currency = new Currency()
                .setCode(currencyCode)
                .setFullName(fullName)
                .setMinorUnits(minorUnits);
        validateCurrency(currency);
        return currencyDao.create(currency);
    }

    private void validateCurrency(Currency currency) {
        if (currency.getFullName() == null || currency.getFullName().isEmpty()) {
            throw new FullNameIncorrectException();
        }
        if (currency.getCode() == null || currency.getCode().isEmpty()) {
            throw new MnemonicCodeIncorrectException();
        }
        if (currency.getMinorUnits() == null || currency.getMinorUnits() < 0) {
            throw new MinorUnitsIncorrectException();
        }
    }
}
