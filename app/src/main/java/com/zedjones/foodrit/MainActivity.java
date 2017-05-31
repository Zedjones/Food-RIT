package com.zedjones.foodrit;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;


public class MainActivity extends AppCompatActivity
        implements ConnectionCallbacks, OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener {

    public static final String EXTRA_MESSAGE = "com.zedjones.foodrit";
    private GoogleApiClient mapsClient;
    private Location mLastLocation;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mSettingsRequest;
    private String test = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //create a mapClient to use the Play Services API for Location
        if(mapsClient == null){
            mapsClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

        }

        if(mLocationRequest == null){
            mLocationRequest = LocationRequest.create()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(10*1000)
                    .setFastestInterval(1000);
        }

        if(mSettingsRequest == null){

        }

       // Location currentLocation =
    }

    //connects to the Google Play API and calls the onStart of Activity
    public void onStart(){
        mapsClient.connect();
        super.onStart();
    }

    //disconnects from the Google Play API and calls the onStop of Activity
    protected void onStop(){
        mapsClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result){

    }

    //TODO figure out why I still can't get location
    @Override
    public void onConnected(Bundle connectionHint){
        try{
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mapsClient);
        }
        catch(SecurityException sec){
            test = "Security Exception";
        }
        if(mLastLocation == null){
            try{
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
    }

    @Override
    public void onConnectionSuspended(int reason){

    }

    public void sendMessage(View view){
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText2);
        String message = editText.getText().toString();
        System.out.println("Message");
        if(mLastLocation != null){
            intent.putExtra(EXTRA_MESSAGE, String.valueOf(mLastLocation.getLatitude()));
        }
        else{
            if(test == null){
                intent.putExtra(EXTRA_MESSAGE, message);
            }
            else{
                intent.putExtra(EXTRA_MESSAGE, test);
            }
        }
        startActivity(intent);
    }
}
