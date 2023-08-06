package org.example;

import java.util.ArrayList;

public class Account {

    private String name;

    private String uuid;

    // The User object that own this account
    private User holder;

    // List of transactions for this account.
    private ArrayList<Transaction> transactions;

    public Account(String name, User holder, Bank theBank) {
        this.name = name;
        this.holder = holder;

        this.uuid = theBank.getNewAccountUUID();
        this.transactions = new ArrayList<Transaction>();

//        holder.addAccount( this);
//        theBank.addAccount(this);
    }

    public String getUUID() {
        return this.uuid;
    }

    public String getSummaryLine() {
        double balance = this.getBalance();

        // format the summary line, depending on the whether the balance is negative
        if (balance >= 0) {
            return String.format("%s : $%.2f : %s", this.uuid, balance, this.name);
        } else {
            return String.format("%s : $(%.2f) : %s", this.uuid, balance, this.name);
        }
    }

    public double getBalance() {
        double balance = 0;
        for (Transaction t : this.transactions) {
            balance += t.getAmount();
        }
        return balance;
    }


    public void printTransactionHistory() {
        System.out.printf("%n Transaction History for account %s%n", this.uuid);

        for (int i = this.transactions.size() - 1; i >= 0 ; i--) {
            System.out.println(this.transactions.get(i).getSummaryLine());
        }
        System.out.println();
    }


    /**
     * Add a new transaction in this account
     * @param amount the amount transacted
     */
    public void addTransaction(double amount) {
        Transaction newTrans = new Transaction(amount, this);
        this.transactions.add(newTrans);
    }
    public void addTransaction(double amount, String memo) {
        Transaction newTrans = new Transaction(amount, memo, this);
        this.transactions.add(newTrans);
    }

}
