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
import android.widget.Spinner;
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

        //We will concatenate contents of profileNameTextEdit, MPGTextEdit, and fuelSpinner with a ",' delimiter
        EditText myProfileEdit = (EditText)findViewById(R.id.profileNameTextEdit);
        EditText myMPGEdit = (EditText)findViewById(R.id.MPGTextEdit);
        //Spinner fuelSpinner = (Spinner) findViewById(R.id.fuelSpinner); //Will implement fuel in the future

        //Prevent user from entering an empty field
        if (myProfileEdit.getText().toString().length() == 0 || myMPGEdit.getText().toString().length() == 0) {

            Toast.makeText(getApplicationContext(), "You left a field empty!", Toast.LENGTH_LONG).show();

        }
        //If no fields empty, save to file
        else {

            //Grab Profile Name and MPG from EditTexts in GUI
            profileInfo = ((myProfileEdit.getText().toString()) + "," + (myMPGEdit.getText().toString()) + "\n");

            //Open file to write to
            FileOutputStream outputToFile = null;
            try {
                //MODE_APPEND will add to end of file, instead of overwriting the file
                outputToFile = openFileOutput(profileFileName, Context.MODE_APPEND);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            //Write to file
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

            //******UNCOMMENT FOR TEST TOAST**********
            //testToast();
            //****************************************

            //Set view back to activity_main, then close this activity
            setContentView(R.layout.activity_main);
            this.finish();

        }

    }

    //Test function to read from file and display contents in a Toast
    private void testToast(){

        String testReadText = "";

        //Read file to be displayed with a Toast
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

        //Save contents to String
        testReadText += new String(input);

        //Close the file
        try {
            readFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Toast message of file contents saved to testReadText
        //*******************************************************************************
        Toast.makeText(getApplicationContext(), testReadText, Toast.LENGTH_LONG).show();
        //*******************************************************************************

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
