package com.karmanno.payments.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MakePaymentTest {
    @DisplayName("Should make payment with the same currencies")
    @Test
    public void shouldMakePaymentWithSameCurrencies() {
        throw new RuntimeException();
    }

    @DisplayName("Should make payment with different currencies")
    @Test
    public void shouldMakePaymentWithDifferentCurrencies() {
        throw new RuntimeException();
    }

    @DisplayName("Should make two payments from one account in the same time with X + Y <= Balance and both of them should succeed")
    @Test
    public void shouldMakeTwoPaymentsWithSuccess() {
        throw new RuntimeException();
    }

    @DisplayName("Should make two payments from one account in the same time with X + Y > Balance and the second one should fail")
    @Test
    public void shouldMakeTwoPaymentsWithError() {
        throw new RuntimeException();
    }

    @DisplayName("Should receive error when balance < X with the same currencies")
    @Test
    public void shouldReceiveErrorWhenBalanceIsNotEnough() {
        throw new RuntimeException();
    }

    @DisplayName("Should receive error when Convert(balance) < X with the same currencies")
    @Test
    public void shouldReceiveErrorWhenBalanceIsNotEnoughWithConversion() {
        throw new RuntimeException();
    }
}
