

/**
 * This class is for holding the information of an account. It has a profile and a balance of the account.
 * 
 * @author Jizhou Yang
 *  
 */

package com.example.project3;

public abstract class Account implements Comparable<Account> {
    protected Profile holder;
    protected double balance;
    public abstract double monthlyInterest();
    public abstract double monthlyFee();

    //creat a new account type for other class to use
    public Account(Profile holder, double balance) {
        this.holder = holder;
        this.balance = balance;
    }

    public Account(Profile holder) {
        this.holder = holder;
    }

    

    //withdraw method to withdraw
    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
        } else {
            System.out.println("Insufficient balance for withdrawal.");
        }
    }
    
    //deposit method when user is trying to deposit
    public void deposit(double amount) {
        balance += amount;
    }

    // Getter and Setter for holder
    public Profile getHolder() {
        return holder;
    }

    public void setHolder(Profile holder) {
        this.holder = holder;
    }

    // Getter and Setter for balance
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    //getter for account type
    public String getAccountType() {
        return this.getClass().getSimpleName();
    }

    //getter for profile
    public Profile getProfile() {
        return this.holder;
    }

    @Override
    public int compareTo(Account account) {
        return this.holder.compareTo(account.holder);
    }


    //get monthly interest
    public double getMonthlyInterest() {
        return monthlyInterest();
    }

    //get monthly fee
    public double getMonthlyFee() {
        return monthlyFee();
    }
    

    //toString method
    @Override
    public String toString() {
        return "Account{" +
                "holder=" + holder +
                ", balance=" + balance +
                '}';
    }


    //equals method
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Account) {
            Account account = (Account) obj;
            return this.holder.equals(account.holder);
        }
        return false;
    }

    
   }