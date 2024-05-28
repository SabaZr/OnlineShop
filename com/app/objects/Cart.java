package com.app.objects;

import java.io.Serializable;
import java.util.List;

public class Cart implements Serializable {

    private int id;
    private List<CartItem> cartItems;

    public Cart(int id, List<CartItem> cartItems) {
        this.id = id;
        this.cartItems = cartItems;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public double getTotal() {
        double total = 0;
        for (CartItem cartItem : cartItems)
            total += cartItem.getQuantity() * cartItem.getItem().getPrice();
        return total;
    }
}
