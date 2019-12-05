package com.karmanno.payments.domain;

public enum  PaymentStatus {
    PROCESSING(0),
    COMPLETED(1),
    FAILED(2);

    private final int value;

    PaymentStatus(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
