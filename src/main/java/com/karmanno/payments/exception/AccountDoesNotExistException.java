package com.karmanno.payments.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AccountDoesNotExistException extends NotFoundException implements ExceptionMapper<AccountDoesNotExistException> {
    public AccountDoesNotExistException() {
        super("Account");
    }

    @Override
    public Response toResponse(AccountDoesNotExistException e) {
        return response();
    }
}
