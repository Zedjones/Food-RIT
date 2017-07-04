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
                        int index = 0;
                        for(Element locElem : locationTimes){
                            Elements locationTimesOpen = locElem.getElementsByClass("menu-day-closed");
                            Elements locationTimesClosed = locElem.getElementsByClass("menu-day-open");
                            if(!locationTimesOpen.isEmpty()){
                                if(!locationTimesClosed.isEmpty()){
                                    for(int loc = 0; loc < locationTimesClosed.size(); loc++){
                                        if(loc == 0){
                                            locationTimesOpen.add(1, locationTimesClosed.get(loc));
                                        }
                                        else{
                                            int insertAt = locationTimesOpen.indexOf(locationTimesClosed.get(loc-1));
                                            locationTimesOpen.add(insertAt, locationTimesClosed.get(loc));
                                        }
                                    }
                                }
                                index = locationTimesOpen.size()-1;
                                for(int i = 0; i < locationTimesOpen.size(); i++){
                                    Element current = locationTimesOpen.get(i);
                                    String open = current.text();
                                    if(open.contains("Open") || open.contains("open")){
                                        isOpen = true;
                                        index = i;
                                        break;
                                    }
                                }
                            }
                            Elements locationTimesTime = locElem.getElementsByClass("menu-day-hours");
                            if(!locationTimesTime.isEmpty()){
                                Element current = locationTimesTime.get(index);
                                times = current.text();
                                String[] temp = times.split(" ", 2);
                                times = temp[1];
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
                        int index = 0;
                        for(Element locElem : locationTimes){
                            Elements locationTimesOpen = locElem.getElementsByClass("menu-day-closed");
                            Elements locationTimesClosed = locElem.getElementsByClass("menu-day-open");
                            if(!locationTimesOpen.isEmpty()){
                                if(!locationTimesClosed.isEmpty()){
                                    for(int loc = 0; loc < locationTimesClosed.size(); loc++){
                                        if(loc == 0){
                                            locationTimesOpen.add(1, locationTimesClosed.get(loc));
                                        }
                                        else{
                                            int insertAt = locationTimesOpen.indexOf(locationTimesClosed.get(loc-1));
                                            locationTimesOpen.add(insertAt, locationTimesClosed.get(loc));
                                        }
                                    }
                                }
                                index = locationTimesOpen.size()-1;
                                for(int i = 0; i < locationTimesOpen.size(); i++){
                                    Element current = locationTimesOpen.get(i);
                                    String open = current.text();
                                    if(open.contains("Open") || open.contains("open")){
                                        isOpen = true;
                                        index = i;
                                        break;
                                    }
                                }
                            }
                            Elements locationTimesTime = locElem.getElementsByClass("menu-day-hours");
                            if(!locationTimesTime.isEmpty()){
                                Element current = locationTimesTime.get(index);
                                times = current.text();
                                String[] temp = times.split(" ", 2);
                                times = temp[1];
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
                        int index = 0;
                        for(Element locElem : locationTimes){
                            Elements locationTimesOpen = locElem.getElementsByClass("menu-day-closed");
                            Elements locationTimesClosed = locElem.getElementsByClass("menu-day-open");
                            if(!locationTimesOpen.isEmpty()){
                                if(!locationTimesClosed.isEmpty()){
                                    for(int loc = 0; loc < locationTimesClosed.size(); loc++){
                                        if(loc == 0){
                                            locationTimesOpen.add(1, locationTimesClosed.get(loc));
                                        }
                                        else{
                                            int insertAt = locationTimesOpen.indexOf(locationTimesClosed.get(loc-1));
                                            locationTimesOpen.add(insertAt, locationTimesClosed.get(loc));
                                        }
                                    }
                                }
                                index = locationTimesOpen.size()-1;
                                for(int i = 0; i < locationTimesOpen.size(); i++){
                                    Element current = locationTimesOpen.get(i);
                                    String open = current.text();
                                    if(open.contains("Open") || open.contains("open")){
                                        isOpen = true;
                                        index = i;
                                        break;
                                    }
                                }
                            }
                            Elements locationTimesTime = locElem.getElementsByClass("menu-day-hours");
                            if(!locationTimesTime.isEmpty()){
                                Element current = locationTimesTime.get(index);
                                times = current.text();
                                String[] temp = times.split(" ", 2);
                                times = temp[1];
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
                        int index = 0;
                        for(Element locElem : locationTimes){
                            Elements locationTimesOpen = locElem.getElementsByClass("menu-day-closed");
                            Elements locationTimesClosed = locElem.getElementsByClass("menu-day-open");
                            if(!locationTimesOpen.isEmpty()){
                                if(!locationTimesClosed.isEmpty()){
                                    for(int loc = 0; loc < locationTimesClosed.size(); loc++){
                                        if(loc == 0){
                                            locationTimesOpen.add(1, locationTimesClosed.get(loc));
                                        }
                                        else{
                                            int insertAt = locationTimesOpen.indexOf(locationTimesClosed.get(loc-1));
                                            locationTimesOpen.add(insertAt, locationTimesClosed.get(loc));
                                        }
                                    }
                                }
                                index = locationTimesOpen.size()-1;
                                for(int i = 0; i < locationTimesOpen.size(); i++){
                                    Element current = locationTimesOpen.get(i);
                                    String open = current.text();
                                    if(open.contains("Open") || open.contains("open")){
                                        isOpen = true;
                                        index = i;
                                        break;
                                    }
                                }
                            }
                            Elements locationTimesTime = locElem.getElementsByClass("menu-day-hours");
                            if(!locationTimesTime.isEmpty()){
                                Element current = locationTimesTime.get(index);
                                times = current.text();
                                String[] temp = times.split(" ", 2);
                                times = temp[1];
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
                Log.d("Exception caught", e.getMessage() +
                        " Line " + e.getStackTrace()[1].getLineNumber());
            }
        }
        catch(Exception e){
            Log.d("Exception caught", e.getMessage());
        }
    }
}
