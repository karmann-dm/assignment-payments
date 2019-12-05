package com.karmanno.payments.rest;

import com.karmanno.payments.domain.Account;
import com.karmanno.payments.dto.CreateAccountRequest;
import com.karmanno.payments.dto.PutMoneyRequest;
import com.karmanno.payments.exception.AccountDoesNotExistException;
import com.karmanno.payments.exception.CurrencyDoesNotExistException;
import com.karmanno.payments.exception.UserDoesNotExistException;
import com.karmanno.payments.service.AccountService;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("account")
@Slf4j
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AccountResource {
    private final AccountService accountService;

    @Inject
    public AccountResource(AccountService accountService) {
        this.accountService = accountService;
    }

    @GET
    public Response getAccounts(@QueryParam("userId") Integer userId) throws UserDoesNotExistException {
        List<Account> accounts = accountService.findAllByUser(userId);
        return Response.status(201)
                .entity(accounts)
                .build();
    }

    @POST
    public Response create(CreateAccountRequest request) throws
            UserDoesNotExistException, CurrencyDoesNotExistException {
        Account account = accountService.create(request.getUserId(), request.getCurrencyCode());
        return Response.status(201)
                .entity(account)
                .build();
    }

    @PUT
    public Response putMoney(PutMoneyRequest request) throws AccountDoesNotExistException {
        Account account = accountService.update(
                request.getAccountNumber(),
                request.getCurrencyCode(),
                request.getAmount()
        );
        return Response.status(200)
                .entity(account)
                .build();
    }
}
