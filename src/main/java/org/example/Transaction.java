package org.example;

import java.util.Date;

public class Transaction {

    private final double amount;

    private final Date timeStamp;

    private String memo;


    // the account in which the transaction was performed.
    private final Account inAccount;

    public double getAmount() {
        return this.amount;
    }


    public Transaction(double amount, Account inAccount) {
        this.amount = amount;
        this.inAccount = inAccount;
        timeStamp = new Date();
        memo = "";
    }

    /**
     * Create a new transaction
     * @param amount the amount transacted
     * @param memo   the memo for the transaction
     * @param inAccount the account the transaction belongs to
     */
    public Transaction(double amount, String memo, Account inAccount) {
        this(amount, inAccount);
        this.memo = memo;
    }


    public String getSummaryLine() {
        if (amount >= 0) {
            return String.format("%s : $%.02f : %s", this.timeStamp.toString(),
                    this.amount, this.memo);
        } else {
            return String.format("%s : $(%.02f) : %s", this.timeStamp.toString(),
                    -this.amount, this.memo );
        }
    }
}
