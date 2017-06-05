package com.zedjones.foodrit;

/**
 * Created by zedjones on 6/5/17.
 */

public class DiningLocation {
    private String name;
    private String times;
    private String location;
    private boolean isOpen;

    public DiningLocation(String name, String location, String times, boolean isOpen){
        this.name = name;
        this.location = location;
        this.times = times;
        this.isOpen = isOpen;
    }

    public String getName(){
        return name;
    }

    public String getTimes(){
        return times;
    }

    public String getLocation(){
        return location;
    }

    public boolean isOpen(){
        return isOpen;
    }

    public String toString(){
        String returned = name + ", " + location + ", " + times + ", ";
        if(isOpen){
            returned += "Open";
        }
        else{
            returned += "Closed";
        }
        return returned;

    }
}
