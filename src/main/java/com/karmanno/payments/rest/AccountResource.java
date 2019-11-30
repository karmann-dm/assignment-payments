package com.karmanno.payments.rest;

import com.karmanno.payments.dto.CreateAccountRequest;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("account")
@Slf4j
public class AccountResource {

    @GET
    @Path("/{userId}")
    public Response getAccountsByUser(@PathParam("userId") int userId) {
        return Response.status(404).build();
    }

    @GET
    public Response getAccounts() {
        return Response.status(404).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(CreateAccountRequest request) {
        return Response.status(404).build();
    }
}
