package com.example.zolphinus.gasapp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by zolphinus on 4/7/2015.
 */
public class Team4SplashScreen extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.team4_splash_screen);
        Handler h = new Handler();

        h.postDelayed(new Runnable() {
            public void run() {

                Team4SplashScreen.this.finish();
            }
        }, 3000);


    }
}
