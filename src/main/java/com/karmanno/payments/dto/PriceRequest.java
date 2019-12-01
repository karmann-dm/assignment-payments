package com.karmanno.payments.dto;

import lombok.Data;

@Data
public class PriceRequest {
    private String firstCurrency;
    private String secondCurrency;
    private int sellPrice;
    private int buyPrice;
}
