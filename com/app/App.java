package com.app;

import com.app.objects.User;
import com.app.pages.Authentication;
import com.app.pages.CustomerDashboard;
import com.app.pages.SellerDashboard;
import com.app.pages.UserTypeSelection;
import com.app.utilities.ConsoleConstants;
import com.app.utilities.ConsoleHelper;

public class App {

    private static ConsoleHelper consoleHelper;

    public static void Run() {
        if (consoleHelper == null)
            consoleHelper = new ConsoleHelper();

        consoleHelper.println(ConsoleConstants.WELCOME_MESSAGE);
        while (true) {
            int type = new UserTypeSelection().select();
            if (type == -1)
                continue;

            int registered = consoleHelper.readInRange(ConsoleConstants.IS_REGISTERED, 1, 2);
            User user = null;

            if (registered == User.REGISTERED)
                user = new Authentication().authenticate(type);
            else if (registered == User.NOT_REGISTERED)
                user = new Authentication().register(type);

            if (user == null)
                continue;

            if (type == User.CUSTOMER) {
                new CustomerDashboard(user).show();
            } else if (type == User.SELLER) {
                new SellerDashboard(user).show();
            }
        }
    }
}
