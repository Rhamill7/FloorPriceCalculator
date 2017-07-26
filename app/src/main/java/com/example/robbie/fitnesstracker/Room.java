package com.example.robbie.fitnesstracker;

/**
 * Created by Robbie on 08/02/2017.
 */

public class Room {
    private String name;
    private double length;
    private double breadth;
    private String date;
    private boolean active = false;
    private String units;

    public Room(String name, double length, double breadth, String date, int active){
        this.name = name;
        this.length = length;
        this.breadth= breadth;
        this.date = date;
       // this.units = units;

        if (active == 1){
            this.active = true;
        }
        else {
            this.active = false;
        }

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

    public String getDate() {
        return date;
    }

    public boolean isActive(){
        return active;
    }

    public void setActive(){
        this.active = true;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }
}
