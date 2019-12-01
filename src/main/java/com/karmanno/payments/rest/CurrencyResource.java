package com.karmanno.payments.rest;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("currency")
public class CurrencyResource {
    @POST
    public Response createCurrency() {
        return Response.status(404).build();
    }
}
