package com.store.model;

public class Product {

    private int id;

    private String name;
    private float msrp, salePrice;
    private int upc;
    private String shortDescription, brandName, size, color, gender;

    public Product(int id, String name, float msrp, float salePrice, int upc, String shortDescription, String brandName, String size, String color, String gender) {
        this.id = id;
        this.name = name;
        this.msrp = msrp;
        this.salePrice = salePrice;
        this.upc = upc;
        this.shortDescription = shortDescription;
        this.brandName = brandName;
        this.size = size;
        this.color = color;
        this.gender = gender;
    }

    public Product(int id) {
        this.id = id;
    }

    public Product(String name, int id) {
        this.id = id;
        this.name = name;
    }

    //id, name, msrp, salePrice, upc, shortDescription, brandName, size, color, gender

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getMsrp() {
        return this.msrp;
    }

    public void setMsrp(float msrp) {
        this.msrp = msrp;
    }

    public float getSalePrice() {
        return this.salePrice;
    }

    public void setSalePrice(float salePrice) {
        this.salePrice = salePrice;
    }

    public int getUpc() {
        return this.upc;
    }

    public void setUpc(int upc) {
        this.upc = upc;
    }

    public String getShortDescription() {
        return this.shortDescription;
    }

    public void setShortDescription(String description) {
        this.shortDescription = description;
    }

    public String getBrandName() {
        return this.brandName;
    }

    public void setBrandName(String brand) {
        this.brandName = brand;
    }

    public String getSize() {
        return this.size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    @Override
    public String toString() {

        return String.format(
                "Customer[id=%d, name='%s', msrp='%d', price='%d', upc='%d', description='%s', brand='%s', size='%s', color='%s', gender='%s']",
                this.getId(), this.getName(), this.getMsrp(), this.getSalePrice(), this.getUpc(), this.getShortDescription(), this.getBrandName(), this.getSize(), this.getColor(), this.getGender());
    }

}

