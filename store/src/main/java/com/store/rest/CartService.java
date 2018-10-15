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

    public void insertIntoCart(int productId, String username) {
        cartDAO.insertIntoCart(productId, username);
    }

    public boolean deleteItemInCart(int cartId, int productId) {
       boolean output = cartDAO.deleteItemInCart(cartId, productId);
       return output;
    }

    /*
    public Customer getCustomer(String customer) {
        Customer retString = customerDAO.getCustomer(customer);
        return retString;
    }

    public Customer createCustomer(String fname, String lname, String username, String email) {
        Customer customer = new Customer(fname, lname, username, email);
        Customer retString = customerDAO.createCustomer(customer);
        return retString;
    }

    public Customer updateCustomer(String fname, String lname, String username, String email) {
        Customer customer = new Customer(fname, lname, username, email);
        Customer retString = customerDAO.updateCustomer(customer);
        return retString;
    }

    public boolean deleteCustomer(String username) {
        boolean success = customerDAO.deleteCustomer(username);
        return success;
    }
    */

}