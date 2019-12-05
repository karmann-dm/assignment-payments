package com.karmanno.payments.service.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.karmanno.payments.dao.AccountDao;
import com.karmanno.payments.dao.CurrencyPriceDao;
import com.karmanno.payments.dao.PaymentDao;
import com.karmanno.payments.domain.*;
import com.karmanno.payments.service.PaymentProcessor;
import com.karmanno.payments.util.CurrencyConverter;

@Singleton
public class PaymentProcessorImpl implements PaymentProcessor {
    private final PaymentDao paymentDao;
    private final AccountDao accountDao;
    private final CurrencyPriceDao currencyPriceDao;

    @Inject
    public PaymentProcessorImpl(PaymentDao paymentDao, AccountDao accountDao, CurrencyPriceDao currencyPriceDao) {
        this.paymentDao = paymentDao;
        this.accountDao = accountDao;
        this.currencyPriceDao = currencyPriceDao;
    }

    @Override
    public Payment process(Payment payment) {
        if (payment != null) {
            Account from = payment.getAccountFrom();
            Account to = payment.getAccountTo();
            int major = payment.getPrice() / from.getCurrency().getMinorUnits();
            int minor = payment.getPrice() - major * from.getCurrency().getMinorUnits();
            Amount withdrawFrom = new Amount()
                    .setMajor(major)
                    .setMinor(minor);
            Integer newBalance = from.getBalance() - withdrawFrom.getMajor() * from.getCurrency().getMinorUnits() + withdrawFrom.getMinor();
            if (newBalance < 0) {
                return paymentDao.updateWithStatus(PaymentStatus.FAILED.getValue(), payment.getId());
            }

            Integer addTo = calculatePrice(to, from.getCurrency(), withdrawFrom);
            try {
                accountDao.putMoney(
                        from.getNumber(),
                        newBalance
                );
                accountDao.putMoney(
                        to.getNumber(),
                        to.getBalance() + addTo
                );
            } catch (RuntimeException e) {
                return paymentDao.updateWithStatus(PaymentStatus.FAILED.getValue(), payment.getId());
            }
            return paymentDao.updateWithStatus(PaymentStatus.COMPLETED.getValue(), payment.getId());
        }
        throw new RuntimeException("Payment not found");
    }

    private Integer calculatePrice(Account account, Currency currency, Amount currencyAmount) {
        if (currency.getId().equals(account.getCurrency().getId())) {
            return currencyAmount.getMajor() * currency.getMinorUnits() + currencyAmount.getMinor();
        } else {
            CurrencyPrice currencyPrice = currencyPriceDao.get(currency.getId(), account.getCurrency().getId());
            Amount convertedAmount = CurrencyConverter.convert(currencyAmount, currency, account.getCurrency(), currencyPrice);
            return convertedAmount.getMajor() * account.getCurrency().getMinorUnits() + convertedAmount.getMinor();
        }
    }
}
