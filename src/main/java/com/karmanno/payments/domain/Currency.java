package com.karmanno.payments.domain;

import lombok.Data;

@Data
public class Currency {
    private Integer id;
    private String code;
    private String fullName;
    private Integer minorUnits;
}
