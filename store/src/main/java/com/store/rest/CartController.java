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

    //@Autowired
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
    public Collection<Product> getCustomer(@QueryParam("username") String username) {
        Collection<Product> output = cartService.getProductsByUser(username);
        return output;
    }


    @GET
    @Path("/products")
    @Produces("application/json")
    public Collection<Customer> listCustomersByPurchase(@QueryParam("productId") int productId) {
        Collection<Customer> output = cartService.listCustomersByPurchase(productId);
        return output;
    }


    @POST
    public void createCart(@QueryParam("productId") int productId, @QueryParam("username") String username) {
       cartService.insertIntoCart(productId, username);
    }

    @PUT
    @Path("purchase/{cartId}")
    public void purchaseCart(@PathParam("cartId") int cartId) {
        cartService.purchaseCart(cartId);
    }


    @DELETE
    public void deleteCustomer(@QueryParam("cartId") int cartId, @QueryParam("productId") int productId) {
        boolean output = cartService.deleteItemInCart(cartId, productId);
    }

}