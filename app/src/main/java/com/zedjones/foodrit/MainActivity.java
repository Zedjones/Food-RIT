package com.zedjones.foodrit;

import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity
        implements ConnectionCallbacks, OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener {

    public static final String EXTRA_MESSAGE = "com.zedjones.foodrit";
    private GoogleApiClient mapsClient;
    private Location mLastLocation;
    private LocationRequest mLocationRequest;
    private HashMap<Coordinate, Float> distancesTo;
    private ArrayList<DiningLocation> locations;
    private FoodParser parser;
    private String test = null;
    private boolean firstLaunch = true;
    final static int REQUEST_LOCATION_TIME = 199;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("App Status", "created");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //create a mapClient to use the Play Services API for Location
        if(mapsClient == null){
            mapsClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            Log.d("GoogleApiClient", "created");
        }

        if(mLocationRequest == null){
            mLocationRequest = LocationRequest.create()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(10*1000)
                    .setFastestInterval(1000);
        }

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi
                .checkLocationSettings(mapsClient, builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch(status.getStatusCode()){
                    case LocationSettingsStatusCodes.SUCCESS:
                        break;

                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try{
                            status.startResolutionForResult(
                                    MainActivity.this,
                                    REQUEST_LOCATION_TIME);
                        }
                        catch(IntentSender.SendIntentException sie){

                        }
                        break;

                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }

            }
        });

        distancesTo = new HashMap<>();
        locations = new ArrayList<>();
        if(firstLaunch){
            parser = new FoodParser(locations,
                    "https://www.rit.edu/fa/diningservices/hours-and-locations");
            parser.start();
            try{
                parser.join();
            }
            catch(InterruptedException ie){
                Log.d("Exception", ie.getMessage());
            }
            Collections.sort(locations, new LocationComparator());
            for(DiningLocation location : locations){
                System.out.println(location);
            }
            Thread getDistanceThread = new Thread(new Runnable() {
            @Override
                public void run(){
                    try{
                        while(mLastLocation == null){
                            Thread.sleep(500);
                        }
                        for(DiningLocation location : locations){
                            location.setDistance(getRoadDistanceString(new Coordinate(mLastLocation.getLatitude(),
                                    mLastLocation.getLongitude()), location.getLocation()));
                        }
                    }
                    catch(InterruptedException ie){
                        Log.d("Interrupted Exception", ie.getMessage());
                    }

            }
            });
            getDistanceThread.start();
        }
    }

    //connects to the Google Play API and calls the onStart of Activity
    public void onStart(){
        Log.d("App Status", "started");
        mapsClient.connect();
        Log.d("GoogleApiClient", "connected");
        super.onStart();
    }

    //disconnects from the Google Play API and calls the onStop of Activity
    protected void onStop(){
        mapsClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result){
        Log.d("Connection failed:", result.getErrorMessage());
    }

    @Override
    public void onConnected(Bundle connectionHint){
        try{
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mapsClient);
            Log.d("Location requests", "first try");
        }
        catch(SecurityException sec){
            Log.d("Location requests", "security exception");
            test = "Security Exception";
        }
        if(mLastLocation == null){
            try{
                Log.d("Location requests", "started");
                LocationServices.FusedLocationApi.
                        requestLocationUpdates(mapsClient, mLocationRequest, this);
            }
            catch(SecurityException sec){

            }
        }
    }

    @Override
    public void onLocationChanged(Location location){
        mLastLocation = location;
        Log.d("Location changed", location.toString());
        LocationServices.FusedLocationApi.removeLocationUpdates(mapsClient, this);
    }

    @Override
    public void onConnectionSuspended(int reason){

    }

    public void sendMessage(View view){
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText2);
        String message = editText.getText().toString();
        float[] results = new float[5];
        if(mLastLocation != null){
            Location.distanceBetween(mLastLocation.getLatitude(),
                    mLastLocation.getLongitude(), 43.086290, -77.670557, results);
            intent.putExtra(EXTRA_MESSAGE, getRoadDistance(new Coordinate(mLastLocation.getLatitude(),
                    mLastLocation.getLongitude()), new Coordinate(43.086290, -77.670557)));
        }
        else{
            Log.d("mLastLocation", "null");
            if(test == null){
                intent.putExtra(EXTRA_MESSAGE, message);
            }
            else{
                intent.putExtra(EXTRA_MESSAGE, test);
            }
        }
        startActivity(intent);
    }

    public String getRoadDistance(Coordinate start, Coordinate dest){
        MapsThread mapsThread = new MapsThread(start, dest);
        mapsThread.start();
        try{
            mapsThread.join();
        }
        catch(InterruptedException ie){
            Log.d("InterruptedException", ie.getMessage());
        }
        return mapsThread.getDistance();
    }

    public String getRoadDistanceString(Coordinate start, String dest){
        MapsThreadString mapsThread = new MapsThreadString(start, dest);
        mapsThread.start();
        try{
            mapsThread.join();
        }
        catch(InterruptedException ie){
            Log.d("InterruptedException", ie.getMessage());
        }
        return mapsThread.getDistance();
    }
}
