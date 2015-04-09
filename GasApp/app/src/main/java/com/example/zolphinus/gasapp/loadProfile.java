package com.example.zolphinus.gasapp;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;


public class loadProfile extends ListActivity {

    //Variables & declarations
    private static String profileFileName = "Profiles.txt"; //Internal file name
    FileInputStream readInFile = null;
    String tempName = null;

    //ArrayList<HashMap to hold profile entries for ListView GUI
    ArrayList<HashMap<String, String>> profileList;
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



        /*Load.setOnClickListener(new View.OnClickListener());
        {
            //if listern sense activity load struct info 

        }*/

  /*  public void onClick(View v){
        try{
            FileInputStream fis = openFileInput("text.txt");
            InputStreamReader isr = new InputStreamReader(fis)
        }catch ( FileNotFoundException e){
            e.printStackTrace();
        }
 */       
    }
        //profileName = findViewById(R.id.textView2)

    public void updateListView(){

        //Variables & declarations
        int intToCast = 0;
        int counter = 0;
        int counterDelimiter = 0;
        char tempChar;
        String tempString = null;
        Boolean endOfFile = false;
        Boolean afterComma = false;
        String name = null, mpg = null , fuel = null; //HashMap id values
        HashMap<String, String> profileHash = new HashMap<String, String>();

    /*
        //Attempt to open the internal Profiles.txt
        try {
            readInFile = openFileInput(profileFileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Put contents of file into String to be parsed
        byte[] inputByte = new byte[0];

        try {
            inputByte = new byte[readInFile.available()];
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            while(readInFile.read(inputByte) != -1) {}
        } catch (IOException e) {
            e.printStackTrace();
        }
        inputString += new String(inputByte);

        //Close the file
        try {
            readInFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    */
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

        //While loop that will read each line from file
        while(endOfFile == false){

            //Read a line from the file
            try {
                tempString = buffReader.readLine();

                //If line read is not empty, parse the line
                if(tempString.length() != 0) {

                    //Clear HashMap for new entry
                    profileHash.clear();
                    //Clear counter for new line
                    counter = 0;

                    //While loop that will parse the current line
                    while (counter < tempString.length()) {

                        //Grab the current substring character from the line
                        tempChar = tempString.charAt(counter);

                        //If character is a comma, ignore this delimiter and change afterComma to true to indicate we're now reading into mpg field
                        if (tempChar == ',') {
                            afterComma = true;
                            counter++;
                        }
                        //Else if we have not hit the comma delimiter in the line, we are reading into name field
                        else if (tempChar != ',' && afterComma == false){
                            name = name + tempChar;
                            counter++;
                        }
                        //Else if we have already hit the comma delimiter, read into mpg field
                        else if (tempChar != ',' && afterComma == true){
                            mpg = mpg + tempChar;
                            counter++;
                        }

                    }

                    //Add the name and mpg Strings to the HashMap
                    profileHash.put(TAG_NAME, name);
                    profileHash.put(TAG_MPG, mpg);
                    //profileHash.put(TAG_FUEL, fuel); //Will implement fuel type later

                    //Clear the name, mpg, and fuel Strings for use in next line
                    name = null;
                    mpg = null;
                    //fuel = null; //Will implement fuel type later

                    //Add HashMap to ArrayList
                    profileList.add(profileHash);

                }
                //Else if line read is empty, we have hit the end of the file and should stop reading
                else if (tempString.length() == 0){
                    endOfFile = true;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        //*********FOR TESTING PURPOSES************
        Toast butteredToast = null;
        butteredToast.makeText(getApplicationContext(), poopString, Toast.LENGTH_LONG).show();
        //*****************************************

        //Display profileList into ListView
        //ListAdapter adapter = new SimpleAdapter(this, profileList, R.layout.load_layout,
        //        new String[]{TAG_NAME, TAG_MPG}, new int[]{R.id.nameLayout, R.id.mpgLayout});
        //setListAdapter(null); //Clear prior list adapters
        //setListAdapter(adapter);

    }
        
    public void loadProfileSelected(View v){
        //startActivity(new Intent(getApplicationContext(), MainActivity.class
        //));
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
