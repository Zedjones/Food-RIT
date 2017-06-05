package com.zedjones.foodrit;

import android.util.Log;

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
                for(Element elem : body.getElementsByClass("odd formatted-table views-row-first")){
                    Log.d("Parsing HTML", "starting");
                    String name = null;
                    String location = null;
                    String times = null;
                    boolean isOpen = false;
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
                            Elements locationTimesTime = locElem.getElementsByClass("menu-day-hours");
                            if(!locationTimesTime.isEmpty()){
                                times = locationTimesTime.first().text();
                                String[] temp = times.split(" ", 2);
                                times = temp[1];
                            }
                            Elements locationTimesOpen = locElem.getElementsByClass("menu-day-closed");
                            if(!locationTimesOpen.isEmpty()){
                                String open = locationTimesOpen.first().text();
                                if(open.contains("Open") || open.contains("open")){
                                    isOpen = true;
                                }
                                else{
                                    isOpen = false;
                                }
                            }
                        }
                    }
                    Log.d("Location name", name);
                    Log.d("Location located", location);
                    Log.d("Location times", times);
                    Log.d("Location open", Boolean.toString(isOpen));
                    DiningLocation tempLoc = new DiningLocation(name, location, times, isOpen);
                    locations.add(tempLoc);
                }
                for(Element elem : body.getElementsByClass("even formatted-table")){
                    Log.d("Parsing HTML", "starting");
                    String name = null;
                    String location = null;
                    String times = null;
                    boolean isOpen = false;
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
                            Elements locationTimesTime = locElem.getElementsByClass("menu-day-hours");
                            if(!locationTimesTime.isEmpty()){
                                times = locationTimesTime.first().text();
                                String[] temp = times.split(" ", 2);
                                times = temp[1];
                            }
                            Elements locationTimesOpen = locElem.getElementsByClass("menu-day-closed");
                            if(!locationTimesOpen.isEmpty()){
                                String open = locationTimesOpen.first().text();
                                if(open.contains("Open") || open.contains("open")){
                                    isOpen = true;
                                }
                                else{
                                    isOpen = false;
                                }
                            }
                        }
                    }
                    Log.d("Location name", name);
                    Log.d("Location located", location);
                    if(times != null){
                        Log.d("Location times", times);
                    }
                    Log.d("Location open", Boolean.toString(isOpen));
                    DiningLocation tempLoc = new DiningLocation(name, location, times, isOpen);
                    locations.add(tempLoc);
                }
                for(Element elem : body.getElementsByClass("odd formatted-table")){
                    Log.d("Parsing HTML", "starting");
                    String name = null;
                    String location = null;
                    String times = null;
                    boolean isOpen = false;
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
                            Elements locationTimesTime = locElem.getElementsByClass("menu-day-hours");
                            if(!locationTimesTime.isEmpty()){
                                times = locationTimesTime.first().text();
                                String[] temp = times.split(" ", 2);
                                times = temp[1];
                            }
                            Elements locationTimesOpen = locElem.getElementsByClass("menu-day-closed");
                            if(!locationTimesOpen.isEmpty()){
                                String open = locationTimesOpen.first().text();
                                if(open.contains("Open") || open.contains("open")){
                                    isOpen = true;
                                }
                                else{
                                    isOpen = false;
                                }
                            }
                        }
                    }
                    Log.d("Location name", name);
                    Log.d("Location located", location);
                    if(times != null){
                        Log.d("Location times", times);
                    }
                    Log.d("Location open", Boolean.toString(isOpen));
                    DiningLocation tempLoc = new DiningLocation(name, location, times, isOpen);
                    locations.add(tempLoc);
                }
                for(Element elem : body.getElementsByClass("odd formatted-table views-row-last")){
                    Log.d("Parsing HTML", "starting");
                    String name = null;
                    String location = null;
                    String times = null;
                    boolean isOpen = false;
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
                            Elements locationTimesTime = locElem.getElementsByClass("menu-day-hours");
                            if(!locationTimesTime.isEmpty()){
                                times = locationTimesTime.first().text();
                                String[] temp = times.split(" ", 2);
                                times = temp[1];
                            }
                            Elements locationTimesOpen = locElem.getElementsByClass("menu-day-closed");
                            if(!locationTimesOpen.isEmpty()){
                                String open = locationTimesOpen.first().text();
                                if(open.contains("Open") || open.contains("open")){
                                    isOpen = true;
                                }
                                else{
                                    isOpen = false;
                                }
                            }
                        }
                    }
                    Log.d("Location name", name);
                    Log.d("Location located", location);
                    if(times != null){
                        Log.d("Location times", times);
                    }
                    Log.d("Location open", Boolean.toString(isOpen));
                    DiningLocation tempLoc = new DiningLocation(name, location, times, isOpen);
                    locations.add(tempLoc);
                }
            }
            catch(Exception e){
                Log.d("Exception caught", e.getMessage());
            }
        }
        catch(Exception e){
            Log.d("Exception caught", e.getMessage());
        }
    }
}
