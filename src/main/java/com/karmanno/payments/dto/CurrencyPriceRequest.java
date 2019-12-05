package com.karmanno.payments.dto;

import lombok.Data;

@Data
public class CurrencyPriceRequest {
    private String codeFrom;
    private String codeTo;
    private String price;
}
