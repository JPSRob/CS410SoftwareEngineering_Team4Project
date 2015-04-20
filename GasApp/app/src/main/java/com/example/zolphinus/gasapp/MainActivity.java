package com.example.zolphinus.gasapp;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class MainActivity extends ListActivity {
    
    
    /**************************/ 
    //load message additions
            
            //Credit Kostya But via stackoverflow.com
    String myLog = "myLog";

    AlphaAnimation inAnimation;
    AlphaAnimation outAnimation;

    FrameLayout progressBarHolder;
    Button generateButton;
    
    /**********************/ 

    private ProgressDialog pDialog = null;

    double latitude = 0.0;
    double longitude = 0.0;
    double mpgValue = 0.1;
    double tankSize = 11.5;




    //url to get JSON info
    private String firstPart = "http://api.mygasfeed.com/stations/radius/";
    private String secondPart = "/reg/price/ik3c9jau1p.json?";

    private String testError = "TEST";
    private Boolean JSONFail = false;
    private int numTries;


    //private static String url = "http://api.androidhive.info/contacts/";



    //GPS Class
    private GPSTracker gps;

    //JSON Node labels
    private static final String TAG_STATUS = "status";
    private static final String TAG_STATUS_ERROR = "error";
    private static final String TAG_STATUS_CODE = "code";
    private static final String TAG_STATUS_DESCRIPTION = "description";
    private static final String TAG_STATUS_MESSAGE = "message";

    private static final String TAG_GEO = "geoLocation";
    private static final String TAG_GEO_COUNTRY_SHORT = "country_short";
    private static final String TAG_GEO_LAT = "lat";
    private static final String TAG_GEO_LNG = "lng";
    private static final String TAG_GEO_ADDRESS = "address";
    private static final String TAG_GEO_CITY_LONG = "city_long";
    private static final String TAG_GEO_REGION_SHORT = "region_short";
    private static final String TAG_GEO_REGION_LONG = "region_long";
    private static final String TAG_GEO_COUNTRY_LONG = "country_long";


    private static final String TAG_STATIONS = "stations";
    private static final String TAG_STATIONS_ZIP = "zip";
    private static final String TAG_STATIONS_COUNTRY = "country";
    private static final String TAG_STATIONS_REG_PRICE = "reg_price";
    private static final String TAG_STATIONS_MID_PRICE = "mid_price";
    private static final String TAG_STATIONS_PRE_PRICE = "pre_price";
    private static final String TAG_STATIONS_DIESEL_PRICE = "diesel_price";
    private static final String TAG_STATIONS_ADDRESS = "address";
    private static final String TAG_STATIONS_DIESEL = "diesel";
    private static final String TAG_STATIONS_ID = "id";
    private static final String TAG_STATIONS_LAT = "lat";
    private static final String TAG_STATIONS_LNG = "lng";
    private static final String TAG_STATIONS_STATION = "station";
    private static final String TAG_STATIONS_REGION = "region";
    private static final String TAG_STATIONS_CITY = "city";
    private static final String TAG_STATIONS_REG_DATE = "reg_date";
    private static final String TAG_STATIONS_MID_DATE = "mid_date";
    private static final String TAG_STATIONS_PRE_DATE = "pre_date";
    private static final String TAG_STATIONS_DIESEL_DATE = "diesel_date";
    private static final String TAG_STATIONS_DISTANCE = "distance";
    private static final String TAG_VALUE = "value";

    //By default, sorting starts with Regular Gas prices
    String sortKey = TAG_STATIONS_REG_PRICE;
    String priceKey = TAG_STATIONS_REG_PRICE;


    JSONArray stations = null;

    ArrayList<Map<String, String>> stationList;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //String latitude;
        //String longitude;
        super.onCreate(savedInstanceState);


        startActivity(new Intent(getApplicationContext(), Team4SplashScreen.class));
        startActivity(new Intent(getApplicationContext(), SplashScreen.class));
        setContentView(R.layout.activity_main);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        gps = new GPSTracker(MainActivity.this);
        StrictMode.setThreadPolicy(policy);
        stationList = new ArrayList<Map<String,String>>();
        ListView lv = getListView();

        //Load Message additions/*****************************************/ //Load Message additions
        generateButton = (Button) findViewById(R.id.generateButton);
        progressBarHolder = (FrameLayout) findViewById(R.id.progressBarHolder);
    }

    //Load Message additions
    private class loadingMessage extends AsyncTask <Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            generateButton.setEnabled(false);
            inAnimation = new AlphaAnimation(0f, 1f);
            inAnimation.setDuration(400);
            progressBarHolder.setAnimation(inAnimation);
            progressBarHolder.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            outAnimation = new AlphaAnimation(1f, 0f);
            outAnimation.setDuration(400);
            progressBarHolder.setAnimation(outAnimation);
            progressBarHolder.setVisibility(View.GONE);
            generateButton.setEnabled(true);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                for (int i = 0; i < 5; i++) {
                    Log.d(myLog, "Step " + i);
                    TimeUnit.SECONDS.sleep(1);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }//Load Message additions
    
    //button click for Create and Load Profile Buttons
    //BORROWED LOGIC AND CODE FROM DEVELOPER.ANDROID.COM

    public void createProfileClick(View v) {
        //Intent intent = new Intent(this, profileScreen.class);
        //Button createProfile = (Button) v;
        startActivity(new Intent(getApplicationContext(), profileScreen.class));
    }
    //this name needs to correspond to the what is seen with the button in the manifest file
    public void loadProfileClick(View v){
        //Intent intent =  new Intent(this, loadProfile.class);
        startActivity( new Intent(getApplicationContext(), loadProfile.class));
        
    }

    
    
    //button click for the Generate Button
    public void generateClick(View v) {

        /*
        switch (v.getId()) {
            case R.id.generateButton:
                new loadingMessage().execute();
                break;
        }
        */


        //test for empty MPG edit text
        EditText editText = (EditText) findViewById(R.id.mpgEditText);
        RadioButton valueButton = (RadioButton) findViewById(R.id.valueRadioButton);
        RadioButton priceButton = (RadioButton) findViewById(R.id.priceRadioButton);
        RadioButton distanceButton = (RadioButton) findViewById(R.id.distanceRadioButton);

        if (valueButton.isChecked()) {
            if (TextUtils.isEmpty(editText.getText())) {
                Toast.makeText(getApplicationContext(), "Please enter MPG.", Toast.LENGTH_LONG).show();
            } else {
                mpgValue = Double.parseDouble(editText.getText().toString());
                makeJSONCall();
            }
        }else if(distanceButton.isChecked() || priceButton.isChecked()){
            mpgValue = 0.0;
            makeJSONCall();
        }
    }
    private Runnable jsonRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            new GetContacts().execute();
        }
    };

    
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            if(pDialog == null) {
                pDialog = new ProgressDialog(MainActivity.this);
                pDialog.setMessage("Please wait...");
                pDialog.setCancelable(false);
                pDialog.show();
            }

        }

        @Override
        protected Void doInBackground(Void... params) {
           //build url to get JSON info
            Spinner radiusSpinner = (Spinner) findViewById(R.id.radiusDropSpinner);
            Spinner fuelSpinner = (Spinner) findViewById(R.id.fuelDropSpinner);

            String fuelType = fuelSpinner.getSelectedItem().toString();

            RadioButton fuelButton = (RadioButton) findViewById(R.id.priceRadioButton);
            RadioButton distanceButton = (RadioButton) findViewById(R.id.distanceRadioButton);
            RadioButton valueButton = (RadioButton) findViewById(R.id.valueRadioButton);
            String dollarSign = "";
            String gasType = "";


            dollarSign = gasType + " $";

            if(fuelButton.isChecked()){
                if(fuelType.equals("Regular")){
                    sortKey = TAG_STATIONS_REG_PRICE;
                }
                if(fuelType.equals("Mid")){
                    sortKey = TAG_STATIONS_MID_PRICE;
                }
                if(fuelType.equals("Premium")){
                    sortKey = TAG_STATIONS_PRE_PRICE;
                }
                if(fuelType.equals("Diesel")){
                    sortKey = TAG_STATIONS_DIESEL_PRICE;
                }
            }else if(distanceButton.isChecked()){
                sortKey = TAG_STATIONS_DISTANCE;
            }else if(valueButton.isChecked()){
                sortKey = TAG_VALUE;
            }else{
                //error case, should never reach this
                testError = "NO BUT";
            }

            if(fuelType.equals("Regular")){
                fuelType = "/reg";
                gasType = "Reg";
                priceKey = TAG_STATIONS_REG_PRICE;
            }else if(fuelType.equals("Mid")){
                fuelType = "/mid";
                gasType = "Mid";
                priceKey = TAG_STATIONS_MID_PRICE;
            }else if(fuelType.equals("Premium")){
                fuelType = "/pre";
                gasType = "Pre";
                priceKey = TAG_STATIONS_PRE_PRICE;
            }else if(fuelType.equals("Diesel")){
                fuelType = "/diesel";
                gasType = "Dsl";
                priceKey = TAG_STATIONS_DIESEL_PRICE;
            }else{
                fuelType = "/reg";
                gasType = "ERR";
                priceKey = TAG_STATIONS_REG_PRICE;
            }





            String latStr = String.valueOf(latitude);
            String lngStr = String.valueOf(longitude);
            String radius = radiusSpinner.getSelectedItem().toString();
            String url = firstPart + latStr + '/' + lngStr + '/' + radius + secondPart; //+ fuelType + secondPart;

            //erases old data to avoid populating a list multiple times


            ServiceHandler sh = new ServiceHandler();

            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

            Log.d("Reponse: ", "> " + jsonStr);


            if(jsonStr != null){
                try{
                    //grabs a JSON object from the url
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    stationList.clear();


                    //grabs the status node from the JSON request
                    JSONObject status = jsonObj.getJSONObject(TAG_STATUS);
                    String status_error = status.getString(TAG_STATUS_ERROR);
                    String status_code = status.getString(TAG_STATUS_CODE);
                    String status_description = status.getString(TAG_STATUS_DESCRIPTION);
                    String status_message = status.getString(TAG_STATUS_MESSAGE);


                    //grabs the geoLocation node from the JSON request

                    //we may need to check if the JSON call returns an object
                    JSONObject geoLocation = jsonObj.getJSONObject(TAG_GEO);

                    //and if not, check for a JSON array and skip the normal fields

                    String country_short = geoLocation.getString(TAG_GEO_COUNTRY_SHORT);
                    String geo_lat = geoLocation.getString(TAG_GEO_LAT);
                    String geo_lng = geoLocation.getString(TAG_GEO_LNG);
                    String country_long = geoLocation.getString(TAG_GEO_COUNTRY_LONG);
                    String region_short = geoLocation.getString(TAG_GEO_REGION_SHORT);
                    String region_long = geoLocation.getString(TAG_GEO_REGION_LONG);
                    String city_long = geoLocation.getString(TAG_GEO_CITY_LONG);
                    String geo_address = geoLocation.getString(TAG_GEO_ADDRESS);

                    stations = jsonObj.getJSONArray(TAG_STATIONS);



                    //loop through array
                    for(int i = 0; i < stations.length(); i++){
                        //grabs first object in array
                        JSONObject c = stations.getJSONObject(i);



                        //grab object fields and assign to strings

                        String country = c.getString(TAG_STATIONS_COUNTRY);
                        String zip = c.getString(TAG_STATIONS_ZIP);
                        String reg_price = c.getString(TAG_STATIONS_REG_PRICE);
                        String mid_price = c.getString(TAG_STATIONS_MID_PRICE);
                        String pre_price = c.getString(TAG_STATIONS_PRE_PRICE);
                        String diesel_price = c.getString(TAG_STATIONS_DIESEL_PRICE);
                        String reg_date = c.getString(TAG_STATIONS_REG_DATE);
                        String mid_date = c.getString(TAG_STATIONS_MID_DATE);
                        String pre_date = c.getString(TAG_STATIONS_PRE_DATE);
                        String diesel_date = c.getString(TAG_STATIONS_DIESEL_DATE);
                        String address = c.getString(TAG_STATIONS_ADDRESS);
                        String diesel = c.getString(TAG_STATIONS_DIESEL);
                        String station_id = c.getString(TAG_STATIONS_ID);
                        String lat = c.getString(TAG_STATIONS_LAT);
                        String lng = c.getString(TAG_STATIONS_LNG);
                        String station = c.getString(TAG_STATIONS_STATION);
                        String region = c.getString(TAG_STATIONS_REGION);
                        String city = c.getString(TAG_STATIONS_CITY);
                        String distance = c.getString(TAG_STATIONS_DISTANCE);


                        //create temporary  map for single entry
                        Map<String, String> station_map = new HashMap<String, String>();

                        //create new Station object and store the values in it

                        Double stationValue = 0.0;
                        //calculate value here
                        if(fuelType.equals("/reg")){
                            stationValue = getValue(distance, reg_price);
                        }
                        if(fuelType.equals("/mid")){
                            stationValue = getValue(distance, mid_price);
                        }
                        if(fuelType.equals("/pre")){
                            stationValue = getValue(distance, pre_price);
                        }
                        if(fuelType.equals("/diesel")){
                            stationValue = getValue(distance, diesel_price);
                        }

                        String gasValue = String.valueOf(stationValue);





                        //adds child nodes into HashMap   key/value
                        station_map.put(TAG_STATIONS_STATION, station);
                        station_map.put(TAG_STATIONS_REG_PRICE, dollarSign + reg_price);
                        station_map.put(TAG_STATIONS_ADDRESS, address);
                        station_map.put(TAG_STATIONS_DISTANCE, distance);
                        station_map.put(TAG_STATIONS_MID_PRICE, dollarSign +  mid_price);
                        station_map.put(TAG_STATIONS_PRE_PRICE, dollarSign +  pre_price);
                        station_map.put(TAG_STATIONS_DIESEL_PRICE, dollarSign + diesel_price);

                        //add value here
                        station_map.put(TAG_VALUE, gasValue);

                        //then add to the entry list
                        stationList.add(station_map);
                    }
                    Collections.sort(stationList, new ListMapComparator(sortKey));
                    JSONFail = false;
                } catch (JSONException e){
                    e.printStackTrace();
                    JSONFail = true;
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            super.onPostExecute(result);
            numTries++;
            final Handler handler = new Handler();
            if(JSONFail == true && numTries < 4){
                handler.postDelayed(jsonRunnable, 2500);
            }

            if(numTries == 4){
                //error flag for error message goes here
                testError = "4 Fails";
            }

            if (pDialog != null) {
                if(numTries == 4 || JSONFail == false) {
                    if (pDialog.isShowing())
                        pDialog.dismiss();
                    pDialog = null;
                }
            }


            setListAdapter(null);

            //Update JSON into ListView
                  ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this, stationList,
                    R.layout.list_item, new String[] {TAG_STATIONS_STATION, TAG_STATIONS_ADDRESS,
                    TAG_STATIONS_DISTANCE, priceKey }, new int[] {R.id.name,
                    R.id.email, R.id.mobile, R.id.price});
            setListAdapter(adapter);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

    private void emptyMPGfield(){
        //handles the case where the user tries to press the Generate button
        //but MPG edit text is empty

    }

    private void makeJSONCall(){
        //handles the case where all necessary information is ready to make the JSON URL
        //then attempts to make the call up to three times

        //start loading screen here


        //checks if GPS values can be obtained
        if (gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            //Test toast to verify lat/long values
            //Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        } else {
            //Toast.makeText(getApplicationContext(), "Couldn't load",Toast.LENGTH_LONG).show();
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }

        numTries = 0;
        JSONFail = false;

        new GetContacts().execute();
        //final TextView textViewToChange = (TextView) findViewById(R.id.vehicleTextView);
        //textViewToChange.setText(testError);
    }

    double getValue(String distance, String price){
        double CostToDrive;
        double CostOfGas;
        double value;

        String temp = distance.replaceAll(" miles", "");


        if(temp.equals("0")){
            temp = "0.1";
        }

        String tempPrice = price;
        if(tempPrice.equals("N/A")){
            return 9999999.0;
        }

        double dis = Double.parseDouble(temp);
        double pric = Double.parseDouble(tempPrice);

        CostToDrive = (dis / mpgValue) * pric;
        CostOfGas = tankSize * pric;


        value = CostToDrive + CostOfGas;

        if(tempPrice.equals("999.99")){
            testError = String.valueOf(value);
        }
        return value;
        //return String.valueOf(value);
    }
}
