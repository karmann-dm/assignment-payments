package com.karmanno.payments.rest;

import com.karmanno.payments.domain.User;
import com.karmanno.payments.dto.CreateUserRequest;
import com.karmanno.payments.exception.UserExistException;
import com.karmanno.payments.exception.UsernameIsIncorrectException;
import com.karmanno.payments.service.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("user")
public class UserResource {
    private final UserService userService;

    @Inject
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(CreateUserRequest request)
            throws UsernameIsIncorrectException, UserExistException {
        User user = userService.create(request.getUsername());
        return Response.status(Response.Status.CREATED)
                .entity(user)
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllUsers() {
        return Response.status(Response.Status.OK)
                .entity(userService.findAll())
                .build();
    }
}
