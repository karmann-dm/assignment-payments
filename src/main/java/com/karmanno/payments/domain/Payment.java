package com.karmanno.payments.domain;

import lombok.Data;

@Data
public class Payment {
    private Integer id;
    private String pid;
    private String from;
    private String to;
    private Integer price;
    private Integer status;
    private Account accountFrom;
    private Account accountTo;
}
