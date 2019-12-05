package com.karmanno.payments.service;

import com.karmanno.payments.domain.Payment;

public interface PaymentProcessor {
    Payment process(Payment payment);
}
