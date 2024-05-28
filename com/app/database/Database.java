package com.app.database;

import com.app.objects.Item;
import com.app.objects.Order;
import com.app.objects.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Database {

    public static final String ITEMS = "items.file";
    public static final String USERS = "users.file";
    public static final String ORDERS = "orders.file";

    public static List<Item> Items = new ArrayList<>();
    public static List<User> Users = new ArrayList<>();
    public static List<Order> Orders = new ArrayList<>();


    public static boolean read() {
        try {
            DatabaseHandler<Item> itemDatabaseHandler = new DatabaseHandler<>(ITEMS);
            Items = itemDatabaseHandler.read();

            DatabaseHandler<User> userDatabaseHandler = new DatabaseHandler<>(USERS);
            Users = userDatabaseHandler.read();

            DatabaseHandler<Order> orderDatabaseHandler = new DatabaseHandler<>(ORDERS);
            Orders = orderDatabaseHandler.read();
            return true;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean write() {
        try {
            DatabaseHandler<Item> itemDatabaseHandler = new DatabaseHandler<>(ITEMS);
            itemDatabaseHandler.write(Items);

            DatabaseHandler<User> userDatabaseHandler = new DatabaseHandler<>(USERS);
            userDatabaseHandler.write(Users);

            DatabaseHandler<Order> orderDatabaseHandler = new DatabaseHandler<>(ORDERS);
            orderDatabaseHandler.write(Orders);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static int generateItemId() {
        if (Items.size() == 0)
            return 1;
        else return Items.get(Items.size() - 1).getId() + 1;
    }

    public static int generateUserId() {
        if (Users.size() == 0)
            return 1;
        else return Users.get(Users.size() - 1).getId() + 1;
    }

    public static int generateOrderId() {
        if (Orders.size() == 0)
            return 1;
        else return Orders.get(Orders.size() - 1).getId() + 1;
    }

}
