/**
 * This is a collage checking account class. It is a subclass of Checking.
 * It has an instance variable campus.
 * 
 * @author Jizhou Yang
 *  
 */

package com.example.project3;

public class CollegeChecking extends Checking {

    private Campus campus;


    //mew constructor
    public CollegeChecking(Profile holder, double directDeposit, Campus campus) {
        super(holder, directDeposit);
        this.campus = campus;
    }
    
    public CollegeChecking(Profile holder) {
        super(holder);
    }

    @Override
    public double monthlyInterest() {
  
        return 0.01 * balance / 12;
    }


    @Override
    public double monthlyFee() {
       
        return 0;
    }


   //get campus when needed
    public Campus getCampus() {
        return campus;
    }

    //set the campus of account
    public void setCampus(Campus campus) {
        this.campus = campus;
    }

    @Override
    public String toString() {
        return getHolder() +" (CC)";
    }

   
    
}
