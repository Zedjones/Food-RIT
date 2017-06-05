package com.zedjones.foodrit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by zedjones on 6/5/17.
 */

public class FoodParser extends Thread{
    private ArrayList<DiningLocation> locations;
    private String html;

    public FoodParser(ArrayList<DiningLocation> locations, String html){
        this.locations = locations;
        this.html = html;
    }

    public void run(){
        try{
            Element body = Jsoup.connect(html).get().body();
            try{
                for(Element elem : body.getElementsByClass("odd formated-table views-row-first")){
                    String name;
                    String location;
                    String times;
                    boolean isOpen;
                    Elements locationName = elem.getElementsByAttributeValue("data-th", "Name");
                    if(!locationName.isEmpty()){
                        name = locationName.first().text();
                    }
                    Elements locationLoc = elem.getElementsByAttributeValue("data-th", "Located");
                    if(!locationLoc.isEmpty()){
                        location = locationLoc.first().text();
                    }
                    Elements locationTimes = elem.getElementsByAttributeValue("data-th", "Hours");
                    if(!locationLoc.isEmpty()){
                        for(Element locElem : locationTimes){

                        }
                    }
                }
            }
            catch(Exception e){

            }
        }
        catch(Exception e){

        }
    }
}
