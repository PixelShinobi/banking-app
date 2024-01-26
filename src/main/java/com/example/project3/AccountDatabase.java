/**
 * This class is used to store all the accounts in an array and perform operations on the accounts.
 * 
 * @author Jizhou Yang
 *  
 */
package com.example.project3;


public class AccountDatabase {
    private Account[] accounts; 
    private int numAcct;
    private static AccountDatabase instance;

    public AccountDatabase() {
        accounts = new Account[4];
        numAcct = 0;
    }

    public static AccountDatabase getInstance() {
        if (instance == null) {
            instance = new AccountDatabase();
        }
        return instance;
    }


    //find method to find if there is a existed account that is the same with new input
    private int find(Account account) {
        for (int i = 0; i < numAcct; i++) {
            if (accounts[i].getHolder().getLname().compareToIgnoreCase(account.getHolder().getLname()) == 0 && 
                    accounts[i].getHolder().getFname().compareToIgnoreCase(account.getHolder().getFname()) == 0 && 
                        accounts[i].getHolder().getDob().compareTo(account.getHolder().getDob()) == 0) {
                return i;
            }
        }
        return -1;
    }

    //expand array to keep track of account
    private void grow() {
        Account[] newAccounts = new Account[accounts.length + 4];
        for (int i = 0; i < numAcct; i++) {
            newAccounts[i] = accounts[i];
        }
        accounts = newAccounts;
    }

    //check if account is contained in the account
    public boolean contains(Account account) {
        int index = find(account);
        if (index == -1) {

            return false;
        } else {

            Account existingAccount = accounts[index];
            return existingAccount.getClass().equals(account.getClass());
        }
    }
    
    //open a new account by set an array of index =account
    public boolean open(Account account) {
        if (contains(account)) {
            return false;
        }
        if (numAcct >= accounts.length) {
            grow();
        }
        accounts[numAcct++] = account;
        return true;
    }
    //close method that is used to close a account
    public boolean close(Account account) {
        int index = find(account);
        if (index == -1) {
            return false;
        }
        for (int i = index; i < numAcct - 1; i++) {
            accounts[i] = accounts[i + 1];
        }
        numAcct--;
        return true;
    }

    //withdraw method
    public boolean withdraw(Account account, double amount) {
        int index = find(account);
        if (index == -1) {
            return false;
        }
       else{
            accounts[index].setBalance(accounts[index].getBalance() - amount);
       }
        return true;
    }
    //deposit method
    public void deposit(Account account, double amount) {
        int index = find(account);
        if (index != -1) {
            double deposited = accounts[index].getBalance() + amount;
            accounts[index].setBalance(deposited);
        }
    }


    public Account[] getAllAccounts() {
        Account[] allAccounts = new Account[numAcct];
        for (int i = 0; i < numAcct; i++) {
            allAccounts[i] = accounts[i];
        }
        return allAccounts;
    }

