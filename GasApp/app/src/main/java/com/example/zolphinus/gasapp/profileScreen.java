package com.example.zolphinus.gasapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class profileScreen extends Activity {

    //Variables
    private static String profileFileName = "Profiles.txt"; //File name
    String profileInfo; //Variable for combining profile info to be written to file

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen);
        //Intent intent = getIntent();
    }

    public void saveProfile(View v){
        //startActivity(new Intent(getApplicationContext(), MainActivity.class));

        //For testing with Toast
        //String testReadText = "";

        //We will concatenate contents of profileNameTextEdit and MPGTextEdit with a ",' delimiter
        EditText myProfileEdit = (EditText)findViewById(R.id.profileNameTextEdit);
        EditText myMPGEdit = (EditText)findViewById(R.id.MPGTextEdit);
        if (myProfileEdit.getText().toString().length() == 0 || myMPGEdit.getText().toString().length() == 0) {

            Toast.makeText(getApplicationContext(), "You left a field empty!", Toast.LENGTH_LONG).show();

        }
        else {

            //Grab Profile Name and MPG from EditTexts in GUI
            profileInfo = (myProfileEdit.getText().toString()) + "," + (myMPGEdit.getText().toString() + "\n");

            FileOutputStream outputToFile = null;
            try {
                //MODE_APPEND will add to end of file, instead of overwriting the file
                outputToFile = openFileOutput(profileFileName, Context.MODE_APPEND);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if (outputToFile != null) {
                try {
                    outputToFile.write(profileInfo.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //Close the file
            try {
                outputToFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


            FileInputStream readFile = null;
            try {
                readFile = openFileInput(profileFileName);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            byte[] input = new byte[0];
            try {
                input = new byte[readFile.available()];
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                while (readFile.read(input) != -1) {
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            //For testing with Toast
            //testReadText += new String(input);

            //Close the file
            try {
                readFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


            //For testing: toast message of file contents
            //*******************************************************************************
            //Toast.makeText(getApplicationContext(), testReadText, Toast.LENGTH_LONG).show();
            //*******************************************************************************

            //Set view back to activity_main, then close this activity
            setContentView(R.layout.activity_main);
            this.finish();

        }

        //setContentView(R.layout.activity_main);
        //this.finish();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
