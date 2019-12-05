package com.karmanno.payments.integration;

import com.karmanno.payments.domain.Account;
import com.karmanno.payments.domain.Currency;
import com.karmanno.payments.domain.User;
import com.karmanno.payments.dto.CreateAccountRequest;
import org.apache.http.HttpResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class CreateAccountTest extends IntegrationTest {

    @DisplayName("Should create account successfully")
    @Test
    public void createAccount() throws IOException {
        // given:
        User user = withUser("user");
        Currency currency = withCurrency("Dollars", "USD", 100);
        CreateAccountRequest request = new CreateAccountRequest()
                .setCurrencyCode(currency.getCode())
                .setUserId(user.getId());

        // when:
        HttpResponse response = targetPost("account", request);
        String jsonAccount = readJsonData(response);

        // then:
        assertEquals(201, response.getStatusLine().getStatusCode());
        Account account = objectMapper.readValue(jsonAccount, Account.class);
        assertEquals(request.getCurrencyCode(), account.getCurrency().getCode());
        assertEquals(request.getUserId(), account.getUser().getId());
    }

    @DisplayName("Should receive error when user does not exist")
    @Test
    public void receiveErrorWhenUserDoesNotExist() throws IOException {
        // given:
        CreateAccountRequest request = new CreateAccountRequest()
                .setCurrencyCode("USD")
                .setUserId(2);

        // when:
        HttpResponse response = targetPost("account", request);

        // then:
        assertEquals(404, response.getStatusLine().getStatusCode());
    }

    @DisplayName("Should receive error when currency does not exist")
    @Test
    public void shouldReceiveErrorWhenCurrencyDoesNotExist() throws IOException {
        // given:
        CreateAccountRequest request = new CreateAccountRequest()
                .setCurrencyCode("LOL")
                .setUserId(1);

        // when:
        HttpResponse response = targetPost("account", request);

        // then:
        assertEquals(404, response.getStatusLine().getStatusCode());
    }
}
