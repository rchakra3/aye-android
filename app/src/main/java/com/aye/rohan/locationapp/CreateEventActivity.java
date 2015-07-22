package com.aye.rohan.locationapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import com.aye.rohan.locationapp.R;
import android.util.Log;

public class CreateEventActivity extends ActionBarActivity {

    //Class vars accesible to next view
    public static String LAT_KEY = "latitude";
    public static String LONG_KEY = "longitude";
    public static String DESC_KEY = "description";
    public static String U_NAME_KEY = "user_name";

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

        startActivity(intent);
    }

}
