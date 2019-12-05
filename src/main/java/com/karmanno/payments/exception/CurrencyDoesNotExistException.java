package com.karmanno.payments.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CurrencyDoesNotExistException extends NotFoundException implements ExceptionMapper<CurrencyDoesNotExistException> {
    public CurrencyDoesNotExistException() {
        super("Currency");
    }

    @Override
    public Response toResponse(CurrencyDoesNotExistException e) {
        return response();
    }
}
