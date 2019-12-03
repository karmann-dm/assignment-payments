package com.karmanno.payments.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UsernameIsEmptyException extends RuntimeException implements ExceptionMapper<UsernameIsEmptyException> {
    private static final String MESSAGE = "Error. Username is empty";

    public UsernameIsEmptyException() {
        super(MESSAGE);
    }

    @Override
    public Response toResponse(UsernameIsEmptyException e) {
        return Response.status(400)
                .entity(MESSAGE)
                .build();
    }
}
