/**
 * This class represents a profile of a person. A profile contains the first name, last name and date of birth of a person.
 * 
 * @author Jizhou Yang
 *  
 */
package com.example.project3;

public class Profile implements Comparable<Profile>{
    private String fname;
    private String lname;
    private Date dob;

    public Profile(String fname, String lname, Date dob) {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }

    //get first name
    public String getFname() {
        return fname;
    }
    
    //get last name
    public String getLname() {
        return lname;
    }


    //get date of birth
    public Date getDob() {
        return dob;
    }

    //compare two profile
    @Override
    public int compareTo(Profile otherProfile) {
        int lastNameComparison = this.lname.compareTo(otherProfile.lname);
        if (lastNameComparison != 0) {
            return lastNameComparison;
        }
        return this.fname.compareTo(otherProfile.fname);
    }

    //override equals method
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Profile) {
            Profile otherProfile = (Profile) obj;
            return fname.equals(otherProfile.fname) &&
                   lname.equals(otherProfile.lname) &&
                   dob.equals(otherProfile.dob);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return fname.hashCode() + lname.hashCode() + dob.hashCode();
    }
    
    @Override
    public String toString() {
    
        
        return fname + " " + lname + " " + dob.toString();
    }


    
}


