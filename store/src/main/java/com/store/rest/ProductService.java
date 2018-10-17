package com.store.rest;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.ArrayList;


import com.store.dao.*;
import com.store.model.*;

import javax.ws.rs.core.Response;

@Service
public class ProductService {

    private ProductDAO productDAO = new ProductDAO();

    public Response getAllProducts() {
        return productDAO.getAllProducts();
    }

    public Response getItemByKeyword(String keyword) {
       return productDAO.getItemByKeyword(keyword);
    }

    public Response getItemById(int id) {
       return productDAO.getItemById(id);
    }


}