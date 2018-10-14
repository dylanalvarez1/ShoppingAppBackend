package com.store.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

//import org.springframework.http.MediaType;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import javax.servlet.http.HttpServlet;
import javax.servlet.*;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.Produces;
import java.util.Collection;
import java.util.ArrayList;


import com.store.dao.*;
import com.store.model.*;

@Controller
@Path("/customers")
public class CustomerController extends HttpServlet {


    //@Autowired
    private CustomerService customerService = new CustomerService();

    public void init(ServletConfig config) {
        try{
            super.init(config);
            SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                    config.getServletContext());
        }catch(ServletException e){
        }
    }

    @GET
    @Path("/{customer}")
    @Produces("application/json")
    public Customer getCustomer(@PathParam("customer") String username) {
        Customer output = customerService.getCustomer(username);
        return output;
    }


    @DELETE
    @Path("/{customer}")
    public void deleteCustomer(@PathParam("customer") String username) {
        boolean output = customerService.deleteCustomer(username);
    }

    @POST
    public void createUser(@QueryParam("fname") String fname, @QueryParam("lname") String lname, @QueryParam("username") String username, @QueryParam("email") String email) {
        Customer output = customerService.createCustomer(fname, lname, username, email);
    }

    @PUT
    public void updateUser(@QueryParam("fname") String fname, @QueryParam("lname") String lname, @QueryParam("username") String username, @QueryParam("email") String email) {
        Customer output = customerService.updateCustomer(fname, lname, username, email);
    }






    /*
    @GET
    @Produces("text/plain")
    public String getAllAlbums() {
        return customerService.getAllAlbums();
    }
    */

}