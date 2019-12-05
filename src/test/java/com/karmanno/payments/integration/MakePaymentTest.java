package com.karmanno.payments.integration;

import com.karmanno.payments.domain.Account;
import com.karmanno.payments.domain.Payment;
import com.karmanno.payments.domain.PaymentStatus;
import com.karmanno.payments.domain.User;
import com.karmanno.payments.dto.MakePaymentRequest;
import com.karmanno.payments.dto.PutMoneyRequest;
import lombok.SneakyThrows;
import org.apache.http.HttpResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MakePaymentTest extends IntegrationTest {
    @DisplayName("Should fail payment when no money on from account")
    @Test
    @SneakyThrows
    public void shouldCreateFail() throws IOException {
        // given:
        User user1 = withUser("user1");
        User user2 = withUser("user2");
        withCurrency("Russian rubles", "RUR", 100);
        Account account1 = withAccount(user1.getId(), "RUR");
        Account account2 = withAccount(user2.getId(), "RUR");
        MakePaymentRequest request = new MakePaymentRequest()
                .setAccountFrom(account1.getNumber())
                .setAccountTo(account2.getNumber())
                .setAmount("123.45");

        // when:
        HttpResponse response = targetPost("payment", request);

        // then:
        assertEquals(201, response.getStatusLine().getStatusCode());
        Payment payment = objectMapper.readValue(readJsonData(response), Payment.class);
        assertEquals(account1.getNumber(), payment.getAccountFrom().getNumber());
        assertEquals(account2.getNumber(), payment.getAccountTo().getNumber());
        assertEquals(12345, payment.getPrice());
        assertEquals(PaymentStatus.FAILED.getValue(), payment.getStatus());
    }

    @DisplayName("Should make payment with the same currencies")
    @Test
    public void shouldMakePaymentWithSameCurrencies() throws IOException {
        // given:
        User user1 = withUser("user1");
        User user2 = withUser("user2");
        withCurrency("Russian rubles", "RUR", 100);
        Account account1 = withAccount(user1.getId(), "RUR");
        Account account2 = withAccount(user2.getId(), "RUR");
        targetPut("account", new PutMoneyRequest().setAmount("200.00").setAccountNumber(account1.getNumber()).setCurrencyCode("RUR"));
        MakePaymentRequest request = new MakePaymentRequest()
                .setAccountFrom(account1.getNumber())
                .setAccountTo(account2.getNumber())
                .setAmount("123.45");

        // when:
        HttpResponse response = targetPost("payment", request);

        // then:
        assertEquals(201, response.getStatusLine().getStatusCode());
        Payment payment = objectMapper.readValue(readJsonData(response), Payment.class);
        assertEquals(account1.getNumber(), payment.getAccountFrom().getNumber());
        assertEquals(account2.getNumber(), payment.getAccountTo().getNumber());
        assertEquals(PaymentStatus.COMPLETED.getValue(), payment.getStatus());
        Account first = getAccount(account1.getNumber());
        Account second = getAccount(account2.getNumber());
        assertEquals(7745, first.getBalance());
        assertEquals(12345, second.getBalance());
    }

    @DisplayName("Should make payment with different currencies")
    @Test
    public void shouldMakePaymentWithDifferentCurrencies() throws IOException {
        // given:
        User user1 = withUser("user1");
        User user2 = withUser("user2");
        withCurrency("Russian rubles", "RUR", 100);
        withCurrency("US dollars", "USD", 100);
        withPrice("USD", "RUR", "60.00");
        Account account1 = withAccount(user1.getId(), "USD");
        Account account2 = withAccount(user2.getId(), "RUR");
        targetPut("account", new PutMoneyRequest().setAmount("100.00").setAccountNumber(account1.getNumber()).setCurrencyCode("USD"));
        MakePaymentRequest request = new MakePaymentRequest()
                .setAccountFrom(account1.getNumber())
                .setAccountTo(account2.getNumber())
                .setAmount("10.00");

        // when:
        HttpResponse response = targetPost("payment", request);

        // then:
        assertEquals(201, response.getStatusLine().getStatusCode());
        Payment payment = objectMapper.readValue(readJsonData(response), Payment.class);
        assertEquals(account1.getNumber(), payment.getAccountFrom().getNumber());
        assertEquals(account2.getNumber(), payment.getAccountTo().getNumber());
        assertEquals(PaymentStatus.COMPLETED.getValue(), payment.getStatus());
        Account first = getAccount(account1.getNumber());
        Account second = getAccount(account2.getNumber());
        assertEquals(9000, first.getBalance());
        assertEquals(60000, second.getBalance());
    }

    @DisplayName("Should receive error when FROM account does not exist")
    @Test
    public void getErrorWhenFromDoesNotExist() throws IOException {
        // given:
        User user1 = withUser("user1");
        User user2 = withUser("user2");
        withCurrency("Russian rubles", "RUR", 100);
        Account account1 = withAccount(user1.getId(), "RUR");
        Account account2 = withAccount(user2.getId(), "RUR");
        targetPut("account", new PutMoneyRequest().setAmount("200.00").setAccountNumber(account1.getNumber()).setCurrencyCode("RUR"));
        MakePaymentRequest request = new MakePaymentRequest()
                .setAccountFrom("")
                .setAccountTo(account2.getNumber())
                .setAmount("123.45");

        // when:
        HttpResponse response = targetPost("payment", request);

        // then:
        assertEquals(404, response.getStatusLine().getStatusCode());
    }

    @DisplayName("Should receive error when TO account does not exist")
    @Test
    public void getErrorWhenToDoesNotExist() throws IOException {
        // given:
        User user1 = withUser("user1");
        User user2 = withUser("user2");
        withCurrency("Russian rubles", "RUR", 100);
        Account account1 = withAccount(user1.getId(), "RUR");
        Account account2 = withAccount(user2.getId(), "RUR");
        targetPut("account", new PutMoneyRequest().setAmount("200.00").setAccountNumber(account1.getNumber()).setCurrencyCode("RUR"));
        MakePaymentRequest request = new MakePaymentRequest()
                .setAccountFrom(account1.getNumber())
                .setAccountTo("")
                .setAmount("123.45");

        // when:
        HttpResponse response = targetPost("payment", request);

        // then:
        assertEquals(404, response.getStatusLine().getStatusCode());
    }
}
