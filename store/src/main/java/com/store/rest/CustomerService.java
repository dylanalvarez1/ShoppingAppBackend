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


    public Response getCustomer(String customer) {
        return customerDAO.getCustomer(customer);
    }

    public Response createCustomer(String fname, String lname, String username, String email) {
        Customer customer = new Customer(fname, lname, username, email);
        Response output = customerDAO.createCustomer(customer);
        return output;
    }

    public Response updateCustomer(String fname, String lname, String username, String email) {
        Customer customer = new Customer(fname, lname, username, email);
        Response output = customerDAO.updateCustomer(customer);
        return output;
    }

    public Response deleteCustomer(String username) {
        Response output = customerDAO.deleteCustomer(username);
        return output;
    }


}