package com.store.model;

public class Order {

    //cartId, itemId
    private int cartId, itemId;

    public Order(int cartId, int itemId) {
        this.cartId = cartId;
        this.itemId = itemId;
    }

    //cartId, itemId

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

