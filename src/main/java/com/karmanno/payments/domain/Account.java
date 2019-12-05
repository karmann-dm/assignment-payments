package com.karmanno.payments.domain;

import lombok.Data;

@Data
public class Account {
    private Integer id;
    private String number;
    private Integer userId;
    private Integer currencyId;
    private User user;
    private Currency currency;
    private Integer balance;

    private String balanceStr;

    public String getBalanceStr() {
        int major = balance / currency.getMinorUnits();
        int minor = balance - major * currency.getMinorUnits();
        balanceStr = String.format("%d.%02d", major, minor);
        return balanceStr;
    }
}
