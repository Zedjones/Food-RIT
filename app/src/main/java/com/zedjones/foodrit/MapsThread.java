package com.zedjones.foodrit;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Created by zedjones on 6/2/17.
 */

public class MapsThread extends Thread{
    private String distance;
    private Coordinate start;
    private Coordinate dest;

    public MapsThread(Coordinate start, Coordinate dest){
        this.start = start;
        this.dest = dest;
    }
    @Override
    public void run() {
        String response = new String();
        try{
            URL url = new URL("http://maps.googleapis.com/maps/api/directions/json?origin="
                    + start.getLatitude() + "," + start.getLongitude() + "&destination=" +
                    dest.getLatitude() + "," + dest.getLongitude() +
                    "&sensor=false&units=metric&mode=driving");

            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(),
                    "UTF-8"));
            for(String line; (line = reader.readLine()) != null; response += line);
            //Log.d("Response", response);

            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("routes");
            JSONObject routes = jsonArray.getJSONObject(0);
            JSONArray legs = routes.getJSONArray("legs");
            JSONObject steps = legs.getJSONObject(0);
            JSONObject jsonDistance = steps.getJSONObject("distance");
            distance = jsonDistance.getString("text");
        }
        catch (Exception e){
            Log.d("Exception", e.getMessage());
        }
    }

    public String getDistance(){
        return distance;
    }
}
