package com.karmanno.payments.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UserDoesNotExistException extends NotFoundException implements ExceptionMapper<UserDoesNotExistException> {
    public UserDoesNotExistException() {
        super("User");
    }

    @Override
    public Response toResponse(UserDoesNotExistException e) {
        return response();
    }
}
