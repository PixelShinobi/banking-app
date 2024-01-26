
/**
 *
 * This class is the controller for the GUI for the project.
 * It handles and execute all actions and command from the GUI.
 *
 * @author Jizhou Yang
 *
 */

package com.example.project3;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.control.Alert.AlertType;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;



public class TransactionManagerController {

// initialize all the buttons, textfields, radio buttons, checkboxes, and output box
    @FXML
    private Button OpenAccountButton, CloseAccountButton, DepositButton, WithdrawButton, DisplayAccountsButton, DisplayAccountsWithInterestsAndFeesButton, UpdateBlanceButton, LoadaccountsFromFileButton, ClearButton;
    @FXML
    private TextField FirstNameTextfield1, LastNameTextfield1, initialBalanceTextfield, amountTextfield;
    @FXML
    private TextField FirstNameTextfield2, LastNameTextfield2;
    @FXML
    private DatePicker Dob1, Dob2;
    @FXML
    private RadioButton Checking1, Savings1, CollageChecking1, MoneyMarket1;
    @FXML
    private RadioButton Checking2, Savings2, CollageChecking2, MoneyMarket2;
    @FXML
    private RadioButton NB, Newark, Camden;
    @FXML
    private CheckBox Loyalty;
    @FXML
    private TextArea OutputBox;

    //open account using textfield input
    @FXML
    private void handleOpenAccountCommand(ActionEvent event) {
        try {
            String accountType = "";
            if (Checking1.isSelected()) {
                accountType = "C";
            } else if (Savings1.isSelected()) {
                accountType = "S";
            } else if (CollageChecking1.isSelected()) {
                accountType = "CC";
            } else if (MoneyMarket1.isSelected()) {
                accountType = "MM";
            }

            String firstName = FirstNameTextfield1.getText();
            String lastName = LastNameTextfield1.getText();
            String dateOfBirth = Dob1.getValue().toString();
            double initialDeposit;
            try {
                initialDeposit = Double.parseDouble(initialBalanceTextfield.getText());
            } catch (NumberFormatException nfe) {
                OutputBox.appendText("Not a valid amount.\n");
                return;
            }

            if (initialDeposit <= 0) {
                OutputBox.appendText("Initial deposit cannot be 0 or negative.\n");
                return;
            }

            String campusCodeOrloyalty = "";

            Date date = new Date(dateOfBirth);

            if (accountType.equals("CC")) {
                if (date.isValid() == true) {
                    int CURRENT_YEAR = 2023;
                    int minAge = 16;
                    int maxAge = 24;
                    int year = date.getYear();
                    if (year > CURRENT_YEAR - minAge || year < CURRENT_YEAR - maxAge) {
                        OutputBox.appendText("DOB invalid: " + date + date.isValidwithReason(accountType) + "\n");
                        return;
                    }
                }

                if (date.isValid() == false) {
                    OutputBox.appendText("DOB invalid: " + date + date.isValidwithReason(accountType) + "\n");
                    return;
                }

            }

            if (!date.isValid()) {
                OutputBox.appendText("DOB invalid: " + date + date.isValidwithReason(accountType) + "\n");
                return;
            }

            Profile holder = new Profile(firstName, lastName, date);

            Account newAccount;


            AccountDatabase accountDatabase = AccountDatabase.getInstance();

            switch (accountType) {
                case "C":

                    if (accountDatabase.contains(new Checking(holder))) {
                        newAccount = new Checking(holder, initialDeposit);
                        OutputBox.appendText(newAccount + " Account is already in the database.\n");
                        return;
                    }
                    newAccount = new Checking(holder, initialDeposit);
                    break;

                case "CC":

                    if (NB.isSelected()) {
                        campusCodeOrloyalty = "0";

                    } else if (Newark.isSelected()) {
                        campusCodeOrloyalty = "1";
                    }
                    if (Loyalty.isSelected()) {
                        campusCodeOrloyalty = "2";
                    }

                    if (campusCodeOrloyalty.equals("0") || campusCodeOrloyalty.equals("1") || campusCodeOrloyalty.equals("2")) {
                        newAccount = new CollegeChecking(holder, initialDeposit, new Campus(campusCodeOrloyalty));
                        break;
                    } else {
                        OutputBox.appendText("Invalid campus code.\n");
                        return;
                    }

                case "S":


                    boolean isLoyal = false;

                    if (Loyalty.isSelected()) {
                        isLoyal = true;
                    }

                    newAccount = new Savings(holder, initialDeposit, isLoyal);
                    break;

                case "MM":
                    if (initialDeposit < 2000) {

                        OutputBox.appendText("Minimum of $2000 to open a Money Market account.\n");
                        return;
                    }
                    newAccount = new MoneyMarket(holder, initialDeposit);
                    break;
                default:
                    OutputBox.appendText("Invalid account type.\n");
                    return;
            }

            if (accountDatabase.open(newAccount)) {
                OutputBox.appendText(newAccount + " opened. \n");
            } else if (accountDatabase.contains(newAccount)) {
                OutputBox.appendText(newAccount + " is already in the database.\n");

            }


        } catch (Exception e) {
            OutputBox.appendText("Missing data for opening an account.\n");
        }

    }

