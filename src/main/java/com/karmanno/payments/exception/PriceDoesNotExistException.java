package com.karmanno.payments.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class PriceDoesNotExistException extends NotFoundException implements ExceptionMapper<PriceDoesNotExistException> {
    public PriceDoesNotExistException() {
        super("Price");
    }

    @Override
    public Response toResponse(PriceDoesNotExistException e) {
        return response();
    }
}
