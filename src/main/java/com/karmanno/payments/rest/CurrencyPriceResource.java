package com.karmanno.payments.rest;

import com.karmanno.payments.dto.PriceRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("price")
public class CurrencyPriceResource {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(PriceRequest request) {
        return Response.status(404).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response edit(PriceRequest request) {
        return Response.status(404).build();
    }

    @GET
    public Response getPrice(@QueryParam("from") String currencyFrom,
                             @QueryParam("to") String currencyTo) {
        return Response.status(404).build();
    }
}
