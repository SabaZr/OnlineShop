package com.app.pages;

import com.app.database.Database;
import com.app.objects.CartItem;
import com.app.objects.Item;
import com.app.objects.Order;
import com.app.objects.User;
import com.app.utilities.ConsoleConstants;
import com.app.utilities.ConsoleHelper;

import java.util.List;

public class SellerDashboard {

    private static final int EXIT = 0;
    private static final int ITEMS_LIST = 1;
    private static final int USERS = 2;
    private static final int ORDERS = 3;
    private static final int LOGOUT = 4;

    private static final int ITEMS_ADD = 1;
    private static final int ITEMS_REMOVE = 2;


    private ConsoleHelper console;
    private User user;
    private final int _menuFrom = 0;
    private final int _menuTo = 4;

    public SellerDashboard(User user) {
        this.console = new ConsoleHelper();
        this.user = user;
    }

    public void show() {
        while (this.user != null) {
            int menuAction = this.console.readInRange(ConsoleConstants.SELLER_DASH, _menuFrom, _menuTo);

            switch (menuAction) {
                case EXIT:
                    System.exit(0);
                case ITEMS_LIST:
                    showItemsList();
                    break;
                case USERS:
                    showUsers();
                    break;
                case ORDERS:
                    showOrders();
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
        }
        showItemsOptions();
    }

    private void showItemsOptions() {
        int option = this.console.readInRange(ConsoleConstants.SELLER_ITEMS_LIST_OPTIONS, 0, 2);
        if (option > 0) {
            switch (option) {
                case ITEMS_ADD:
                    addItem();
                    break;
                case ITEMS_REMOVE:
                    removeItem();
                    break;
            }
        }
    }

    private void addItem() {
        String itemName = (String) this.console.read(ConsoleConstants.ADD_ITEMS_NAME, ConsoleHelper.STRING);
        Object priceObj = this.console.read(ConsoleConstants.ADD_ITEMS_PRICE, ConsoleHelper.DOUBLE);
        Double price = -1.0;
        try {
            price = (Double) priceObj;
        } catch (ClassCastException exception) {
            price = (long) priceObj * 1.0;
        }
        Database.Items.add(new Item(Database.generateItemId(), itemName, price));
        showItemsList();
    }

    private void removeItem() {
        int itemId = (int) this.console.read(ConsoleConstants.REMOVE_ITEM, ConsoleHelper.INTEGER);
        if (itemId > 0) {
            Item item = getItem(itemId);
            if (item == null)
                removeItem();
            else {
                Database.Items.remove(item);
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

    private void showUsers() {
        this.console.println(ConsoleConstants.USERS_LIST);

        if (Database.Users.size() == 0)
            this.console.println(ConsoleConstants.NOT_404_FOUND);
        else {
            for (User user : Database.Users) {
                this.console.print(user.getId());
                this.console.print("\t-\t");
                this.console.print(user.getUsername());
                this.console.print("\n");
            }
            showUsersOptions();
        }
    }

    private void showUsersOptions() {
        int userId = (int) this.console.read(ConsoleConstants.USERS_LIST_OPTIONS, ConsoleHelper.INTEGER);
        if (userId > 0) {
            User user = getUser(userId);
            if (user == null) {
                showUsers();
            } else {
                if (user.getId() != this.user.getId())
                    Database.Users.remove(user);
                else {
                    this.console.println(ConsoleConstants.SELF_DELETION);
                    showUsers();
                }
            }
        }
    }

    private User getUser(int userId) {
        for (User user : Database.Users)
            if (user.getId() == userId)
                return user;
        return null;
    }

    private void showOrders() {
        this.console.println(ConsoleConstants.ORDERS_LIST);

        if (Database.Orders.size() == 0)
            this.console.println(ConsoleConstants.NOT_404_FOUND);
        else {
            for (Order order : Database.Orders) {
                this.console.print(order.getId());
                this.console.print("\t-\t");
                this.console.print(order.getStatus());
                this.console.print("\n");
            }
            showOrdersOptions();
        }
    }

    private void showOrdersOptions() {
        int orderId = (int) this.console.read(ConsoleConstants.ORDERS_OPTIONS, ConsoleHelper.INTEGER);
        if (orderId > 0) {
            Order order = getOrder(orderId);
            if (order == null) {
                showOrdersOptions();
                return;
            } else {
                getOrderDetails(order);
                setOrderStatus(order);
                showOrders();
            }
        }
    }

    private void getOrderDetails(Order order) {
        order.getInformation();
    }

    private void setOrderStatus(Order order) {
        int orderStatus = this.console.readInRange(ConsoleConstants.SET_ORDER_STATUS, 0, 3);
        if (orderStatus > 0) {
            order.setStatus(orderStatus);
        }
    }

    private Order getOrder(int orderId) {
        for (Order order : Database.Orders)
            if (order.getId() == orderId)
                return order;
        return null;
    }

    private void logout() {
        this.user = null;
    }
}
