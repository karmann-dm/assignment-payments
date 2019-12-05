package com.karmanno.payments.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class MnemonicCodeIncorrectException extends BadRequestException implements ExceptionMapper<MnemonicCodeIncorrectException> {
    public MnemonicCodeIncorrectException() {
        super("Mnemonic code");
    }

    @Override
    public Response toResponse(MnemonicCodeIncorrectException e) {
        return response();
    }
}
