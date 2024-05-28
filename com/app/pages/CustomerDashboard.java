package com.app.pages;

import com.app.database.Database;
import com.app.objects.*;
import com.app.utilities.ConsoleConstants;
import com.app.utilities.ConsoleHelper;

import java.util.ArrayList;

public class CustomerDashboard {

    private static final int EXIT = 0;
    private static final int ITEMS_LIST = 1;
    private static final int SHOW_CART = 2;
    private static final int SHOW_ORDER = 3;
    private static final int LOGOUT = 4;

    private static final int CART_SUBMIT = 1;
    private static final int CART_REMOVE_ITEM = 2;
    private static final int CART_SET_QUANTITY = 3;

    private ConsoleHelper console;
    private User user;
    private final int _menuFrom = 0;
    private final int _menuTo = 4;

    public CustomerDashboard(User user) {
        this.console = new ConsoleHelper();
        this.user = user;
    }

    public void show() {
        while (this.user != null) {
            int menuAction = this.console.readInRange(ConsoleConstants.CUSTOMER_DASH, _menuFrom, _menuTo);

            switch (menuAction) {
                case EXIT:
                    System.exit(0);
                case ITEMS_LIST:
                    showItemsList();
                    break;
                case SHOW_CART:
                    showCart();
                    break;
                case SHOW_ORDER:
                    showOrder();
                    break;
                case LOGOUT:
                    logout();
                    break;
            }
            Database.write();
        }
    }

    private void showItemsList() {
        this.console.println(ConsoleConstants.ITEMS_LIST);

        if (Database.Items.size() == 0)
            this.console.println(ConsoleConstants.NOT_404_FOUND);
        else {
            for (Item item : Database.Items) {
                this.console.print(item.getId());
                this.console.print("\t-\t");
                this.console.print(item.getName());
                this.console.print("\t-\t");
                this.console.print(item.getPrice());
                this.console.print("\n");
            }
            showItemsOptions();
        }
    }

    private void showItemsOptions() {
        int itemId = (int) this.console.read(ConsoleConstants.ITEMS_LIST_OPTIONS, ConsoleHelper.INTEGER);
        if (itemId > 0) {
            Item item = getItem(itemId);
            if (item == null) {
                showItemsOptions();
            } else {
                if (this.user.getCart() == null)
                    this.user.setCart(new Cart(1, new ArrayList<>()));
                CartItem cartItem = alreadyInCart(item);
                if (cartItem == null) {
                    this.user.getCart().getCartItems().add(new CartItem(
                            this.user.getCart().getCartItems().size() == 0 ? 1 :
                                    this.user.getCart().getCartItems().get(
                                            this.user.getCart().getCartItems().size() - 1
                                    ).getId() + 1
                            , getItem(itemId), 1));
                } else cartItem.addQuantity();
                showItemsList();
            }
        }
    }

    private Item getItem(int itemId) {
        for (Item item : Database.Items)
            if (item.getId() == itemId)
                return item;
        return null;
    }

    private CartItem alreadyInCart(Item item) {
        if (this.user.getCart() == null)
            return null;
        for (CartItem cartItem : this.user.getCart().getCartItems())
            if (cartItem.getItem().getId() == item.getId())
                return cartItem;
        return null;
    }

    private void showCart() {
        this.console.println(ConsoleConstants.SHOW_CART);
        if (this.user.getCart() == null)
            this.console.println(ConsoleConstants.NOT_404_FOUND);
        else {
            for (CartItem cartItem : this.user.getCart().getCartItems()) {
                this.console.print(cartItem.getId());
                this.console.print("\t-\t");
                this.console.print(cartItem.getItem().getName());
                this.console.print("\t-\t");
                this.console.println(cartItem.getQuantity());
            }
            this.console.println("-----------------");
            this.console.print("Total:\t");
            this.console.println(this.user.getCart().getTotal());
            showCartOptions();
        }
    }

    private void showCartOptions() {
        int option = (int) this.console.read(ConsoleConstants.CART_OPTIONS, ConsoleHelper.INTEGER);

        switch (option) {
            case EXIT:
                break;
            case CART_SUBMIT:
                submitCart();
                break;
            case CART_REMOVE_ITEM:
                removeItem();
                break;
            case CART_SET_QUANTITY:
                setItemQuantity();
                break;
        }
    }

    private void submitCart() {
        int submitOption = this.console.readInRange(ConsoleConstants.CART_SUBMIT, 1, 2);
        if (this.user.getCart().getCartItems().size() > 0 && submitOption == 1) {
            Database.Orders.add(new Order(Database.generateOrderId(), this.user, this.user.getCart()));
            this.user.setCart(null);
        }
    }

    private void removeItem() {
        int itemId = (int) this.console.read(ConsoleConstants.CART_REMOVE_ITEM, ConsoleHelper.INTEGER);
        if (itemId > 0) {
            Item item = getItem(itemId);
            if (item == null) {
                showItemsOptions();
            } else {
                CartItem cartItem = alreadyInCart(item);
                if (cartItem != null) {
                    this.user.getCart().getCartItems().remove(cartItem);
                } else {
                    this.console.println(ConsoleConstants.NOT_404_FOUND);
                    removeItem();
                }
                showCartOptions();
            }
        }
    }

    private void setItemQuantity() {
        int cartItemId = (int) this.console.read(ConsoleConstants.CART_SET_QUANTITY, ConsoleHelper.INTEGER);
        if (cartItemId > 0) {
            CartItem cartItem = getCartItem(cartItemId);
            if (cartItem != null) {
                setQuantity(cartItem);
                showCart();
            } else {
                this.console.println(ConsoleConstants.NOT_404_FOUND);
                setItemQuantity();
            }
        }
    }

    private void setQuantity(CartItem cartItem) {
        int quantity = this.console.readInRange(ConsoleConstants.SET_QUANTITY, 1, 10);
        if (quantity > 0) {
            cartItem.setQuantity(quantity);
        }
    }

    private CartItem getCartItem(int id) {
        for (CartItem cartItem : this.user.getCart().getCartItems())
            if (cartItem.getId() == id)
                return cartItem;
        return null;
    }

    private void showOrder() {
        this.console.println(ConsoleConstants.ORDERS_LIST);
        int totalOrders = 0;
        for (Order order : Database.Orders)
            if (order.getUser().getId() == this.user.getId()) {
                this.console.print(order.getId());
                this.console.print("\t-\t");
                this.console.print(order.getStatus());
                this.console.print("\t-\t");
                this.console.println(order.getCart().getTotal());
                totalOrders++;
            }

        if (totalOrders == 0)
            this.console.println(ConsoleConstants.NOT_404_FOUND);
    }

    private void logout() {
        this.user = null;
    }
}
