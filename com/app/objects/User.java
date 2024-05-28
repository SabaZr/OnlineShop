package com.app.objects;

import java.io.Serializable;

public class User implements Serializable {
    public static final int CUSTOMER = 1;
    public static final int SELLER = 2;

    public static final int REGISTERED = 1;
    public static final int NOT_REGISTERED = 2;

    private int id;
    private int type;
    private Cart cart;
    private String username;
    private String password;

    public User(int id, int type, String username, String password) {
        this.id = id;
        this.type = type;
        this.username = username;
        this.password = password;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
