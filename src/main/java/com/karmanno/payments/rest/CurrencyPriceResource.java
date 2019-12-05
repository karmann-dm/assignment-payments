package com.karmanno.payments.rest;

import com.karmanno.payments.dto.CurrencyPriceRequest;
import com.karmanno.payments.exception.CurrencyDoesNotExistException;
import com.karmanno.payments.exception.PriceDoesNotExistException;
import com.karmanno.payments.service.CurrencyPriceService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("price")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CurrencyPriceResource {
    private final CurrencyPriceService currencyPriceService;

    @Inject
    public CurrencyPriceResource(CurrencyPriceService currencyPriceService) {
        this.currencyPriceService = currencyPriceService;
    }

    @POST
    public Response create(CurrencyPriceRequest request) {
        return Response.status(201)
                .entity(currencyPriceService.create(
                        request.getCodeFrom(),
                        request.getCodeTo(),
                        request.getPrice()
                ))
                .build();
    }

    @PUT
    public Response edit(CurrencyPriceRequest request) {
        return Response.status(200)
                .entity(currencyPriceService.edit(
                        request.getCodeFrom(),
                        request.getCodeTo(),
                        request.getPrice()
                ))
                .build();
    }

    @GET
    public Response getPrice(@QueryParam("from") String currencyFrom,
                             @QueryParam("to") String currencyTo) throws CurrencyDoesNotExistException, PriceDoesNotExistException {
        return Response.status(200)
                .entity(currencyPriceService.get(currencyFrom, currencyTo))
                .build();
    }
}
