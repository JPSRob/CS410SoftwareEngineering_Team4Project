package com.example.zolphinus.gasapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;


public class loadProfile extends ListActivity {

    //Variables & declarations
    private static String profileFileName = "Profiles.txt"; //Internal file name
    FileInputStream readInFile = null;

    private static String defaultProfileFileName = "Default.txt"; //Internal file to hold default profile
    String defaultName = null;
    String defaultMPG = null;

    //ArrayList<HashMap to hold profile entries for ListView GUI
    ArrayList<HashMap<String, String>> profileList = new ArrayList<HashMap<String, String>>();
    //Const String keys for HashMap
    private static final String TAG_NAME = "name"; //Name of profile
    private static final String TAG_MPG = "mpg"; //MPG value
    private static final String TAG_FUEL = "fuel"; //Fuel type

    Button Load; //the button that loads and exits activity
    int information_Search = 70;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_profile);
        Load = (Button) findViewById(R.id.Load);

        //Call function to read internal file and display to ListView
        updateListView();

        //Code for ListView OnItemClickListener which will listen for user's clicks on the ListView items
        ListView listview = getListView();
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //@Override
            public void onItemClick(AdapterView <?>adapter,View v, int position, long id){

                defaultName = profileList.get(position).get(TAG_NAME);
                defaultMPG = profileList.get(position).get(TAG_MPG);

                //Test for onItemClick functionality - Will display contents of item clicked by user
                Toast garlicToast = null;
                garlicToast.makeText(getApplicationContext(), ("Selected: " + defaultName + ", " + defaultMPG
                + "MPG"), Toast.LENGTH_LONG).show();

            }
        });

    }

    public void updateListView(){

        //Variables & declarations
        int counter = 0;
        char tempChar;
        String tempString = "";
        Boolean afterComma = false;
        String name = "", mpg = "", fuel = ""; //HashMap id values
        HashMap<String, String> profileHash = new HashMap<String, String>();

        //FOR TESTING: butteredToast will read "poop" if the input file is null
        //String poopString = "";

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
            //poopString = "not null poop"; //FOR TESTING: Was used to test w/butteredToast if file is not null
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
                //poopString = poopString + name + mpg + "\n";

                //Add the name and mpg Strings to the HashMap
                profileHash.put(TAG_NAME, name);
                profileHash.put(TAG_MPG, mpg);
                //profileHash.put(TAG_FUEL, fuel); //Will implement fuel type later

                //Add HashMap to ArrayList
                profileList.add(profileHash);

                //Clear HashMap for next entry
                profileHash = new HashMap<String, String>();
                //Clear counter for next line
                counter = 0;
                afterComma = false;
                //Clear the name, mpg, and fuel Strings for use in next line
                name = "";
                mpg = "";
                //fuel = null; //Will implement fuel type later

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //*********FOR TESTING PURPOSES************
        //Toast butteredToast = null;
        //butteredToast.makeText(getApplicationContext(), profileList.toString(), Toast.LENGTH_LONG).show();
        //*****************************************

        //Display profileList into ListView
        ListAdapter adapter = new SimpleAdapter(loadProfile.this, profileList, R.layout.load_layout,
                new String[]{TAG_NAME, TAG_MPG}, new int[]{R.id.nameLayout, R.id.mpgLayout});
        //setListAdapter(null); //Clear prior list adapters
        setListAdapter(adapter);

        //File I/O cleanup
        if (inStream != null) {
            try {
                inStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            inReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            buffReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    //Method to delete contents of Profiles.txt and Default.txt
    public void clearFile(View v){

        //Delete Profiles.txt and Default.txt
        deleteFile(profileFileName);
        deleteFile(defaultProfileFileName);

        //Initialize a new Profiles.txt
        FileOutputStream outputToFile = null;
        try {
            outputToFile = openFileOutput(profileFileName ,Context.MODE_APPEND);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            outputToFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Initialize a new Default.txt
        FileOutputStream outputToDefaultFile = null;
        try {
            outputToDefaultFile = openFileOutput(defaultProfileFileName , Context.MODE_APPEND);
            //FOR TESTING: Displays Toast if initializing file is successful
            //Toast garlicToast = null;
            //garlicToast.makeText(getApplicationContext(), ("successfully opened Default.txt"), Toast.LENGTH_LONG).show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            outputToDefaultFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Return to activity_main and close this activity
        setContentView(R.layout.activity_main);
        this.finish();

    }

    //Method that is called when user clicks "Load Profile" button
    public void loadProfileSelected(View v){

        //If user has selected an item in the ListView, save to default.txt
        if(defaultName != null && defaultMPG != null){

            //String we will write to Default.txt
            String defaults = (defaultName + "," + defaultMPG);

            //Clear file of any contents before writing new data
            deleteFile(defaultProfileFileName);

            //Open file to write to
            FileOutputStream outputToFile = null;
            try {
                //MODE_APPEND will add to end of file, instead of overwriting the file
                outputToFile = openFileOutput(defaultProfileFileName, Context.MODE_PRIVATE);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            //Write to file
            if (outputToFile != null) {
                try {
                    outputToFile.write(defaults.getBytes());
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

            //Return to activity_main and close this activity
            setContentView(R.layout.activity_main);
            this.finish();

        }
        //Else if user has not selected a ListView item, toast an error message.
        else{

            Toast toasty = null;
            toasty.makeText(getApplicationContext(), "No profile selected!", Toast.LENGTH_LONG).show();

        }
        
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
