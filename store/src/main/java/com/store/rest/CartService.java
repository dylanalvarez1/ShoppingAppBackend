package com.store.rest;

import com.store.model.Customer;
import org.springframework.stereotype.Service;

import java.util.Collection;


import com.store.dao.*;
import com.store.model.*;

import javax.ws.rs.core.Response;

@Service
public class CartService {


    private CartDAO cartDAO = new CartDAO();

    public Response getProductsByUser(String username) {
       return cartDAO.getProductsByUser(username);

    }

    public Response listCustomersByPurchase(int productId) {
        return cartDAO.listCustomersByPurchase(productId);
    }

    public Response insertIntoCart(int productId, String username) {
        return cartDAO.insertIntoCart(productId, username);
    }

    public Response purchaseCart(int cartId) {
        return cartDAO.purchaseCart(cartId);
    }

    public Response deleteItemInCart(int cartId, int productId) {
      return cartDAO.deleteItemInCart(cartId, productId);
    }


}