/**
 * 
 * This class represents a Savings account. It is a subclass of Account.
 * @author Jizhou Yang
 *  
 */
package com.example.project3;
public class Savings extends Account {

    protected boolean isLoyal;

     public Savings(Profile holder, double initialDeposit, boolean isLoyal) {
        super(holder, initialDeposit);
        this.isLoyal = isLoyal;
    }




    public Savings(Profile holder) {
        super(holder);
    }

    //withdraw method
    @Override
    public double monthlyFee() {
       
        return balance >= 500 ? 0 : 5.0;
    }


    // Implement logic for calculating monthly interest for Savings account 
    @Override
    public double monthlyInterest() {
       
        double annualInterestRate = isLoyal ? 0.0425 : 0.04;
        double monthlyInterestRate = annualInterestRate / 12;
        return monthlyInterestRate * balance;
    }


    // Getter and Setter for isLoyal
    public boolean isLoyal() {
        return isLoyal;
    }

   //set loyal 
    public void setLoyal(boolean loyal) {
        isLoyal = loyal;
    }


    @Override
    public String toString() {
        return holder.toString() + " (S)";
    }

    
    
}
