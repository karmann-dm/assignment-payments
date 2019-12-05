package com.karmanno.payments.integration;

import com.karmanno.payments.domain.Currency;
import com.karmanno.payments.domain.CurrencyPrice;
import com.karmanno.payments.dto.CurrencyPriceRequest;
import org.apache.http.HttpResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class AddCurrencyPriceTest extends IntegrationTest {
    @DisplayName("Should add currency price successfully")
    @Test
    public void addCurrencySuccessfully() throws IOException {
        // given:
        Currency from = withCurrency("US Dollar", "USD", 100);
        Currency to = withCurrency("Euro", "EUR", 100);
        CurrencyPriceRequest request = new CurrencyPriceRequest()
                .setCodeFrom(from.getCode())
                .setCodeTo(to.getCode())
                .setPrice("12.34");

        // when:
        HttpResponse response = targetPost("price", request);

        // then:
        assertEquals(201, response.getStatusLine().getStatusCode());
        CurrencyPrice price = objectMapper.readValue(readJsonData(response), CurrencyPrice.class);
        assertEquals(1234, price.getPrice());
        assertEquals(from.getId(), price.getFrom());
        assertEquals(to.getId(), price.getTo());
        assertEquals(from.getCode(), price.getCurrencyFrom().getCode());
        assertEquals(to.getCode(), price.getCurrencyTo().getCode());
        assertEquals("12.34", price.getPriceStr());
    }

    @DisplayName("Should edit currency price successfully")
    @Test
    public void editCurrencyPrice() throws IOException {
        // given:
        Currency from = withCurrency("US Dollar", "USD", 100);
        Currency to = withCurrency("Euro", "EUR", 100);
        withPrice(from.getCode(), to.getCode(), "12.21");
        CurrencyPriceRequest request = new CurrencyPriceRequest()
                .setCodeFrom(from.getCode())
                .setCodeTo(to.getCode())
                .setPrice("17.38");

        // when:
        HttpResponse response = targetPut("price", request);

        // then:
        assertEquals(200, response.getStatusLine().getStatusCode());
        CurrencyPrice price = objectMapper.readValue(readJsonData(response), CurrencyPrice.class);
        assertEquals(1738, price.getPrice());
        assertEquals(from.getId(), price.getFrom());
        assertEquals(to.getId(), price.getTo());
        assertEquals(from.getCode(), price.getCurrencyFrom().getCode());
        assertEquals(to.getCode(), price.getCurrencyTo().getCode());
        assertEquals("17.38", price.getPriceStr());
    }

    @DisplayName("Should receive error when FIRST does not exist")
    @Test
    public void getErrorWhenFirstDoesNotExists() {
        // given:
        Currency to = withCurrency("Euro", "EUR", 100);
        CurrencyPriceRequest request = new CurrencyPriceRequest()
                .setCodeFrom("USD")
                .setCodeTo(to.getCode())
                .setPrice("17.38");

        // when:
        HttpResponse response = targetPost("price", request);

        // then:
        assertEquals(404, response.getStatusLine().getStatusCode());
    }

    @DisplayName("Should receive error when SECOND does not exist")
    @Test
    public void getErrorWhenSecondDoesNotExist() {
        // given:
        Currency from = withCurrency("US Dollar", "USD", 100);
        CurrencyPriceRequest request = new CurrencyPriceRequest()
                .setCodeFrom(from.getCode())
                .setCodeTo("EUR")
                .setPrice("17.38");

        // when:
        HttpResponse response = targetPost("price", request);

        // then:
        assertEquals(404, response.getStatusLine().getStatusCode());
    }


    @DisplayName("Should receive error when amount is in incorrect format while adding")
    @Test
    public void errorWhileCreate() {
        // given:
        Currency from = withCurrency("US Dollar", "USD", 100);
        Currency to = withCurrency("Euro", "EUR", 100);
        CurrencyPriceRequest request = new CurrencyPriceRequest()
                .setCodeFrom(from.getCode())
                .setCodeTo(to.getCode())
                .setPrice("1738");

        // when:
        HttpResponse response = targetPost("price", request);

        // then:
        assertEquals(400, response.getStatusLine().getStatusCode());
    }

    @DisplayName("Should receive error when amount is in incorrect format while editing")
    @Test
    public void errorWhileEdit() {
        // given:
        Currency from = withCurrency("US Dollar", "USD", 100);
        Currency to = withCurrency("Euro", "EUR", 100);
        withPrice(from.getCode(), to.getCode(), "12.21");
        CurrencyPriceRequest request = new CurrencyPriceRequest()
                .setCodeFrom(from.getCode())
                .setCodeTo(to.getCode())
                .setPrice("14.923");

        // when:
        HttpResponse response = targetPut("price", request);

        // then:
        assertEquals(400, response.getStatusLine().getStatusCode());
    }
}
