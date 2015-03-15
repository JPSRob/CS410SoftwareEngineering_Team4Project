package com.example.zolphinus.gasapp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class loadProfile extends ActionBarActivity {
    Button Load; //the button that loads and exits activity
    int information_Search = 70;
    //EditText profileName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_profile);
        Load = (Button) findViewById(R.id.Load);

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

    
        
    public void loadProfileSelected(View v){
        //startActivity(new Intent(getApplicationContext(), MainActivity.class
        //));
        setContentView(R.layout.activity_main);
        
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
