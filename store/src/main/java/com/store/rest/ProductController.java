package com.store.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
@Path("/items")
public class ProductController extends HttpServlet {


    //@Autowired
    private ProductService productService = new ProductService();

    public void init(ServletConfig config) {
        try {
            super.init(config);
            SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                    config.getServletContext());
        } catch (ServletException e) {
        }
    }

    @GET
    @Produces("application/json")
    public Response getAllItems() {
        Collection<Product> output = productService.getAllProducts();
        return Response.status(200).entity(output).build();
    }

    @GET
    @Path("/search/{keyword}")
    @Produces("application/json")
    public Response getItemByKeyword(@PathParam("keyword") String keyword) {
        Collection<Product> output = productService.getItemByKeyword(keyword);
        return Response.status(200).entity(output).build();
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response getItemById(@PathParam("id") int id) {
        Product output = productService.getItemById(id);
        return Response.status(200).entity(output).build();
    }

}