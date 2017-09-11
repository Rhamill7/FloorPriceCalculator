package com.example.robbie.FloorPriceCalculator;

/**
 * Created by Robbie on 11/09/2017.
 */

public class Customer {


        private String name;
        private String address;
        private String number;

        public Customer(String name, String address, String number){
            this.name = name;
            this.address = address;
            this.number= number;
        }

        public String getName(){
            return name;
        }

        public String getAddress(){
            return address;
        }

        public String getNumber(){
            return number;
        }


}
