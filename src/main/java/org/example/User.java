package org.example;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.UUID;

public class User {

    private final String firstName;

    private final String lastName;

    // ID number of the user
    private final String uuid;

    // The MD5 hash of the user's pin
    private byte[] pinHash;

    // The list of accounts for this user
    private final ArrayList<Account> accounts;

    public User(String firstName, String lastName, String pin, Bank theBank) {
        this.firstName = firstName;
        this.lastName = lastName;

        // store the pin's MD5 hash, rather than the original value for security reasons
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            pinHash = md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error, caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }

        uuid = theBank.getNewUserUUID();

        accounts = new ArrayList<>();

        // print log message
        System.out.printf("New user %s, %s with ID %s created.%n" , lastName, firstName, uuid);

    }

    public void addAccount(Account anAccount) {
        this.accounts.add(anAccount);
    }

    public String getUUID() {
        return uuid;
    }

    public String getFirstName() {
        return firstName;
    }

    /**
     * Check whether a given pin matches the true User pin
     * @param aPin the pin to check
     * @return whether the pin is valid or not
     */
    public boolean validatePin(String aPin) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return MessageDigest.isEqual(md.digest(aPin.getBytes()), this.pinHash);
        } catch(NoSuchAlgorithmException e){
            System.out.println("error, caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }

    public void printAccountSummary() {
        System.out.printf("%n%s's accounts summary%n", this.firstName);
        for (int i = 0; i < this.accounts.size(); i++) {
            System.out.printf("%d %s\n", i + 1,  this.accounts.get(i).getSummaryLine());
        }
        System.out.println();
    }

    public int numAccount() {
        return this.accounts.size();
    }

    public void printAccountTransactionHistory(int accIdx) {
        this.accounts.get(accIdx).printTransactionHistory();
    }

    public double getAccountBalance(int accIdx) {
        return this.accounts.get(accIdx).getBalance();
    }

    public void addAccountTransaction(int accIdx, double amount, String memo) {
        this.accounts.get(accIdx).addTransaction(amount, memo);
    }

}


// Is it necessary to use this operator in the constructor?