    //print the sorted list of accounts
    public String printSorted() {
        StringBuilder output = new StringBuilder();
        output.append("\n*Accounts sorted by account type and profile.\n");

        for (int i = 0; i < numAcct - 1; i++) {
            for (int j = 0; j < numAcct - 1 - i; j++) {
                Account account1 = accounts[j];
                Account account2 = accounts[j + 1];
                int comparison = account1.getAccountType().compareTo(account2.getAccountType());
                if (comparison > 0 || 
                   (comparison == 0 && account1.getHolder().getLname().compareTo(account2.getHolder().getLname()) > 0) ||
                   (comparison == 0 && account1.getHolder().getLname().equals(account2.getHolder().getLname()) 
                   && account1.getHolder().getFname().compareTo(account2.getHolder().getFname()) > 0) ||
                   (comparison == 0 && account1.getHolder().getLname().equals(account2.getHolder().getLname()) 
                   && account1.getHolder().getFname().equals(account2.getHolder().getFname()) 
                   && account1.getHolder().getDob().compareTo(account2.getHolder().getDob()) > 0)) {
                    accounts[j] = account2;
                    accounts[j + 1] = account1;
                }
            }
        }
        
        for (int i = 0; i < numAcct - 1; i++) {
            for (int j = 0; j < numAcct - 1 - i; j++) {
                Account account1 = accounts[j];
                Account account2 = accounts[j + 1];
                int comparison = account1.getAccountType().compareTo(account2.getAccountType());
                if (comparison == 0 && account1.getHolder().getLname().compareTo(account2.getHolder().getLname()) > 0) {
                    accounts[j] = account2;
                    accounts[j + 1] = account1;
                }
            }
        }
        
        for (int i = 0; i < numAcct - 1; i++) {
            for (int j = 0; j < numAcct - 1 - i; j++) {
                Account account1 = accounts[j];
                Account account2 = accounts[j + 1];
                int comparison = account1.getAccountType().compareTo(account2.getAccountType());
                if (comparison == 0 && account1.getHolder().getLname().equals(account2.getHolder().getLname()) 
                && account1.getHolder().getFname().compareTo(account2.getHolder().getFname()) > 0) {
                    accounts[j] = account2;
                    accounts[j + 1] = account1;
                }
            }
        }
        
        for (int i = 0; i < numAcct - 1; i++) {
            for (int j = 0; j < numAcct - 1 - i; j++) {
                Account account1 = accounts[j];
                Account account2 = accounts[j + 1];
                int comparison = account1.getAccountType().compareTo(account2.getAccountType());
                if (comparison == 0 && account1.getHolder().getLname().equals(account2.getHolder().getLname()) 
                && account1.getHolder().getFname().equals(account2.getHolder().getFname()) 
                && account1.getHolder().getDob().compareTo(account2.getHolder().getDob()) > 0) {
                    accounts[j] = account2;
                    accounts[j + 1] = account1;
                }
            }
        }

        for (int i = 0; i < numAcct; i++) {
            Account account = accounts[i];
            String holderName = account.getHolder().getFname() + " " + account.getHolder().getLname();
            output.append(account.getAccountType()).append("::").append(holderName).append(" ").
                    append(account.getHolder().getDob().toString()).append(String.format("::Balance $%.2f", account.getBalance()));

            if (account instanceof CollegeChecking) {
                output.append("::").append(((CollegeChecking) account).getCampus());
            }
            if (account instanceof Savings) {
                if(((Savings) account).isLoyal()){
                    output.append("::is loyal");
                }
            }
            if (account instanceof MoneyMarket) {
                output.append("::withdrawal: ").append(((MoneyMarket) account).getWithdrawals());
            }

            output.append("\n");
        }
        output.append("*end of list\n");
        return output.toString();
    }

    
    public String printFeesAndInterests() {
        StringBuilder output = new StringBuilder();
        output.append("\n*list of accounts with fee and monthly interest\n");
    
        for (int i = 0; i < numAcct - 1; i++) {
            for (int j = 0; j < numAcct - 1 - i; j++) {
                Account account1 = accounts[j];
                Account account2 = accounts[j + 1];
                int comparison = account1.getAccountType().compareTo(account2.getAccountType());
                if (comparison > 0 || 
                   (comparison == 0 && account1.getHolder().getLname().compareTo(account2.getHolder().getLname()) > 0) ||
                   (comparison == 0 && account1.getHolder().getLname().equals(account2.getHolder().getLname()) 
                   && account1.getHolder().getFname().compareTo(account2.getHolder().getFname()) > 0) ||
                   (comparison == 0 && account1.getHolder().getLname().equals(account2.getHolder().getLname()) 
                   && account1.getHolder().getFname().equals(account2.getHolder().getFname()) 
                   && account1.getHolder().getDob().compareTo(account2.getHolder().getDob()) > 0)) {
                    accounts[j] = account2;
                    accounts[j + 1] = account1;
                }
            }
        }
        
        for (int i = 0; i < numAcct - 1; i++) {
            for (int j = 0; j < numAcct - 1 - i; j++) {
                Account account1 = accounts[j];
                Account account2 = accounts[j + 1];
                int comparison = account1.getAccountType().compareTo(account2.getAccountType());
                if (comparison == 0 && account1.getHolder().getLname().compareTo(account2.getHolder().getLname()) > 0) {
                    accounts[j] = account2;
                    accounts[j + 1] = account1;
                }
            }
        }
        
        for (int i = 0; i < numAcct - 1; i++) {
            for (int j = 0; j < numAcct - 1 - i; j++) {
                Account account1 = accounts[j];
                Account account2 = accounts[j + 1];
                int comparison = account1.getAccountType().compareTo(account2.getAccountType());
                if (comparison == 0 && account1.getHolder().getLname().equals(account2.getHolder().getLname()) 
                && account1.getHolder().getFname().compareTo(account2.getHolder().getFname()) > 0) {
                    accounts[j] = account2;
                    accounts[j + 1] = account1;
                }
            }
        }
        
        for (int i = 0; i < numAcct - 1; i++) {
            for (int j = 0; j < numAcct - 1 - i; j++) {
                Account account1 = accounts[j];
                Account account2 = accounts[j + 1];
                int comparison = account1.getAccountType().compareTo(account2.getAccountType());
                if (comparison == 0 && account1.getHolder().getLname().equals(account2.getHolder().getLname()) 
                && account1.getHolder().getFname().equals(account2.getHolder().getFname()) 
                && account1.getHolder().getDob().compareTo(account2.getHolder().getDob()) > 0) {
                    accounts[j] = account2;
                    accounts[j + 1] = account1;
                }
            }
        }
        for (int i = 0; i < numAcct; i++) {
            Account account = accounts[i];
            String holderName = account.getHolder().getFname() + " " + account.getHolder().getLname();
            output.append(account.getAccountType()).append("::").append(holderName).append(" ").
                    append(account.getHolder().getDob().toString()).append(String.format("::Balance $%.2f", account.getBalance()));

            if (account instanceof CollegeChecking) {
                output.append("::").append(((CollegeChecking) account).getCampus());
            }
            if (account instanceof Savings) {
                if(((Savings) account).isLoyal()){
                    output.append("::is loyal");
                }
            }
            if (account instanceof MoneyMarket) {
                output.append("::withdrawal: ").append(((MoneyMarket) account).getWithdrawals());
            }

            output.append(String.format("::fee $%.2f", account.getMonthlyFee())).
                    append(String.format("::monthly interest $%.2f", account.getMonthlyInterest()));

            output.append("\n");
        }

        output.append("*end of list\n");
        return output.toString();
    }

