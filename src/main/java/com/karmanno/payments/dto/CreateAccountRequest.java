package com.karmanno.payments.dto;

import lombok.Data;

@Data
public class CreateAccountRequest {
    private int userId;
    private String currencyCode;
}
