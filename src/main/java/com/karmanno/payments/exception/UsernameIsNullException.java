package com.karmanno.payments.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UsernameIsNullException extends RuntimeException implements ExceptionMapper<UsernameIsNullException> {
    private static final String MESSAGE = "Error. Username is null";

    public UsernameIsNullException() {
        super("Error. Username is null");
    }

    @Override
    public Response toResponse(UsernameIsNullException e) {
        return Response.status(400)
                .entity(MESSAGE)
                .build();
    }
}
