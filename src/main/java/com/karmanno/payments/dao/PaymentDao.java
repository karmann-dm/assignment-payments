package com.karmanno.payments.dao;

import com.karmanno.payments.domain.Payment;

import java.util.List;

public interface PaymentDao {
    Payment create(String from, String to, Integer calculated);
    Payment updateWithStatus(Integer status, Integer paymentId);
    Payment getById(Integer id);
    List<Payment> getAllPayments(String account);
    Payment getLast();
}