    //close account using textfield input
    @FXML
    private void handleCloseAccountCommand(ActionEvent event) {


        try {
            String accountType = "";
            if (Checking1.isSelected()) {
                accountType = "C";
            } else if (Savings1.isSelected()) {
                accountType = "S";
            } else if (CollageChecking1.isSelected()) {
                accountType = "CC";
            } else if (MoneyMarket1.isSelected()) {
                accountType = "MM";
            }

            String firstName = FirstNameTextfield1.getText();
            String lastName = LastNameTextfield1.getText();
            String dateOfBirth = Dob1.getValue().toString();

            Date date = new Date(dateOfBirth);


            if (!date.isValid()) {
                OutputBox.appendText("DOB invalid: " + date + date.isValidwithReason(accountType) + "\n");
                return;
            }

            Profile holder = new Profile(firstName, lastName, date);
            Account account;

            switch (accountType) {
                case "C":
                    account = new Checking(holder);
                    break;
                case "CC":
                    account = new CollegeChecking(holder);
                    break;
                case "S":
                    account = new Savings(holder);
                    break;
                case "MM":
                    account = new MoneyMarket(holder);
                    break;
                default:
                    OutputBox.appendText("Invalid account type.\n");
                    return;
            }

            AccountDatabase accountDatabase = AccountDatabase.getInstance();
            if (accountDatabase.close(account)) {
                OutputBox.appendText(account + " has been closed.\n");
            } else {
                OutputBox.appendText(account + " is not in the database.\n");
            }
        } catch (Exception e) {
            OutputBox.appendText("An error occurred while closing the account.\n");
        }


    }

    //deposit using textfield input
    @FXML
    private void handleDepositCommand(ActionEvent event) {
        try {
            String accountType = "";
            if (Checking2.isSelected()) {
                accountType = "C";
            } else if (Savings2.isSelected()) {
                accountType = "S";
            } else if (CollageChecking2.isSelected()) {
                accountType = "CC";
            } else if (MoneyMarket2.isSelected()) {
                accountType = "MM";
            }


            String firstName = FirstNameTextfield2.getText();
            String lastName = LastNameTextfield2.getText();
            String dateOfBirth = Dob2.getValue().toString();

            double amount;
            try {
                amount = Double.parseDouble(amountTextfield.getText());
                if (amount <= 0) {
                    OutputBox.appendText("Deposit amount cannot be 0 or negative.\n");
                    return;
                }
            } catch (NumberFormatException nfe) {
                OutputBox.appendText("Not a valid amount.\n");
                return;
            }

            Date date = new Date(dateOfBirth);
            if (!date.isValid()) {
                OutputBox.appendText("DOB invalid: " + date + date.isValidwithReason(accountType) + "\n");
                return;
            }

            Profile holder = new Profile(firstName, lastName, date);
            Account account;

            switch (accountType) {
                case "C":
                    account = new Checking(holder);
                    break;
                case "CC":
                    account = new CollegeChecking(holder);
                    break;
                case "S":
                    account = new Savings(holder);
                    break;
                case "MM":
                    account = new MoneyMarket(holder);
                    break;
                default:
                    OutputBox.appendText("Invalid account type.\n");
                    return;
            }

            AccountDatabase accountDatabase = AccountDatabase.getInstance();

            if (accountDatabase.contains(account)) {
                accountDatabase.deposit(account, amount);
                OutputBox.appendText(account + " Deposit - balance updated.\n");
            } else {
                OutputBox.appendText(account + " is not in the database.\n");
            }

        } catch (Exception e) {
            OutputBox.appendText("Missing data for depositing into an account.\n");
        }

    }

