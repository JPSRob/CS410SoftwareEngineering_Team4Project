package com.example.zolphinus.gasapp;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Justin Roberts on 3/1/2015.
 * References: https://developer.android.com/training/location/retrieve-current.html
 */
public class GetLocation extends ActionBarActivity implements GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener {

    //Variables
    protected GoogleApiClient mGoogleApiClient; //Our instance of the GoogleApiClient
    protected Location mLastLocation; //Location for getting location from mGoogleApiClient
    protected TextView mLatitudeText; //TextView to hold latitude we get from mLastLocation
    protected TextView mLongitudeText; //TextView to hold longitude we get from mLastLocation

    /**
     * Constructor creates an instance of the Google Play services API client,
     * thereby building the LocationServices API to be used in the class.
     */
    public GetLocation(){
        protected synchronized void buildGoogleApiClient() {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    //onConnected callback provided by our GoogleApiClient, which is called when client is ready.
    @Override
    public void onConnected(Bundle connectionHint) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient); //Get location
        if (mLastLocation != null) {
            mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude())); //Set latitude
            mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude())); //Set longitude
        }
    }

    @Override
    public void onDisconnected() {
        
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    //Getters for latitude and longitude
    public String getLatitude() {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient); //Get location
        if (mLastLocation != null) {
            mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude())); //Set latitude
        }
            return mLatitudeText.toString();
        }

    public String getLongitude() {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient); //Get location
        if (mLastLocation != null) {
            mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude())); //Set longitude
        }
            return mLongitudeText.toString();
        }
    }

