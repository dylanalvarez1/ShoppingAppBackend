package com.store.model;

public class Order {

    //cartId, itemId
    private int orderId, cartId, itemId;

    public Order(int orderId, int cartId, int itemId) {
        this.orderId = orderId;
        this.cartId = cartId;
        this.itemId = itemId;
    }

    //orderId, cartId, itemId
    public int getId() {
        return this.orderId;
    }

    public void setId(int orderId) {
        this.orderId = orderId;
    }

    public int getCartId() {
        return this.cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getItemId() {
        return this.itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    @Override
    public String toString() {
        return String.format(
                "{cartid:'%d', itemid:'%d'}",
                this.getCartId(), this.getItemId());
    }

}

