/**
 * This class represents a checking account. It is a subclass of Account.
 *
 * 
 * @author Jizhou Yang
 *  
 */
package com.example.project3;



public class Checking extends Account {
   
    private static final double INTEREST_RATE = 0.01 / 12;
    private static final double MONTHLY_FEE = 12.0;

    //creat new checking account type
    public Checking(Profile holder, double initialDeposit) {
        super(holder, initialDeposit);
    }

    public Checking(Profile holder) {
        super(holder);
    }

    @Override
    public double monthlyInterest() {
        return balance * INTEREST_RATE;
    }

    @Override
    public double monthlyFee() {
        if (balance >= 1000) {
            return 0.0;
        } else {
            return MONTHLY_FEE;
        }
    }

    @Override
    public String toString() {
        return holder.toString() + " (C)";
    }


}