/**
 * This class is a money market account. It is a subclass of Savings.
 * 
 * @author Jizhou Yang
 *  
 */
package com.example.project3;
public class MoneyMarket extends Savings {

    private int withdrawals;

    public MoneyMarket(Profile holder, double balance) {
        super(holder, balance, true);

        if (balance < 2000) {
            System.out.println("Insufficient balance to open Money Market account.");
        
        }

        withdrawals = 0;
    }

    
    public MoneyMarket(Profile holder) {
        super(holder);
    }

    
    //withdraw method
    public void withdraw(double amount) {
        if (getBalance() >= amount) {
            setBalance(getBalance() - amount);
            withdrawals++;

            if (withdrawals > 3) {
                setBalance(getBalance() - 10);
            }
            
         
        } else {
            System.out.println("Insufficient balance for withdrawal.");
        }
    }
//deposit method
    public void deposit(double amount) {
        setBalance(getBalance() + amount);

        
        checkLoyalCustomerStatus();
    }



    //setisLoyal method
    private void setIsLoyal(boolean isLoyal) {
        setLoyal(isLoyal);
    }


  
    //check loyal customer status
    private void checkLoyalCustomerStatus() {
        if (getBalance() < 2000) {
            setIsLoyal(false);
        } else {
            setIsLoyal(true);
        }
    }

    @Override
    public double monthlyInterest() {
        double annualInterestRate = isLoyal() ? 0.0475 : 0.045; // 4.75% or 4.5%
        double monthlyInterestRate = annualInterestRate / 12;
        return monthlyInterestRate * getBalance();
    }

    @Override
    public double monthlyFee() {
        if (getBalance() >= 2000) {
            return 0;
    
        } else 
            return 25.0;
        
    }

    // Getter for withdrawals
    public int getWithdrawals() {
        return withdrawals;
    }

    // Setter for withdrawals
    public void setWithdrawals(int withdrawals) {
        this.withdrawals = withdrawals;
    }

    // Override toString() method
    @Override
    public String toString() {
        return getHolder() + " (MM)";
    }


  
}
