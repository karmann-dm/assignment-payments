package com.karmanno.payments.exception;

import javax.ws.rs.core.Response;

public abstract class BadRequestException extends RuntimeException {
    private String message;

    protected BadRequestException(String field) {
        super(String.format("%s is incorrect", field));
        this.message = String.format("%s is incorrect", field);
    }

    protected Response response() {
        return Response.status(400)
                .entity(message)
                .build();
    }
}
