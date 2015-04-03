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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class loadProfile extends ListActivity {

    //Variables & declarations
    private static String profileFileName = "Profiles.txt"; //Internal file name
    FileInputStream readInFile = null;
    String tempName = null;
    ArrayList<HashMap<String, String>> profileList; //ArrayList<HashMap to hold profile entries for ListView GUI
    String inputString = "kitty";


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
        String name = null, mpg = null , fuel = null; //HashMap id values
        //Create new HashMap entry
        HashMap<String, String> profileHash = new HashMap<String, String>();


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

        String poopString = "";
        //inputString = "Hello,21";
        //Parse String holding contents of file
        while(counter < inputString.length()){

            //Grab characters from string one at a time
            tempChar = inputString.charAt(counter);
            poopString = poopString + tempChar;
            //If find comma "," delimiter, ignore and increment counterDelimiter to 1 to indicate we're reading "mpg" characters
            if(tempChar == ','){
                counterDelimiter = 1;
            }

            //Else if counterDelimiter is 0, we're still reading in "name" characters
            else if(tempChar != '\n' && counterDelimiter == 0){
                name = name + tempChar;
            }

            //Else if counterDelimiter is 1, we're still reading in "mpg" characters
            else if(tempChar != '\n' && counterDelimiter == 1){
                mpg = mpg + tempChar;
            }

            /*If end of line: finish mpg entry, fill profileHash entries, add to profileList array, and reset counterDelimiter to 0
            to indicate we're reading "name" characters*/
            else if(tempChar == '\n' || tempChar == '\r'){
                mpg = mpg + tempChar;

                profileHash.put(TAG_NAME, name);
                profileHash.put(TAG_MPG, mpg);
                //profileHash.put(TAG_FUEL, fuel); //Will implement fuel type later

                tempName = tempName + name; //For testing with Toast
                name = null;
                mpg = null;
                //fuel = null;

                profileList.add(profileHash);

                counterDelimiter = 0;
            }

            counter++;

        }

        //*********FOR TESTING PURPOSES************
        Toast butteredToast = null;
        butteredToast.makeText(getApplicationContext(), poopString, Toast.LENGTH_LONG).show();
        //*****************************************

        //Display profileList into ListView
        ListAdapter adapter = new SimpleAdapter(this, profileList, R.layout.load_layout,
                new String[]{TAG_NAME, TAG_MPG}, new int[]{R.id.nameLayout, R.id.mpgLayout});
        setListAdapter(null); //Clear prior list adapters
        setListAdapter(adapter);

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
