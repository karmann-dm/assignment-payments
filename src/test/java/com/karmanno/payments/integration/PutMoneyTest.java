package com.karmanno.payments.integration;

import com.karmanno.payments.domain.Account;
import com.karmanno.payments.domain.Currency;
import com.karmanno.payments.domain.CurrencyPrice;
import com.karmanno.payments.domain.User;
import com.karmanno.payments.dto.PutMoneyRequest;
import org.apache.http.HttpResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class PutMoneyTest extends IntegrationTest {
    @DisplayName("Should put money to the account successfully")
    @Test
    public void shouldPutMoney() throws IOException {
        // given:
        User user = withUser("user");
        Currency currency = withCurrency("Dollar", "USD", 100);
        Account account = withAccount(user.getId(), currency.getCode());

        // when:
        HttpResponse response = targetPut("account", new PutMoneyRequest()
                .setCurrencyCode(currency.getCode())
                .setAccountNumber(account.getNumber())
                .setAmount("250.33")
        );

        // then:
        assertEquals(200, response.getStatusLine().getStatusCode());
        Account resultAccount = objectMapper.readValue(readJsonData(response), Account.class);
        assertEquals(25033, resultAccount.getBalance());
        assertEquals("250.33", resultAccount.getBalanceStr());
    }

    @DisplayName("Should receive error when currency does not exist")
    @Test
    public void shouldReceiveErrorWhenCurrencyDoesNotExist() {
        // given:
        User user = withUser("user");
        Currency currency = withCurrency("Dollar", "USD", 100);
        Account account = withAccount(user.getId(), currency.getCode());

        // when:
        HttpResponse response = targetPut("account", new PutMoneyRequest()
                .setCurrencyCode("LOL")
                .setAccountNumber(account.getNumber())
                .setAmount("250.33")
        );

        // then:
        assertEquals(404, response.getStatusLine().getStatusCode());
    }

    @DisplayName("Should put money to the account with conversion successfully")
    @Test
    public void shouldPutMoneyWithConversion() throws IOException {
        // given:
        User user = withUser("user");
        Currency currency = withCurrency("Euro", "EUR", 100);
        Currency fromCurrency = withCurrency("Dollar", "USD", 100);
        Account account = withAccount(user.getId(), currency.getCode());
        withPrice(fromCurrency.getCode(), currency.getCode(), "10.00");

        // when:
        HttpResponse response = targetPut("account", new PutMoneyRequest()
                .setCurrencyCode(fromCurrency.getCode())
                .setAccountNumber(account.getNumber())
                .setAmount("100.00")
        );

        // then:
        assertEquals(200, response.getStatusLine().getStatusCode());
        Account filledAccount = objectMapper.readValue(readJsonData(response), Account.class);
        assertEquals("1000.00", filledAccount.getBalanceStr());
    }

    @DisplayName("Should receive error when account does not exist")
    @Test
    public void getErrorWhenAccountDoesNotExist() {
        // given:
        User user = withUser("user");
        Currency currency = withCurrency("Euro", "EUR", 100);

        // when:
        HttpResponse response = targetPut("account", new PutMoneyRequest()
                .setCurrencyCode(currency.getCode())
                .setAccountNumber("123")
                .setAmount("250.33")
        );

        // then:
        assertEquals(404, response.getStatusLine().getStatusCode());
    }
}