    //withdraw using textfield input
    @FXML
    private void handleWithdrawCommand(ActionEvent event) {
        try {

            String accountType = "";
            if (Checking2.isSelected()) {
                accountType = "C";
            } else if (Savings2.isSelected()) {
                accountType = "S";
            } else if (CollageChecking2.isSelected()) {
                accountType = "CC";
            } else if (MoneyMarket2.isSelected()) {
                accountType = "MM";
            }


            String firstName = FirstNameTextfield2.getText();
            String lastName = LastNameTextfield2.getText();
            String dateOfBirth = Dob2.getValue().toString();

            double amount;
            try {
                amount = Double.parseDouble(amountTextfield.getText());
            } catch (NumberFormatException nfe) {
                OutputBox.appendText("Not a valid amount.\n");
                return;
            }

            if (amount <= 0) {
                OutputBox.appendText("Withdraw - amount cannot be 0 or negative.\n");
                return;
            }

            Date date = new Date(dateOfBirth);

            if (!date.isValid()) {
                OutputBox.appendText("DOB invalid: " + date + date.isValidwithReason(accountType) + "\n");
                return;
            }

            Account account;

            Profile holder = new Profile(firstName, lastName, date);

            switch (accountType) {
                case "C":
                    account = new Checking(holder);
                    break;
                case "CC":
                    account = new CollegeChecking(holder);
                    break;
                case "S":
                    account = new Savings(holder);
                    break;
                case "MM":
                    account = new MoneyMarket(holder);
                    break;
                default:
                    OutputBox.appendText("Invalid account type.\n");
                    return;
            }

            AccountDatabase accountDatabase = AccountDatabase.getInstance();

            if (accountDatabase.withdraw(account, amount)) {
                OutputBox.appendText(account + " Withdraw - balance updated.\n");
            } else {
                OutputBox.appendText(account + " is not in the database.\n");
            }


        } catch (Exception e) {
            OutputBox.appendText("Missing Data for withdrawing from an account.\n");
        }


    }

    //display all accounts in GUI
    @FXML
    private void handleDisplayAccountsCommand(ActionEvent event) {

        AccountDatabase accountDatabase = AccountDatabase.getInstance();
        Account[] allAccounts = accountDatabase.getAllAccounts();

        if (allAccounts.length == 0) {
            OutputBox.appendText("Account Database is empty!\n");
            return;
        }

        String sortedAccountsStr = accountDatabase.printSorted();
        OutputBox.appendText(sortedAccountsStr);
    }

    //display all accounts with interests and fees in GUI
    @FXML
    private void handleDisplayAccountsWithInterestsAndFeesCommand(ActionEvent event) {

        AccountDatabase accountDatabase = AccountDatabase.getInstance();
        Account[] allAccounts = accountDatabase.getAllAccounts();

        if (allAccounts.length == 0) {
            OutputBox.appendText("Account Database is empty!\n");
            return;
        }

        String feesAndInterestsStr = accountDatabase.printFeesAndInterests();
        OutputBox.appendText(feesAndInterestsStr);

    }

    //update account balances in GUI
    @FXML
    private void handleUpdateBalancesCommand(ActionEvent event) {

        AccountDatabase accountDatabase = AccountDatabase.getInstance();
        Account[] allAccounts = accountDatabase.getAllAccounts();

        if (allAccounts.length == 0) {
            OutputBox.appendText("Account Database is empty!\n");
            return;
        }

        String updatedBalancesStr = accountDatabase.printUpdatedBalances();
        OutputBox.appendText(updatedBalancesStr);
    }


