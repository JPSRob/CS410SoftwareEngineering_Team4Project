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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


public class loadProfile extends ListActivity {

    private static String profileFileName = "Profiles.txt"; //Internal file name
    FileInputStream readFile = null;
    ArrayList profiles = new ArrayList(); //ArrayList to hold profile entries for ListView GUI

    Button Load; //the button that loads and exits activity
    int information_Search = 70;
    //EditText profileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_profile);
        //Load = (Button) findViewById(R.id.Load);

        //Call function to read internal file and display to ListView
        //updateListView();

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
        //Attempt to open the internal Profiles.txt
        try {
            readFile = openFileInput(profileFileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Read Profiles.txt entries into profiles ArrayList
        int intToCast = 0;
        int counter = 0;
        char tempChar;
        String tempString = "";

        try {
            intToCast = readFile.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(intToCast != -1){
            //Grab characters from file one at a time
            tempChar = (char) intToCast;
            //If end of line, write tempString to ArrayList counter entry, then increment counter
            if(tempChar == '\n'){
                profiles.add(counter, tempString);
                counter++;
            }
            //Else, keep reading into tempString
            else{
                tempString = tempString + tempChar;
            }

            //Keep reading characters from file
            try {
                intToCast = readFile.read();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        //Display profiles ArrayList into ListView loadProfileClick GUI
        //!!!!!!!!!!!!!!!!!!! SUSPECT APP CRASH BEING CAUSED HERE
        ListAdapter adapter = new ArrayAdapter<ArrayList>(this, android.R.layout.simple_list_item_1, profiles);
        ListView listView = (ListView) findViewById(R.id.loadProfileClick);
        listView.setAdapter(adapter);
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
