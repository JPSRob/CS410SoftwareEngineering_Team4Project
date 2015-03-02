package com.example.zolphinus.gasapp;
import android.content.Intent;
import android.app.ListActivity;
import android.app.ProgressDialog;
//import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.view.View;
import android.widget.Button;
//import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends ListActivity {

    private ProgressDialog pDialog;

    private String latitude = "45";
    private String longitude = "-73";


    //url to get JSON info
    private String url = "http://api.mygasfeed.com/stations/radius/45/-73/30/reg/price/ik3c9jau1p.json?";
    //private static String url = "http://api.androidhive.info/contacts/";



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

    JSONArray stations = null;

    JSONArray contacts = null;

    ArrayList<HashMap<String, String>> stationList;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.
        ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        stationList = new ArrayList<HashMap<String,String>>();

        ListView lv = getListView();
        //new GetContacts().execute();
    }

    //button click for the Generate Button
    public void generateClick(View v){
        new GetContacts().execute();
    }

    //button click for Create Profile Button
    //BORROWED LOGIC AND CODE FROM DEVELOPER.ANDROID.COM
    public void createProfileClicked(View v){
        Intent intent = new Intent(this, profileInformationScreen.class);
        //EditText editText = (EditText) findViewById(R.id.createProfileButton);
        startActivity(intent);
    }

    //button click for Create Profile Button
    public void loadProfileClick(View v){

    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            //pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            ServiceHandler sh = new ServiceHandler();

            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

            Log.d("Reponse: ", "> " + jsonStr);

            if(jsonStr != null){
                try{
                    //grabs a JSON object from the url
                    JSONObject jsonObj = new JSONObject(jsonStr);


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


                        //create temporary hash map for single entry
                        HashMap<String, String> station_map = new HashMap<String, String>();

                        //adds child nodes into HashMap   key/value
                        station_map.put(TAG_STATIONS_STATION, station);
                        station_map.put(TAG_STATIONS_REG_PRICE, reg_price);
                        station_map.put(TAG_STATIONS_ADDRESS, address);
                        station_map.put(TAG_STATIONS_DISTANCE, distance);

                        //then add to the entry list
                        stationList.add(station_map);
                    }

                } catch (JSONException e){
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            super.onPostExecute(result);
            //cleanup progress dialog
            if(pDialog != null){
                if(pDialog.isShowing())
                    pDialog.dismiss();
                pDialog = null;
            }


            //Update JSON into ListView

            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this, stationList,
                    R.layout.list_item, new String[] {TAG_STATIONS_STATION, TAG_STATIONS_ADDRESS,
                    TAG_STATIONS_DISTANCE }, new int[] {R.id.name,
                    R.id.email, R.id.mobile });

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
}
