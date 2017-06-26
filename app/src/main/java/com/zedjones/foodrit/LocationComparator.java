package com.zedjones.foodrit;

import java.util.Comparator;

/**
 * Created by zedjones on 6/20/17.
 */

public class LocationComparator implements Comparator<DiningLocation> {

    public int compare(DiningLocation d1, DiningLocation d2){
        if(d1.isOpen() && !d2.isOpen()){
            return -1;
        }
        else if(d2.isOpen() && !d1.isOpen()){
            return 1;
        }
        else{
            return d1.getName().compareTo(d2.getName());
        }
    }
}
