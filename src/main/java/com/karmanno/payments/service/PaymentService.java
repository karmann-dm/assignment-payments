package com.karmanno.payments.service;

import com.karmanno.payments.domain.Payment;

import java.util.List;

public interface PaymentService {
    Payment create(String from, String to, String amount);
    Payment get(Integer id);
    List<Payment> getPayments(String account);
}
