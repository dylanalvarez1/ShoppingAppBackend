package com.store.rest;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.ArrayList;


import com.store.dao.*;
import com.store.model.*;

import javax.ws.rs.core.Response;

@Service
public class CustomerService {

    private CustomerDAO customerDAO = new CustomerDAO();


    public Customer getCustomer(String customer) {
        Customer retString = customerDAO.getCustomer(customer);
        return retString;
    }

    public Response createCustomer(String fname, String lname, String username, String email) {
        Customer customer = new Customer(fname, lname, username, email);
        Response output = customerDAO.createCustomer(customer);
        return output;
    }

    public Customer updateCustomer(String fname, String lname, String username, String email) {
        Customer customer = new Customer(fname, lname, username, email);
        Customer retString = customerDAO.updateCustomer(customer);
        return retString;
    }

    public Response deleteCustomer(String username) {
        Response output = customerDAO.deleteCustomer(username);
        return output;
    }


}