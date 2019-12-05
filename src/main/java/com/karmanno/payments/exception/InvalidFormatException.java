package com.karmanno.payments.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidFormatException extends BadRequestException implements ExceptionMapper<InvalidFormatException> {
    public InvalidFormatException() {
        super("Amount");
    }

    @Override
    public Response toResponse(InvalidFormatException e) {
        return response();
    }
}
