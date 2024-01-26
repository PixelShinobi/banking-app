/**
 * This class is used to create a campus object. It has two attributes: Campusname and Campuscode.
 * 
 * @author Jizhou Yang
 *  
 */
package com.example.project3;

public class Campus {
        private String name;
        private String code;

        public Campus(String code) {
            
            this.code = code;

          
            if (code.equals("0")) {
                this.name = "NEW_BRUNSWICK";
            }
          
            else if (code.equals("1")) {
                this.name = "NEWARK";
            }
           
            else if (code.equals("2")) {
                this.name = "CAMDEN";
            }
        }

       //get name of the account
        public String getName() {
            return name;
        }
        //set name of the account
        public void setName(String name) {
            this.name = name;
        }
        //get code of the campus
        public String getCode() {
            return code;
        }
        //set code of the campus
        public void setCode(String code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return name;
        }


        //override equals method
        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Campus) {
                Campus otherCampus = (Campus) obj;
                return name.equals(otherCampus.name) &&
                        code.equals(otherCampus.code);
            }
            return false;
        }

        
    }