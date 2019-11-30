package com.karmanno.payments.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GetAllPaymentsTest {
    @DisplayName("Should get all payments by only STATUS filter")
    @Test
    public void shouldGetAllPaymentsByStatus() {
        throw new RuntimeException();
    }

    @DisplayName("Should get all payments by only DATE filter")
    @Test
    public void shouldGetAllPaymentsByDate() {
        throw new RuntimeException();
    }

    @DisplayName("Should get all payments by only TYPE filter")
    @Test
    public void shouldGetAllPaymentsByType() {
        throw new RuntimeException();
    }

    @DisplayName("Should get all payments by all filters")
    @Test
    public void shouldGetAllPayments() {
        throw new RuntimeException();
    }

    @DisplayName("Should receive error when STATUS does not exist")
    @Test
    public void shouldReceiveErrorWithIncorrectStatus() {
        throw new RuntimeException();
    }

    @DisplayName("Should receive error when dateFrom > dateTo")
    @Test
    public void shouldReceiveErrorWithIncorrectDate() {
        throw new RuntimeException();
    }

    @DisplayName("Should receive error when TYPE does not exist")
    @Test
    public void shouldReceiveErrorWithIncorrectType() {
        throw new RuntimeException();
    }
}
