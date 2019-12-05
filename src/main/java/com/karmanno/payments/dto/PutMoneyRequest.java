package com.karmanno.payments.dto;

import lombok.Data;

@Data
public class PutMoneyRequest {
    private String accountNumber;
    private String currencyCode;
    private String amount;
}
