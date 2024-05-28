package com.app.pages;

import com.app.database.Database;
import com.app.objects.User;
import com.app.utilities.ConsoleConstants;
import com.app.utilities.ConsoleHelper;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Authentication {

    private int loginAttempts = 0;

    private ConsoleHelper console;

    public Authentication() {
        this.console = new ConsoleHelper();
    }

    public User authenticate(int type) {
        try {
            return login(null, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User register(int type) {
        return signUp(null, type);
    }

    private User login(String note, int type) throws IOException {
        if (note != null)
            this.console.println(note);
        String username = (String) this.console.read("Username:", ConsoleHelper.STRING);
        String password = (String) this.console.read("Password:", ConsoleHelper.STRING);
        loginAttempts++;
        if (loginAttempts == 2)
            return null;

        String hashedPassword = hash(password);
        if (hashedPassword == null)
            return login(ConsoleConstants.ILLEGAL_INPUT, type);

        for (User user : Database.Users)
            if (user.getUsername().equals(username) && user.getPassword().equals(hashedPassword) &&
                    user.getType() == type) {
                this.console.println("Logged in!");
                return user;
            }

        return login(ConsoleConstants.WRONG_CREDENTIALS, type);
    }

    private User signUp(String note, int type) {
        if (note != null)
            this.console.println(note);

        String username = (String) this.console.read("Username:", ConsoleHelper.STRING);
        if (isDuplicateUsername(username))
            return signUp(ConsoleConstants.USERNAME_TAKEN, type);


        String password = (String) this.console.read("Password:", ConsoleHelper.STRING);
        String confirmPassword =
                (String) this.console.read("Confirm Password:", ConsoleHelper.STRING);

        if (password.equals(confirmPassword)) {
            String hashedPass = hash(password);
            if (hashedPass == null)
                return signUp(ConsoleConstants.ILLEGAL_INPUT, type);
            User user = new User(Database.generateUserId(), type, username, hashedPass);
            Database.Users.add(user);
            return user;
        }

        return signUp(ConsoleConstants.PASSWORD_NOT_MATCH, type);
    }

    private boolean isDuplicateUsername(String username) {
        for (User user : Database.Users)
            if (user.getUsername().equals(username))
                return true;
        return false;
    }

    private String hash(String input) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");

            messageDigest.update(input.getBytes());
            return new String(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
