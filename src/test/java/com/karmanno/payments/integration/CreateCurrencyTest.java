package com.karmanno.payments.integration;

import com.karmanno.payments.domain.Currency;
import com.karmanno.payments.dto.CreateCurrencyRequest;
import org.apache.http.HttpResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class CreateCurrencyTest extends IntegrationTest {
    @DisplayName("Should create currency successfully")
    @Test
    public void shouldCreateCurrency() throws IOException {
        // given:
        CreateCurrencyRequest request = currencyRequest();

        // when:
        HttpResponse response = targetPost("http://localhost:9001/currency", request);
        String currencyJson = readJsonData(response);
        Currency currency = objectMapper.readValue(currencyJson, Currency.class);

        // then:
        Assertions.assertEquals(201, response.getStatusLine().getStatusCode());
        Assertions.assertNotNull(currency);
        Assertions.assertEquals(currency.getCode(), request.getMnemonicCode());
        Assertions.assertEquals(currency.getFullName(), request.getFullName());
        Assertions.assertEquals(currency.getMinorUnits(), request.getMinorUnits());
    }

    @DisplayName("Should receive error when minor units is negative")
    @Test
    public void minorUnitsIsNegative() {
        // given:
        CreateCurrencyRequest request = currencyRequestWithNegativeUnits();

        // when:
        HttpResponse response = targetPost("http://localhost:9001/currency", request);

        // then:
        Assertions.assertEquals(400, response.getStatusLine().getStatusCode());
    }

    @DisplayName("Should receive error when minor units is null")
    @Test
    public void minorUnitsIsNull() {
        // given:
        CreateCurrencyRequest request = currencyRequestWithNullUnits();

        // when:
        HttpResponse response = targetPost("http://localhost:9001/currency", request);

        // then:
        Assertions.assertEquals(400, response.getStatusLine().getStatusCode());
    }

    @DisplayName("Should receive error when mnemonic code is empty")
    @Test
    public void mnemonicCodeEmpty() {
        // given:
        CreateCurrencyRequest request = currencyRequestWithEmptyCode();

        // when:
        HttpResponse response = targetPost("http://localhost:9001/currency", request);

        // then:
        Assertions.assertEquals(400, response.getStatusLine().getStatusCode());
    }

    @DisplayName("Should receive error when mnemonic code is null")
    @Test
    public void mnemonicCodeIsNull() {
        // given:
        CreateCurrencyRequest request = currencyRequestWithNullCode();

        // when:
        HttpResponse response = targetPost("http://localhost:9001/currency", request);

        // then:
        Assertions.assertEquals(400, response.getStatusLine().getStatusCode());
    }

    @DisplayName("Should receive error when full name is empty")
    @Test
    public void fullNameIsEmpty() {
        // given:
        CreateCurrencyRequest request = currencyRequestWithEmptyFullName();

        // when:
        HttpResponse response = targetPost("http://localhost:9001/currency", request);

        // then:
        Assertions.assertEquals(400, response.getStatusLine().getStatusCode());
    }

    @DisplayName("Should receive error when full name is null")
    @Test
    public void fullNameIsNull() {
        // given:
        CreateCurrencyRequest request = currencyRequestWithNullFullName();

        // when:
        HttpResponse response = targetPost("http://localhost:9001/currency", request);

        // then:
        Assertions.assertEquals(400, response.getStatusLine().getStatusCode());
    }

    private static CreateCurrencyRequest currencyRequest() {
        return new CreateCurrencyRequest()
                .setFullName("Dollar")
                .setMinorUnits(100)
                .setMnemonicCode("USD");
    }

    private static CreateCurrencyRequest currencyRequestWithEmptyCode() {
        return new CreateCurrencyRequest()
                .setFullName("Dollar")
                .setMinorUnits(100)
                .setMnemonicCode("");
    }

    private static CreateCurrencyRequest currencyRequestWithNullCode() {
        return new CreateCurrencyRequest()
                .setFullName("Dollar")
                .setMinorUnits(100);
    }

    private static CreateCurrencyRequest currencyRequestWithEmptyFullName() {
        return new CreateCurrencyRequest()
                .setFullName("")
                .setMinorUnits(100)
                .setMnemonicCode("USD");
    }

    private static CreateCurrencyRequest currencyRequestWithNullFullName() {
        return new CreateCurrencyRequest()
                .setMinorUnits(100)
                .setMnemonicCode("USD");
    }

    private static CreateCurrencyRequest currencyRequestWithNegativeUnits() {
        return new CreateCurrencyRequest()
                .setFullName("Dollar")
                .setMinorUnits(-100)
                .setMnemonicCode("USD");
    }

    private static CreateCurrencyRequest currencyRequestWithNullUnits() {
        return new CreateCurrencyRequest()
                .setFullName("Dollar")
                .setMinorUnits(-100)
                .setMnemonicCode("USD");
    }
}
