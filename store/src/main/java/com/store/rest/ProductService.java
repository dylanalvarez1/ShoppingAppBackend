package com.store.rest;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.ArrayList;


import com.store.dao.*;
import com.store.model.*;

@Service
public class ProductService {

    //@Autowired
    private ProductDAO productDAO = new ProductDAO();

    public Collection<Product> getAllProducts() {
        Collection<Product> products = productDAO.getAllProducts();
        return products;
    }

    public Collection<Product> getItemByKeyword(String keyword) {
        Collection<Product> products = productDAO.getItemByKeyword(keyword);
        return products;
    }

    public Product getItemById(int id) {
        Product product = productDAO.getItemById(id);
        return product;
    }


}