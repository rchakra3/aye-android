package com.aye.rohan.locationapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;

public class CreateEventActivity extends ActionBarActivity {

    //Class vars accesible to next view
    public static String LAT_KEY = "latitude";
    public static String LONG_KEY = "longitude";
    public static String DESC_KEY = "description";
    public static String U_NAME_KEY = "user_name";
    public static String API_SERVER = "http://45.55.27.229";
    public static String PORT = "9000";
    public static String CREATE_EVENT_URI = "/new_event";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        setContentView(R.layout.activity_create_event);

        //retriving contents from past activity
        String latitude = intent.getStringExtra(MainActivity.LATITUDE_MSG_KEY);
        String longitude = intent.getStringExtra(MainActivity.LONGITUDE_MSG_KEY);

        //View text edits
        EditText txt_latitude = (EditText) findViewById(R.id.latValue);
        EditText txt_longitude = (EditText) findViewById(R.id.longValue);

        //Setting Text Values to Lat-Long
        txt_latitude.setText(latitude);
        txt_longitude.setText(longitude);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_event, menu);
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

    public void createEvent(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, TemporarySuccessActivity.class);

        //Extract lat and long values
        EditText latitude = (EditText) findViewById(R.id.latValue);
        EditText longitude = (EditText) findViewById(R.id.longValue);
        EditText description = (EditText) findViewById(R.id.event_description_Value);
        EditText u_name = (EditText) findViewById(R.id.user_name_value);

        //Pass Lat and Long values to next activity
        intent.putExtra(LAT_KEY, latitude.getText().toString());
        intent.putExtra(LONG_KEY, longitude.getText().toString());
        intent.putExtra(DESC_KEY, description.getText().toString());
        intent.putExtra(U_NAME_KEY, u_name.getText().toString());

        double latitude_val = Double.parseDouble(latitude.getText().toString());
        double longitude_val = Double.parseDouble(longitude.getText().toString());
        String event_name = u_name.getText().toString();
        String event_desc = description.getText().toString();

        /* Adding the call to API */
        new HttpSender().execute("" + latitude_val, "" + longitude_val, event_name, event_desc);
        /* ----------------------- */

        startActivity(intent);
    }

    public void createEventAPICall(double latitude_val, double longitude_val,
                                   String event_name, String event_desc)
    {

        try {

            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost postRequest = new HttpPost(API_SERVER+":"+PORT+CREATE_EVENT_URI);
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("latitude", latitude_val);
            jsonObj.put("longitude", longitude_val);
            jsonObj.put("event_name", event_name);
            jsonObj.put("event_description", event_desc);

            StringEntity input = new StringEntity(jsonObj.toString(), HTTP.UTF_8);
            System.out.print(API_SERVER+":"+PORT+CREATE_EVENT_URI);
            System.out.print(jsonObj.toString());

            input.setContentType("application/json");
            postRequest.setHeader("Accept", "application/json");
            postRequest.setEntity(input);

            HttpClient client = new DefaultHttpClient();
            HttpResponse response = client.execute(postRequest);
            System.out.print(response.getStatusLine().getStatusCode());
            httpClient.getConnectionManager().shutdown();

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private class HttpSender extends AsyncTask<String, Integer, Double> {
        @Override
        protected Double doInBackground(String... params) {
            // TODO Auto-generated method stub
            createEventAPICall( Double.parseDouble(params[0]),
                                Double.parseDouble(params[1]), params[2], params[3]);
            return null;
        }

        protected void onPostExecute(Double result){

        }

    }


}
