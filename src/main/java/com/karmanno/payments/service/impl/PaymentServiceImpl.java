package com.karmanno.payments.service.impl;

import com.google.inject.Inject;
import com.karmanno.payments.dao.AccountDao;
import com.karmanno.payments.dao.PaymentDao;
import com.karmanno.payments.domain.Account;
import com.karmanno.payments.domain.Amount;
import com.karmanno.payments.domain.Payment;
import com.karmanno.payments.exception.AccountDoesNotExistException;
import com.karmanno.payments.exception.InvalidFormatException;
import com.karmanno.payments.service.PaymentProcessor;
import com.karmanno.payments.service.PaymentService;

import java.util.List;

public class PaymentServiceImpl implements PaymentService {
    private final PaymentDao paymentDao;
    private final AccountDao accountDao;
    private final PaymentProcessor paymentProcessor;

    @Inject
    public PaymentServiceImpl(PaymentDao paymentDao, AccountDao accountDao, PaymentProcessor paymentProcessor) {
        this.paymentDao = paymentDao;
        this.accountDao = accountDao;
        this.paymentProcessor = paymentProcessor;
    }

    @Override
    public Payment create(String from, String to, String amount) {
        Amount amountFrom;
        try {
            amountFrom = Amount.fromString(amount);
        } catch (Exception e) {
            throw new InvalidFormatException();
        }
        Account accountFrom = accountDao.findByNumber(from);
        Account accountTo = accountDao.findByNumber(to);
        validateAccount(accountFrom);
        validateAccount(accountTo);

        Payment payment = paymentDao.create(
                from,
                to,
                amountFrom.getMajor() * accountFrom.getCurrency().getMinorUnits() + amountFrom.getMinor()
        );
        return paymentProcessor.process(payment);
    }

    @Override
    public Payment get(Integer id) {
        return paymentDao.getById(id);
    }

    @Override
    public List<Payment> getPayments(String account) {
        Account fetched = accountDao.findByNumber(account);
        validateAccount(fetched);
        return paymentDao.getAllPayments(account);
    }

    private void validateAccount(Account account) {
        if (account == null)
            throw new AccountDoesNotExistException();
    }
}
