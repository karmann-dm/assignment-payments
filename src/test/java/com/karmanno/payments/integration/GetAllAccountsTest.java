package com.karmanno.payments.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.karmanno.payments.domain.Account;
import com.karmanno.payments.domain.Currency;
import com.karmanno.payments.domain.User;
import org.apache.http.HttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class GetAllAccountsTest extends IntegrationTest {
    @DisplayName("Should get all accounts by user")
    @Test
    public void shouldGetAllAccounts() throws IOException {
        // given:
        User user = withUser("user");
        Currency currency = withCurrency("US Dollar", "USD", 100);
        withAccount(user.getId(), currency.getCode());
        withAccount(user.getId(), currency.getCode());

        // when:
        HttpResponse response = targetGet("account", Arrays.asList(
                new BasicNameValuePair("userId", user.getId().toString())
        ));

        // then:
        assertEquals(201, response.getStatusLine().getStatusCode());
        List<Account> accounts = objectMapper.readValue(readJsonData(response), new TypeReference<List<Account>>(){});
        assertEquals(2, accounts.size());
        assertFalse(accounts.stream().anyMatch(Objects::isNull));
    }

    @DisplayName("Should receive error when user does not exist")
    @Test
    public void shouldReceiveErrorWhenUserDoesNotExist() {
        // given:
        User user = withUser("user");
        Currency currency = withCurrency("US Dollar", "USD", 100);
        withAccount(user.getId(), currency.getCode());
        withAccount(user.getId(), currency.getCode());

        // when:
        HttpResponse response = targetGet("account", Arrays.asList(
                new BasicNameValuePair("userId", Integer.toString(2))
        ));

        // then:
        assertEquals(404, response.getStatusLine().getStatusCode());
    }
}