    //open account using file input
    @FXML
    private void handleLoadAccountsFromFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open bankAccount File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                String line;
                while ((line = reader.readLine()) != null) {

                    String[] data = line.split(",");
                    String accountType = data[0];
                    String firstName = data[1];
                    String lastName = data[2];
                    String dob = data[3];

                    String[] dateX = dob.split("/");

                    String newDob = dateX[2] + "-" + dateX[0] + "-" + dateX[1];


                    double initialDeposit;
                    try {
                        initialDeposit = Double.parseDouble(data[4]);
                    } catch (NumberFormatException nfe) {
                        OutputBox.appendText("Not a valid amount.\n");
                        return;
                    }

                    if (initialDeposit <= 0) {
                        OutputBox.appendText("Initial deposit cannot be 0 or negative.\n");
                        return;
                    }

                    String campusCodeOrloyalty = "";

                    Date date = new Date(newDob);

                    if (accountType.equals("CC")) {
                        if (date.isValid() == true) {
                            int CURRENT_YEAR = 2023;
                            int minAge = 16;
                            int maxAge = 24;
                            int year = date.getYear();
                            if (year > CURRENT_YEAR - minAge || year < CURRENT_YEAR - maxAge) {
                                OutputBox.appendText("DOB invalid: " + date + date.isValidwithReason(accountType) + "\n");
                                return;
                            }
                        }

                        if (date.isValid() == false) {
                            OutputBox.appendText("DOB invalid: " + date + date.isValidwithReason(accountType) + "\n");
                            return;
                        }

                    }

                    if (!date.isValid()) {
                        OutputBox.appendText("DOB invalid: " + date + date.isValidwithReason(accountType) + "\n");
                        return;
                    }

                    Profile holder = new Profile(firstName, lastName, date);

                    Account newAccount;


                    AccountDatabase accountDatabase = AccountDatabase.getInstance();

                    switch (accountType) {
                        case "C":

                            if (accountDatabase.contains(new Checking(holder))) {
                                newAccount = new Checking(holder, initialDeposit);
                                OutputBox.appendText(newAccount + " Account is already in the database.\n");
                                return;
                            }
                            newAccount = new Checking(holder, initialDeposit);
                            break;

                        case "CC":

                            if (Loyalty.isSelected()) {
                                campusCodeOrloyalty = "1";
                            } else {
                                campusCodeOrloyalty = "0";
                            }
                            if (campusCodeOrloyalty.equals("0") || campusCodeOrloyalty.equals("1") || campusCodeOrloyalty.equals("2")) {
                                newAccount = new CollegeChecking(holder, initialDeposit, new Campus(campusCodeOrloyalty));
                                break;
                            } else {
                                OutputBox.appendText("Invalid campus code.\n");
                                return;
                            }

                        case "S":


                            boolean isLoyal = false;

                            if (Loyalty.isSelected()) {
                                isLoyal = true;
                            }

                            newAccount = new Savings(holder, initialDeposit, isLoyal);
                            break;

                        case "MM":
                            if (initialDeposit < 2000) {

                                OutputBox.appendText("Minimum of $2000 to open a Money Market account.\n");
                                return;
                            }
                            newAccount = new MoneyMarket(holder, initialDeposit);
                            break;
                        default:
                            OutputBox.appendText("Invalid account type.\n");
                            return;
                    }

                    if (accountDatabase.open(newAccount)) {
                        OutputBox.appendText(newAccount + " opened. \n");
                    } else if (accountDatabase.contains(newAccount)) {
                        OutputBox.appendText(newAccount + " is already in the database.\n");

                    }


                }
            } catch (Exception e) {
                OutputBox.appendText("Missing data for opening an account.\n");
            }

        }


    }


   //clear output box
    @FXML
    private void  clearOutputBox(ActionEvent event){
        OutputBox.setText("");
    }



}