package com.example.zolphinus.gasapp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;


/**
 * Created by zolphinus on 4/6/2015.
 */
public class SplashScreen extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        Handler h = new Handler();

        h.postDelayed(new Runnable() {
            public void run() {
                SplashScreen.this.finish();
            }
        }, 2500);


    }




}
