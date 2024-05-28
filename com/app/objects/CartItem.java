package com.app.objects;

import java.io.Serializable;

public class CartItem implements Serializable {
    private int id;
    private Item item;
    private int quantity;

    public CartItem(int id, Item item, int quantity) {
        this.id = id;
        this.item = item;
        this.quantity = quantity;
    }

    public CartItem(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void addQuantity() {
        if (this.quantity < 10)
            this.quantity++;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
