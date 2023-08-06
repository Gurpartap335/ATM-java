package org.example;

import java.util.Scanner;

public class ATM {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        Bank theBank = new Bank("Indian Bank");

        // add a user, which also creates a savings account
        User user = theBank.addUser("travis", "scott", "2353");

        Account newAccount = new Account("Checking", user, theBank);
        user.addAccount(newAccount);
        theBank.addAccount(newAccount);

        // login
        User curUser;
        while (true) {
            // stay in the login prompt until successful login
            curUser = ATM.mainMenuPrompt(theBank, s);

            // stay in main menu until user quits
            ATM.printUserMenu(curUser, s);
        }
    }

    // there is no data in class, so we can use static method
    public static User mainMenuPrompt(Bank theBank, Scanner s) {
        String userID;
        String pin;
        User authUser;

        do {
            System.out.printf("Welcome to %s%n", theBank.getName());
            System.out.println("Enter user ID : ");
            userID = s.nextLine();
            System.out.println("Enter pin: ");
            pin = s.nextLine();

            authUser = theBank.userLogIn(userID, pin);
            if (authUser == null) {
                System.out.println("Incorrect user ID/pin combination " + "Please try again.");

            }
        } while (authUser == null);

        return authUser;
    }



    public static void printUserMenu(User theUser, Scanner s) {
        theUser.printAccountSummary();
        int choice = 0;

        do {
            System.out.printf("Welcome %s, what would you like to do?%n", theUser.getFirstName());
            System.out.println();
            System.out.println(" 1 Show account transaction history");
            System.out.println(" 2 Withdrawal");
            System.out.println(" 3 Deposit");
            System.out.println(" 4 Transfer");
            System.out.println(" 5 Quit");

            System.out.println();
            System.out.println("Enter choice: ");
            choice = s.nextInt();
            if (choice < 1 || choice > 5) {
                System.out.println("Invalid choice, Please choose 1-5");
            }
        } while (choice < 1 || choice > 5);


        switch (choice) {
            case 1:
                ATM.showTransactionHistory(theUser, s);
                break;
            case 2:
                ATM.withDrawFunds(theUser, s);
                break;
            case 3:
                ATM.depositFunds(theUser, s);
                break;
            case 4:
                ATM.transferFunds(theUser, s);
                break;
            case 5:
                s.nextLine();
                break;
        }
        if (choice != 5) {
            ATM.printUserMenu(theUser, s);
        }
    }



    /**
     * Show the transaction history for an account
     * @param theUser the logged-in User object
     * @param sc the Scanner object used for user input
     */
    public static void showTransactionHistory(User theUser, Scanner sc) {
        int theAcct;

        // get whose account transaction history to look at
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    " whose transactions you want to see",
                    theUser.numAccount());
            theAcct = sc.nextInt() - 1;
        } while (theAcct < 0 || theAcct >= theUser.numAccount());
        theUser.printAccountTransactionHistory(theAcct);
    }

    public static void transferFunds(User theUser, Scanner s) {
        int fromAcct;
        int toAcct;
        double amount;
        double accountBalance;

        // get the account to transfer from
        do {
            System.out.printf("Enter the number (1-%d) of the account%n" +
                    "to transfer from: ", theUser.numAccount());
            fromAcct = s.nextInt()-1;
            if (fromAcct < 0 || fromAcct >= theUser.numAccount()) {
                System.out.println("Invalid account. Please try again.");
            }
        } while (fromAcct < 0 || fromAcct >= theUser.numAccount());
        accountBalance = theUser.getAccountBalance(fromAcct);


        // get the account to transfer to
        do {
            System.out.printf("Enter the number (1-%d) of the account%n" +
                    "to transfer from: ", theUser.numAccount());
            toAcct = s.nextInt()-1;
            if (toAcct < 0 || toAcct >= theUser.numAccount()) {
                System.out.println("Invalid account. Please try again.");
            }
        } while (toAcct < 0 || toAcct >= theUser.numAccount());
        accountBalance = theUser.getAccountBalance(fromAcct);


        // get the amount to transfer
        do {
            System.out.printf("Enter the amount to transfer (max $%.02f): $",
                    accountBalance);
            amount = s.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than zero");
            } else if (amount > accountBalance) {
                System.out.printf("Account must not be greater than%n" +
                        "balance of $%.02f %n", accountBalance);
            }
        } while (amount < 0 || amount > accountBalance);


        // finally do the transfer
        theUser.addAccountTransaction(fromAcct, -1*amount, String.format(
                "Transfer to account %s", theUser.getUUID()));

        theUser.addAccountTransaction(toAcct, -1*amount, String.format(
                "Transfer to account %s", theUser.getUUID()));
    }


    public static void withDrawFunds(User theUser, Scanner s) {
        int fromAcct;
        double amount;
        double accountBalance;
        String memo;


        // get the account to transfer from
        do {
            System.out.printf("Enter the number (1-%d) of the account%n" +
                    "to withdraw from: ", theUser.numAccount());
            fromAcct = s.nextInt()-1;
            if (fromAcct < 0 || fromAcct >= theUser.numAccount()) {
                System.out.println("Invalid account. Please try again.");
            }
        } while (fromAcct < 0 || fromAcct >= theUser.numAccount());
        accountBalance = theUser.getAccountBalance(fromAcct);


        // get the amount to transfer
        do {
            System.out.printf("Enter the amount to transfer (max $%.02f): $",
                    accountBalance);
            amount = s.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than zero");
            } else if (amount > accountBalance) {
                System.out.printf("Account must not be greater than%n" +
                        "balance of $%.02f %n", accountBalance);
            }
        } while (amount < 0 || amount > accountBalance);

        // gobble up rest of previous input
        s.nextLine();

        System.out.println("Enter a memo: ");
        memo = s.nextLine();

        theUser.addAccountTransaction(fromAcct, -1*amount, memo);
    }

    public static void depositFunds(User theUser, Scanner s) {
        int toAcct;
        double amount;
        double accountBalance;
        String memo;


        // get the account to transfer from
        do {
            System.out.printf("Enter the number (1-%d) of the account%n" +
                    "to deposit in ", theUser.numAccount());
            toAcct = s.nextInt()-1;
            if (toAcct < 0 || toAcct >= theUser.numAccount()) {
                System.out.println("Invalid account. Please try again.");
            }
        } while (toAcct < 0 || toAcct >= theUser.numAccount());
        accountBalance = theUser.getAccountBalance(toAcct);


        // get the amount to transfer
        do {
            System.out.printf("Enter the amount to transfer (max $%.02f): $",
                    accountBalance);
            amount = s.nextDouble();
            if (amount < 0) {
                System.out.println("Amount must be greater than zero");
            }
        } while (amount < 0);

        // gobble up rest of previous input
        s.nextLine();

        System.out.println("Enter a memo: ");
        memo = s.nextLine();

        theUser.addAccountTransaction(toAcct, amount, memo);
    }





}

// can we have only one scanner in class?
// you can not have more than one scanner reading thought system.in
