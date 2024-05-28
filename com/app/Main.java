package com.app;

import com.app.database.Database;

public class Main {

    public static void main(String[] args) {
        if (Database.read())
            App.Run();
    }
}
