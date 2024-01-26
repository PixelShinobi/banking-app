/**
 * This class is used to represent a date. 
 * It has three instance variables: year, month, and day.
 * It has a method to check if the date is valid.
 * 
 * @author Jizhou Yang
 *  
 */

package com.example.project3;

import java.util.StringTokenizer;

public class Date implements Comparable<Date> {


private static final int QUADRENNIAL = 4;
private static final int CENTENNIAL = 100;
private static final int QUATERCENTENNIAL = 400;
 
 
private int year;
private int month;
private int day;
 
 

//tokenize the date and store it
public Date(String date) {
    StringTokenizer st = new StringTokenizer(date, "-");
    this.year = Integer.parseInt(st.nextToken());
    this.month = Integer.parseInt(st.nextToken());
    this.day = Integer.parseInt(st.nextToken());
}

 
 
//check if the date is a vaild future date
private boolean isFutureDate() {
    int CURRENT_YEAR = 2023; 
    int CURRENT_MONTH = 10; 
    int CURRENT_DAY = 16; 

    if (year > CURRENT_YEAR || 
        (year == CURRENT_YEAR && month > CURRENT_MONTH) ||
        (year == CURRENT_YEAR && month == CURRENT_MONTH && day > CURRENT_DAY)) {
        return true;
    }
    return false;
}

//check if the date is vaild
public boolean isValid() {
    if (year < 1900 || month < 1 || month > 12 || day < 1) {
        return false;
    }

    if (isFutureDate()) {
        return false;
    }


    int daysInMonth;
    switch (month) {
        case 1:
            daysInMonth = 31;
            break;
        case 2:
            daysInMonth = isLeapYear(year) ? 29 : 28;
            break;
        case 3:
            daysInMonth = 31;
            break;
        case 4:
            daysInMonth = 30;
            break;
        case 5:
            daysInMonth = 31;
            break;
        case 6:
            daysInMonth = 30;
            break;
        case 7:
            daysInMonth = 31;
            break;
        case 8:
            daysInMonth = 31;
            break;
        case 9:
            daysInMonth = 30;
            break;
        case 10:
            daysInMonth = 31;
            break;
        case 11:
            daysInMonth = 30;
            break;
        case 12:
            daysInMonth = 31;
            break;
        default:
            daysInMonth = 31;
            break;
    }

    if (day > daysInMonth) {
        return false;
    }


    if (month == 2 && day == 29 && !isLeapYear(year)) {
        return false;
    }

    return true;
}


public String isValidwithReason(String accountType) {
    int CURRENT_YEAR = 2023; 
    int minAge = 16;
    int maxAge = 24;
    
    
    if (isFutureDate()) {
        return " cannot be today or a future day";
    }
    
    
    if ("CC".equals(accountType)) {
        if (year > CURRENT_YEAR - minAge) { 
            return " under 16";
        }
        if (year < CURRENT_YEAR - maxAge) { 
            return " over 24";
        }
    }
  
    return " not a valid calendar date!";
}

//check if it's a leapyear
private boolean isLeapYear(int year) {
    if (year % QUADRENNIAL == 0) {
        if (year % CENTENNIAL == 0) {
            return year % QUATERCENTENNIAL == 0;
        } else {
            return true;
        }
    } else {
        return false;
    }
}



//getters year
public int getYear() {
    return year;
}



@Override
//compareTo method, used to sort the date
public int compareTo(Date other) {
 
    if (this.year < other.year) {
     return -1;
 } else if (this.year > other.year) {
     return 1;
 } else {
     if (this.month < other.month) {
         return -1;
    } else if (this.month > other.month) {
         return 1;
    } else {
         if (this.day < other.day) {
             return -1;
         } else if (this.day > other.day) {
             return 1;
         } else {
             return 0;
         }
    }
 }


}

//toString method, used to print the date
@Override
    public String toString() {
        return String.format("%d/%d/%d",  month, day, year);
    }



//equals method, used to compare the date
@Override
public boolean equals(Object other) {
    if (other instanceof Date) {
        Date otherDate = (Date) other;
        return year == otherDate.year && month == otherDate.month && day == otherDate.day;
    }
    return false;

}




//test cases for the isValid() method
public static void main(String[] args) {
  

    testDaysInFeb_NonLeap();
    testDaysInFeb_Leap();
    testMonth_OutOfRange();
    testDay_OutOfRange();
    testYear_Before1900();
    

  
}


//test case 1
private static void testDaysInFeb_NonLeap(){
 
    Date date = new Date("2/29/2011");
    
    boolean expectedOutput = false;
 

    System.out.println("**Test case 1: 29 of days in Feb. in a non-leap year is 28");
    

}




//test case 2
private static void testDaysInFeb_Leap(){

    Date date = new Date("2/29/2012");
    
    boolean expectedOutput = true;
    //boolean actualOutput = date.isValid();

    System.out.println("**Test case 2: 29 of days in Feb. in a leap year is 29");
    //testResult(date, expectedOutput, actualOutput);

}


//test case 3
private static void testMonth_OutOfRange(){

    Date date = new Date("13/1/2012");
    
    boolean expectedOutput = false;
    //boolean actualOutput = date.isValid();

    System.out.println("**Test case 3: month is out of range");
    //testResult(date, expectedOutput, actualOutput);

}


//test case 4
private static void testDay_OutOfRange(){

    Date date = new Date("12/32/2012");
    
    boolean expectedOutput = false;
    //boolean actualOutput = date.isValid();

    System.out.println("**Test case 4: day is out of range");
    //testResult(date, expectedOutput, actualOutput);

}


//test case 5
private static void testYear_Before1900(){

    Date date = new Date("11/21/800");
    
    boolean expectedOutput = false;
    //boolean actualOutput = date.isValid();

    System.out.println("**Test case 5: year is before 1900");
    //testResult(date, expectedOutput, actualOutput);

}


//helper method for test cases
private static void testResult(Date date, boolean expectedOutput, boolean actualOutput){


    if (expectedOutput == actualOutput) {
        System.out.println("Test passed");
    } else {
        System.out.println("Test failed");
    }
    System.out.println();

}






}