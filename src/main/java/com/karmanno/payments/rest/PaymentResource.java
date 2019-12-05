package com.karmanno.payments.rest;

import com.karmanno.payments.dto.MakePaymentRequest;
import com.karmanno.payments.service.PaymentService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("payment")
public class PaymentResource {
    private final PaymentService paymentService;

    @Inject
    public PaymentResource(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPayments(@QueryParam("account") String account) {
        return Response.status(200).entity(paymentService.getPayments(account)).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPayment(@PathParam("id") Integer id) {
        return Response.status(200).entity(paymentService.get(id)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response makePayment(MakePaymentRequest request) {
        return Response.status(201).entity(paymentService.create(
                request.getAccountFrom(),
                request.getAccountTo(),
                request.getAmount()
        )).build();
    }
}
