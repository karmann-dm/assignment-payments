package com.karmanno.payments.domain;

import lombok.Data;

@Data
public class CurrencyPrice {
    private Integer from;
    private Integer to;
    private Integer price;
    private Currency currencyFrom;
    private Currency currencyTo;

    private String priceStr;

    public String getPriceStr() {
        int major = price / currencyFrom.getMinorUnits();
        int minor = price - major * currencyFrom.getMinorUnits();
        priceStr = String.format("%d.%02d", major, minor);
        return priceStr;
    }
}
