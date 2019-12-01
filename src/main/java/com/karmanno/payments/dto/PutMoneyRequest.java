package com.karmanno.payments.dto;

import lombok.Data;

@Data
public class PutMoneyRequest {
    private String currencyCode;
    private int amount;
}
