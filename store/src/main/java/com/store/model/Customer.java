package com.store.model;

public class Customer {

    //id, name, msrp, salePrice, upc, shortDescription, brandName, size, color, gender
    private int id;
    private String fname, lname, username, email;

    public Customer(int id, String fname, String lname, String username, String email) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.username = username;
        this.email = email;
    }
    public Customer(int id) {
        this.id = id;
    }

    public Customer(int id, String fname) {
        this.id = id;
        this.fname = fname;
    }

    public Customer(int id, String fname, String lname) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
    }

    //id, name, msrp, salePrice, upc, shortDescription, brandName, size, color, gender

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFname() {
        return this.fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return this.lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {

        return String.format(
                "{id:%d, fname:'%s', lname:'%s', username:'%s', email:'%s'}",
                this.id, this.fname, this.lname, this.username, this.email);
    }

}