    //print the updated balances
    public String printUpdatedBalances() {
        StringBuilder output = new StringBuilder();
        output.append("\n*list of accounts with fees and interests applied.\n");

        for (int i = 0; i < numAcct - 1; i++) {
            for (int j = 0; j < numAcct - 1 - i; j++) {
                Account account1 = accounts[j];
                Account account2 = accounts[j + 1];
                int comparison = account1.getAccountType().compareTo(account2.getAccountType());
                if (comparison > 0 || 
                   (comparison == 0 && account1.getHolder().getLname().compareTo(account2.getHolder().getLname()) > 0) ||
                   (comparison == 0 && account1.getHolder().getLname().equals(account2.getHolder().getLname()) 
                   && account1.getHolder().getFname().compareTo(account2.getHolder().getFname()) > 0) ||
                   (comparison == 0 && account1.getHolder().getLname().equals(account2.getHolder().getLname()) 
                   && account1.getHolder().getFname().equals(account2.getHolder().getFname()) 
                   && account1.getHolder().getDob().compareTo(account2.getHolder().getDob()) > 0)) {
                    accounts[j] = account2;
                    accounts[j + 1] = account1;
                }
            }
        }

        for (int i = 0; i < numAcct - 1; i++) {
            for (int j = 0; j < numAcct - 1 - i; j++) {
                Account account1 = accounts[j];
                Account account2 = accounts[j + 1];
                int comparison = account1.getAccountType().compareTo(account2.getAccountType());
                if (comparison == 0 && account1.getHolder().getLname().compareTo(account2.getHolder().getLname()) > 0) {
                    accounts[j] = account2;
                    accounts[j + 1] = account1;
                }
            }
        }

        for (int i = 0; i < numAcct - 1; i++) {
            for (int j = 0; j < numAcct - 1 - i; j++) {
                Account account1 = accounts[j];
                Account account2 = accounts[j + 1];
                int comparison = account1.getAccountType().compareTo(account2.getAccountType());
                if (comparison == 0 && account1.getHolder().getLname().equals(account2.getHolder().getLname()) 
                && account1.getHolder().getFname().compareTo(account2.getHolder().getFname()) > 0) {
                    accounts[j] = account2;
                    accounts[j + 1] = account1;
                }
            }
        }

        for (int i = 0; i < numAcct - 1; i++) {
            for (int j = 0; j < numAcct - 1 - i; j++) {
                Account account1 = accounts[j];
                Account account2 = accounts[j + 1];
                int comparison = account1.getAccountType().compareTo(account2.getAccountType());
                if (comparison == 0 && account1.getHolder().getLname().equals(account2.getHolder().getLname()) 
                && account1.getHolder().getFname().equals(account2.getHolder().getFname()) 
                && account1.getHolder().getDob().compareTo(account2.getHolder().getDob()) > 0) {
                    accounts[j] = account2;
                    accounts[j + 1] = account1;
                }
            }
        }
        for (int i = 0; i < numAcct; i++) {
            Account account = accounts[i];
            String holderName = account.getHolder().getFname() + " " + account.getHolder().getLname();
            output.append(account.getAccountType()).append("::").append(holderName).append(" ").
                    append(account.getHolder().getDob().toString()).append(String.format("::Balance $%.2f", account.getBalance()));

            if (account instanceof CollegeChecking) {
                output.append("::").append(((CollegeChecking) account).getCampus());
            }
            if (account instanceof Savings) {
                if(((Savings) account).isLoyal()){
                    output.append("::is loyal");
                }
            }
            if (account instanceof MoneyMarket) {
                output.append("::withdrawal: ").append(((MoneyMarket) account).getWithdrawals());
            }

            output.append("\n");
        }

        output.append("*end of list\n");
        return output.toString();
    }
}