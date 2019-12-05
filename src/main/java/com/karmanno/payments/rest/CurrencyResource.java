package com.karmanno.payments.rest;

import com.karmanno.payments.dto.CreateCurrencyRequest;
import com.karmanno.payments.service.CurrencyService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("currency")
public class CurrencyResource {
    private final CurrencyService currencyService;

    @Inject
    public CurrencyResource(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCurrency(CreateCurrencyRequest request) {
        return Response.status(201)
                .entity(currencyService.create(request.getMnemonicCode(),
                        request.getFullName(),
                        request.getMinorUnits()))
                .build();
    }
}
