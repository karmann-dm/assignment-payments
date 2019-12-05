package com.karmanno.payments.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class MinorUnitsIncorrectException extends BadRequestException implements ExceptionMapper<MinorUnitsIncorrectException> {
    public MinorUnitsIncorrectException() {
        super("Minor units");
    }

    @Override
    public Response toResponse(MinorUnitsIncorrectException e) {
        return response();
    }
}
