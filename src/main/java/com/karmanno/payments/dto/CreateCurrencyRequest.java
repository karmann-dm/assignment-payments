package com.karmanno.payments.dto;

import lombok.Data;

@Data
public class CreateCurrencyRequest {
    private String fullName;
    private Integer minorUnits;
    private String mnemonicCode;
}
