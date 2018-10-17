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
@Path("/carts")
public class CartController extends HttpServlet {


    private CartService cartService = new CartService();

    public void init(ServletConfig config) {
        try{
            super.init(config);
            SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                    config.getServletContext());
        }catch(ServletException e){
        }
    }

    @GET
    @Produces("application/json")
    public Response getCustomer(@QueryParam("username") String username) {
       Response output = cartService.getProductsByUser(username);
        return output;
    }


    //In the project description, the path is supposed to be "/", but I was getting erros
    //This explains why it was wrong
    //https://stackoverflow.com/questions/17270475/two-get-methods-with-different-query-parameters-rest
    @GET
    @Path("/products")
    @Produces("application/json")
    public Response listCustomersByPurchase(@QueryParam("productId") int productId) {
        Response output = cartService.listCustomersByPurchase(productId);
        return output;
    }


    @POST
    public Response createCart(@QueryParam("productId") int productId, @QueryParam("username") String username) {
       return cartService.insertIntoCart(productId, username);
    }

    @PUT
    @Path("purchase/{cartId}")
    public Response purchaseCart(@PathParam("cartId") int cartId) {
        return cartService.purchaseCart(cartId);
    }


    @DELETE
    public Response deleteCustomer(@QueryParam("cartId") int cartId, @QueryParam("productId") int productId) {
       Response output = cartService.deleteItemInCart(cartId, productId);
       return output;
    }

}