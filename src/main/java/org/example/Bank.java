package org.example;

import java.util.ArrayList;
import java.util.Random;

public class Bank {

    private String name;

    private ArrayList<User> users;

    private ArrayList<Account> accounts;

    public Bank(String name) {
        this.name = name;
        this.users = new ArrayList<User>();
        this.accounts = new ArrayList<Account>();
    }


    public String getNewUserUUID() {
        String uuid;
        Random random = new Random();
        int len = 6;
        boolean nonUnique;

        // continue looping until we get a unique ID
        do {
            uuid = "";
            for (int i = 0; i < len; i++) {
                uuid += ((Integer) random.nextInt(10)).toString();
            }

            nonUnique = false;
            for (User u : this.users) {
                if (uuid.compareTo(u.getUUID()) == 0) {
                    nonUnique = true;
                    break;
                }
            }
        }  while (nonUnique);
        return uuid;
    }


    public String getNewAccountUUID() {
        String uuid;
        Random random = new Random();
        int len = 10;
        boolean nonUnique;

        do {
            uuid = "";
            for (int i = 0; i < len; i++) {
                uuid += ((Integer) random.nextInt(10)).toString();
            }

            nonUnique = false;
            for (Account a : accounts) {
                if (uuid.compareTo(a.getUUID()) == 0) {
                    nonUnique = true;
                    break;
                }
            }
        }  while (nonUnique);
        return uuid;
    }

    /**
     * Add an account
     * @param  anAccount the account to add
     */
    public void addAccount(Account anAccount) {
        this.accounts.add(anAccount);
    }

    public User addUser(String firstName, String lastName, String pin) {
        // Create a new User object and add it to our list
        User newUser = new User(firstName, lastName, pin, this);
        this.users.add(newUser);

        // create a savings account for the user
        Account newAccount = new Account("Savings", newUser, this);
        newUser.addAccount(newAccount);
        this.addAccount(newAccount);
        return newUser;
    }

    /**
     * Get the User object associated with a particular userID and pin, if they
     * are valid.
     * @param userID the uuid of the user to log in
     * @param pin the pin of the user
     * @return the User object
     */
    public User userLogIn(String userID, String pin) {

        for (User u : this.users) {
            if (u.getUUID().compareTo(userID) == 0 && u.validatePin(pin)) {
                return u;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }
}
