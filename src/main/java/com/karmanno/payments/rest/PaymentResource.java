package com.karmanno.payments.rest;

import com.karmanno.payments.dto.MakePaymentRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("payment")
public class PaymentResource {
    @GET
    public Response getPayments(@QueryParam("userId") Integer userId,
                                @QueryParam("status") String status,
                                @QueryParam("type") String type,
                                @QueryParam("dateFrom") String dateFrom,
                                @QueryParam("dateTo") String dateTo) {
        return Response.status(404).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response makePayment(MakePaymentRequest request) {
        return Response.status(404).build();
    }
}
