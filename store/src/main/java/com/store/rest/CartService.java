package com.store.rest;

import com.store.dao.AlbumDAO;
import com.store.dao.CustomerDAO;
import com.store.model.Album;
import com.store.model.Customer;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.ArrayList;


import com.store.dao.*;
import com.store.model.*;
import java.util.Collection;

@Service
public class CartService {

    //@Autowired
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