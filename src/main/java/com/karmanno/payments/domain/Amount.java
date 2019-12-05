package com.karmanno.payments.domain;

import lombok.Data;

@Data
public class Amount {
    private Integer major;
    private Integer minor;

    public static Amount fromString(String amount) throws Exception {
        String[] parts = amount.split("\\.");
        if (parts.length != 2)
            throw new Exception("Amount has invalid format");
        if (parts[1].length() != 2)
            throw new Exception("Amount has invalid format");
        Integer major = Integer.parseInt(parts[0]);
        Integer minor = Integer.parseInt(parts[1]);
        return new Amount()
                .setMajor(major)
                .setMinor(minor);
    }
}
