package com.example.zolphinus.gasapp;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;


public class loadProfile extends ListActivity {

    //Variables & declarations
    private static String profileFileName = "Profiles.txt"; //Internal file name
    FileInputStream readInFile = null;

    //ArrayList<HashMap to hold profile entries for ListView GUI
    ArrayList<HashMap<String, String>> profileList = new ArrayList<HashMap<String, String>>();
    //Const String keys for HashMap
    private static final String TAG_NAME = "name"; //Name of profile
    private static final String TAG_MPG = "mpg"; //MPG value
    private static final String TAG_FUEL = "fuel"; //Fuel type

    Button Load; //the button that loads and exits activity
    int information_Search = 70;
    //EditText profileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_profile);
        Load = (Button) findViewById(R.id.Load);

        //Call function to read internal file and display to ListView
        updateListView();
    }

    public void updateListView(){

        //Variables & declarations
        int intToCast = 0;
        int counter = 0;
        int counterDelimiter = 0;
        char tempChar;
        String tempString = null;
        Boolean endOfFile = false;
        Boolean afterComma = false;
        String name = "", mpg = "", fuel = ""; //HashMap id values
        HashMap<String, String> profileHash = new HashMap<String, String>();


        //butteredToast will read "poop" if the input file is null
        String poopString = "poop";

        //BufferedReader(InputStreamReader(InputStream)) method of opening Profiles.txt file
        InputStream inStream = null;
        InputStreamReader inReader = null;
        BufferedReader buffReader = null;

        //Open file
        try {
            inStream = openFileInput(profileFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //While the file is not empty/missing, initialize InputStreamReader with file
        if(inStream != null) {
            inReader = new InputStreamReader(inStream);
            poopString = "not null poop";
        }
        //Initialize BufferedReader with our InputStreamReader - this interface will read from the file
        buffReader = new BufferedReader(inReader);

        //File parsing logic
        try {
            //While loop that will read each line from file
            while((tempString = buffReader.readLine()) != null){

                    //While loop that will parse the current line
                    while (counter < tempString.length()) {

                        //Grab the current substring character from the line
                        tempChar = tempString.charAt(counter);

                        //If character is a comma, ignore this delimiter and change afterComma to true to indicate we're now reading into mpg field
                        if (tempChar == ',') {
                            afterComma = true;
                            counter++;
                        }
                        else {
                            //if we have not hit the comma delimiter in the line, read into name field
                            if (!afterComma) {
                                name = name + tempChar;
                                counter++;
                            }
                            //if we have already hit the comma delimiter, read into mpg field
                            else if (afterComma) {
                                mpg = mpg + tempChar;
                                counter++;
                            }
                        }
                    }

                    //For testing with butteredToast
                    poopString = name + mpg;

                    //Add the name and mpg Strings to the HashMap
                    profileHash.put(TAG_NAME, name);
                    profileHash.put(TAG_MPG, mpg);
                    //profileHash.put(TAG_FUEL, fuel); //Will implement fuel type later

                    //Add HashMap to ArrayList
                    profileList.add(profileHash);

                    //Clear counter for next line
                    counter = 0;
                    //Clear the name, mpg, and fuel Strings for use in next line
                    name = null;
                    mpg = null;
                    //fuel = null; //Will implement fuel type later

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        //*********FOR TESTING PURPOSES************
        Toast butteredToast = null;
        butteredToast.makeText(getApplicationContext(), poopString, Toast.LENGTH_LONG).show();
        //*****************************************

        //Display profileList into ListView
        ListAdapter adapter = new SimpleAdapter(loadProfile.this, profileList, R.layout.load_layout,
                new String[]{TAG_NAME, TAG_MPG}, new int[]{R.id.nameLayout, R.id.mpgLayout});
        setListAdapter(null); //Clear prior list adapters
        setListAdapter(adapter);

    }

    //Method to delete contents of Profiles.txt
    public void clearFile(){


        
    }
        
    public void loadProfileSelected(View v){
        setContentView(R.layout.activity_main);
        this.finish();
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
