package org.example;

import java.util.ArrayList;

public class Account {

    private String name;

    private double balance;

    private String uuid;

    // The User object that own this account
    private User holder;

    // List of transactions for this account.
    private ArrayList<Transaction> transactions;
}
