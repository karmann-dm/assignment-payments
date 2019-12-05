package com.karmanno.payments.exception;

import javax.ws.rs.core.Response;

public abstract class NotFoundException extends RuntimeException {
    private String message;

    protected NotFoundException(String field) {
        super(String.format("%s not found", field));
        this.message = String.format("%s not found", field);
    }

    protected Response response() {
        return Response.status(404)
                .entity(message)
                .build();
    }
}
