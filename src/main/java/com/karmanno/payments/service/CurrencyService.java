package com.karmanno.payments.service;

import com.karmanno.payments.domain.Currency;

public interface CurrencyService {
    Currency create(String currencyCode, String fullName, Integer minorUnits);
}
