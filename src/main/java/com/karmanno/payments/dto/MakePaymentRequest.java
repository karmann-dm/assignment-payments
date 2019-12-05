package com.karmanno.payments.dto;

import lombok.Data;

@Data
public class MakePaymentRequest {
    private String accountFrom;
    private String accountTo;
    private String amount;
}
