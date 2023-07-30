package org.example;

import java.util.ArrayList;

public class User {

    private String firstName;

    private String lastName;

    // ID number of the user
    private String uuid;

    // The MD5 hash of the user's pin
    private byte pinHash[];

    // The list of accounts for this user
    private ArrayList<Account> accounts;
}
