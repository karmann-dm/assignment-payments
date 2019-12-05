package com.karmanno.payments.util;

import com.karmanno.payments.domain.Amount;
import com.karmanno.payments.domain.Currency;
import com.karmanno.payments.domain.CurrencyPrice;

public class CurrencyConverter {
    public static Amount convert(Amount amount, Currency currencyFrom, Currency currencyTo, CurrencyPrice price) {
        Integer firstAmount = amount.getMajor() * currencyFrom.getMinorUnits() + amount.getMinor();
        Integer priceAmount = price.getPrice();
        Integer finalAmount = (firstAmount * priceAmount) / currencyTo.getMinorUnits();
        Integer major = finalAmount / currencyTo.getMinorUnits();
        return new Amount()
                .setMajor(major)
                .setMinor(finalAmount / currencyFrom.getMinorUnits() - major);
    }
}
