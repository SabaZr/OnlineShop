package com.app.objects;

import com.app.utilities.ConsoleConstants;

import java.io.Serializable;

public class Order implements Serializable {

    public static final int PROCESSING = 1;
    public static final int SENT = 2;
    public static final int DELIVERED = 3;

    private int id;
    private User user;
    private Cart cart;
    private int status;

    public Order(int id, User user, Cart cart, int status) {
        this.id = id;
        this.user = user;
        this.cart = cart;
        this.status = status;
    }

    public Order(int id, User user, Cart cart) {
        this.id = id;
        this.user = user;
        this.cart = cart;
        this.status = PROCESSING;
    }

    public Order(User user, Cart cart) {
        this.user = user;
        this.cart = cart;
        this.status = PROCESSING;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public String getStatus() {
        switch (this.status) {
            case Order.PROCESSING:
                return "Processing";
            case Order.SENT:
                return "Sent";
            case Order.DELIVERED:
                return "Delivered";
        }
        return null;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void getInformation() {
        StringBuilder info = new StringBuilder();
        info.append("Order ID:");
        info.append(this.id);
        info.append("\t");
        info.append("User ID:");
        info.append(this.user.getId());
        info.append("\t");
        info.append("Username:");
        info.append(this.user.getUsername());
        info.append("\n");
        info.append(ConsoleConstants.ITEMS_LIST);
        info.append("\n");
        for (CartItem cartItem : this.getCart().getCartItems()) {
            info.append(cartItem.getItem().getName());
            info.append("\t-\t");
            info.append("Quantity:");
            info.append("\t");
            info.append(cartItem.getQuantity());
            info.append("\n");
        }
        info.append("---------------");
        info.append("\n");
        info.append("Total:");
        info.append("\t");
        info.append(this.getCart().getTotal());
        info.append("\n");
        System.out.print(info);
    }
}
