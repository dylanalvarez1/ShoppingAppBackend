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

    public String getCustomer(String customer) {
        String retString = customerDAO.getCustomer(customer);
        return retString;
    }

    //TODO: Find the correct way to generate an id
    public String createCustomer(String fname, String lname, String username, String email) {
        int id = (int) Math.random() * 100000;
        Customer customer = new Customer(id, fname, lname, username, email);
        String retString = customerDAO.createCustomer(customer).toString();
        return retString;
    }


}