package com.karmanno.payments.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class FullNameIncorrectException extends BadRequestException implements ExceptionMapper<FullNameIncorrectException> {
    public FullNameIncorrectException() {
        super("Full name");
    }

    @Override
    public Response toResponse(FullNameIncorrectException e) {
        return response();
    }
}
