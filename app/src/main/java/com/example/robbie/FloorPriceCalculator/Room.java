package com.example.robbie.FloorPriceCalculator;

/**
 * Created by Robbie on 08/02/2017.
 */

public class Room {
    private String name;
    private double length;
    private double breadth;

    public Room(String name, double length, double breadth){
        this.name = name;
        this.length = length;
        this.breadth= breadth;
    }

    public String getName(){
        return name;
    }

    public double getLength(){
        return length;
    }

    public double getBreadth(){
        return breadth;
    }

}
