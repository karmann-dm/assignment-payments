package com.karmanno.payments.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UsernameIsIncorrectException extends BadRequestException implements ExceptionMapper<UsernameIsIncorrectException> {
    public UsernameIsIncorrectException() {
        super("Username");
    }

    @Override
    public Response toResponse(UsernameIsIncorrectException e) {
        return response();
    }
}
