package com.store.rest;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.ArrayList;


import com.store.dao.*;
import com.store.model.*;

@Service
public class CustomerService {

    //@Autowired
    private AlbumDAO albumDAO = new AlbumDAO();
    private CustomerDAO customerDAO = new CustomerDAO();

    public String getMsg( String msg) {
        return "Hello : " + msg;
    }

    public String getAllAlbums() {
        String retString = "";
        Collection<Album> albums = albumDAO.getAllAlbums();
        for (Album album : albums) {
            retString += album.toString();
        }

        return retString;
    }

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


}