package com.karmanno.payments.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UserExistException extends RuntimeException implements ExceptionMapper<UserExistException> {
    private static final String MESSAGE = "Error. User exists";

    public UserExistException() {
        super(MESSAGE);
    }

    @Override
    public Response toResponse(UserExistException e) {
        return Response.status(400)
                .entity(MESSAGE)
                .build();
    }
}

