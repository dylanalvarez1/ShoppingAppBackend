package com.store.model;

import java.util.ArrayList;
import java.util.Collection;

public class Cart {

    //userId, orders
    private int cartId;
    private String user;
    private ArrayList<Order> orders;

    public Cart(int cartId, String user) {
        this.cartId = cartId;
        this.user = user;
    }


    //name, msrp, salePrice, upc, shortDescription, brandName, size, color, gender

    public int getId() {
        return this.cartId;
    }

    public void setId(int cartId) {
        this.cartId = cartId;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void addOrder(Order order) {
        this.orders.add(order);
    }

    public void deleteOrder(Order order) {
        int i = 0;
        if(this.orders.contains(order))
        {
            while(i < this.orders.size())
            {
                if(this.orders.get(i) == order) {
                    this.orders.remove(i);
                }
            }
        }
    }

    @Override
    public String toString() {
        return String.format(
                "{userid:'%s'}",
                this.getUser());
    }

}

