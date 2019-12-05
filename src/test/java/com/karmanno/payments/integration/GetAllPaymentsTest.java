package com.karmanno.payments.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.karmanno.payments.domain.Account;
import com.karmanno.payments.domain.Payment;
import com.karmanno.payments.domain.User;
import com.karmanno.payments.dto.MakePaymentRequest;
import com.karmanno.payments.dto.PutMoneyRequest;
import org.apache.http.HttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetAllPaymentsTest extends IntegrationTest {

    @DisplayName("Should get all payments by all filters")
    @Test
    public void shouldGetAllPayments() throws IOException {
        // given:
        User user1 = withUser("user1");
        User user2 = withUser("user2");
        withCurrency("Russian rubles", "RUR", 100);
        Account account1 = withAccount(user1.getId(), "RUR");
        Account account2 = withAccount(user2.getId(), "RUR");
        targetPut("account", new PutMoneyRequest().setAmount("2000.00").setAccountNumber(account1.getNumber()).setCurrencyCode("RUR"));
        MakePaymentRequest request = new MakePaymentRequest()
                .setAccountFrom(account1.getNumber())
                .setAccountTo(account2.getNumber())
                .setAmount("123.45");

        // when:
        targetPost("payment", request);
        targetPost("payment", request);
        targetPost("payment", request);
        HttpResponse response = targetGet("payment", Arrays.asList(
                new BasicNameValuePair("account", account1.getNumber())
        ));

        // then:
        assertEquals(200, response.getStatusLine().getStatusCode());
        List<Payment> payments = objectMapper.readValue(readJsonData(response), new TypeReference<List<Payment>>() {});
        assertEquals(3, payments.size());
    }

    @DisplayName("Should receive error when account does not exist")
    @Test
    public void shouldReceiveErrorWithIncorrectAccount() {
        // given:
        User user1 = withUser("user1");
        User user2 = withUser("user2");
        withCurrency("Russian rubles", "RUR", 100);
        Account account1 = withAccount(user1.getId(), "RUR");
        Account account2 = withAccount(user2.getId(), "RUR");
        targetPut("account", new PutMoneyRequest().setAmount("2000.00").setAccountNumber(account1.getNumber()).setCurrencyCode("RUR"));
        MakePaymentRequest request = new MakePaymentRequest()
                .setAccountFrom(account1.getNumber())
                .setAccountTo(account2.getNumber())
                .setAmount("123.45");

        // when:
        targetPost("payment", request);
        targetPost("payment", request);
        targetPost("payment", request);
        HttpResponse response = targetGet("payment", Arrays.asList(
                new BasicNameValuePair("account", "ddd")
        ));

        // then:
        assertEquals(404, response.getStatusLine().getStatusCode());
    }
}
