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
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity
        implements ConnectionCallbacks, OnConnectionFailedListener{

    public static final String EXTRA_MESSAGE = "com.zedjones.foodrit";
    private GoogleApiClient mapsClient;
    private Location mLastLocation;


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

    public void onConnected(Bundle connectionHint){
        try{
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mapsClient);
        }
        catch(SecurityException sec){

        }
        if(mLastLocation != null){

        }
    }

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
            intent.putExtra(EXTRA_MESSAGE, message);
        }
        startActivity(intent);
    }
}
