package com.karmanno.payments.integration;

import com.karmanno.payments.domain.CurrencyPrice;
import org.apache.http.HttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetCurrencyPriceTest extends IntegrationTest {
    @DisplayName("Should get currency price successfully")
    @Test
    public void getCurrencyPrice() throws IOException {
        // given:
        withCurrency("US Dollar", "USD", 100);
        withCurrency("Euro", "EUR", 100);
        CurrencyPrice price = withPrice("USD", "EUR", "12.45");

        // when:
        HttpResponse response = targetGet("price", Arrays.asList(
                new BasicNameValuePair("from", "USD"),
                new BasicNameValuePair("to", "EUR")
        ));

        // then:
        assertEquals(200, response.getStatusLine().getStatusCode());
        CurrencyPrice currencyPrice = objectMapper.readValue(readJsonData(response), CurrencyPrice.class);
        assertEquals("12.45", currencyPrice.getPriceStr());
        assertEquals("USD", currencyPrice.getCurrencyFrom().getCode());
        assertEquals("EUR", currencyPrice.getCurrencyTo().getCode());
    }

    @DisplayName("Should receive an error when currency FROM does not exists")
    @Test
    public void getErrorWhenFromDoesNotExist() {
        // given:
        withCurrency("US Dollar", "USD", 100);
        withCurrency("Euro", "EUR", 100);
        withPrice("USD", "EUR", "12.45");

        // when:
        HttpResponse response = targetGet("price", Arrays.asList(
                new BasicNameValuePair("from", "PPR"),
                new BasicNameValuePair("to", "EUR")
        ));

        // then:
        assertEquals(404, response.getStatusLine().getStatusCode());
    }

    @DisplayName("Should receive an error when currency TO does not exists")
    @Test
    public void getErrorWhenToDoesNotExist() {
        // given:
        withCurrency("US Dollar", "USD", 100);
        withCurrency("Euro", "EUR", 100);
        withPrice("USD", "EUR", "12.45");

        // when:
        HttpResponse response = targetGet("price", Arrays.asList(
                new BasicNameValuePair("from", "USD"),
                new BasicNameValuePair("to", "PRT")
        ));

        // then:
        assertEquals(404, response.getStatusLine().getStatusCode());
    }

    @DisplayName("Should receive an error when price does not exists")
    @Test
    public void getErrorWhenPriceDoesNotExist() {
        // given:
        withCurrency("US Dollar", "USD", 100);
        withCurrency("Euro", "EUR", 100);

        // when:
        HttpResponse response = targetGet("price", Arrays.asList(
                new BasicNameValuePair("from", "USD"),
                new BasicNameValuePair("to", "EUR")
        ));

        // then:
        assertEquals(404, response.getStatusLine().getStatusCode());
    }
}
