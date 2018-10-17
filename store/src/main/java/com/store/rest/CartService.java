package com.store.rest;

import com.store.model.Customer;
import org.springframework.stereotype.Service;

import java.util.Collection;


import com.store.dao.*;
import com.store.model.*;

@Service
public class CartService {


    private CartDAO cartDAO = new CartDAO();

    public Collection<Product> getProductsByUser(String username) {
        Collection<Product> products = cartDAO.getProductsByUser(username);
        return products;
    }

    public Collection<Customer> listCustomersByPurchase(int productId) {
        Collection<Customer> output = cartDAO.listCustomersByPurchase(productId);
        return output;
    }

    public void insertIntoCart(int productId, String username) {
        cartDAO.insertIntoCart(productId, username);
    }

    public void purchaseCart(int cartId) {
        cartDAO.purchaseCart(cartId);
    }

    public boolean deleteItemInCart(int cartId, int productId) {
       boolean output = cartDAO.deleteItemInCart(cartId, productId);
       return output;
    }


}