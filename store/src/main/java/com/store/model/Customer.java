package com.store.model;

public class Customer {

    //id, name, msrp, salePrice, upc, shortDescription, brandName, size, color, gender
    private String fname, lname, username, email;

    public Customer(String fname, String lname, String username, String email) {
        this.fname = fname;
        this.lname = lname;
        this.username = username;
        this.email = email;
    }

    public Customer(String fname) {
        this.fname = fname;
    }

    public Customer(String fname, String lname) {
        this.fname = fname;
        this.lname = lname;
    }

    //name, msrp, salePrice, upc, shortDescription, brandName, size, color, gender

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
                "{fname:'%s', lname:'%s', username:'%s', email:'%s'}",
                this.getFname(), this.getLname(), this.getUsername(), this.getEmail());
    }

}

