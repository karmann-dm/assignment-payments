package com.karmanno.payments.service;

import com.karmanno.payments.domain.CurrencyPrice;

public interface CurrencyPriceService {
    CurrencyPrice create(String from, String to, String price);
    CurrencyPrice edit(String from, String to, String price);
    CurrencyPrice get(String from, String to);
}
