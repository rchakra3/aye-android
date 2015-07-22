package com.aye.rohan.locationapp;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.w3c.dom.Text;


public class MainActivity extends ActionBarActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public final static String LATITUDE_MSG_KEY = "latitude";
    public final static String LONGITUDE_MSG_KEY = "longitude";

    GoogleApiClient mGoogleApiClient;
    private boolean mResolvingError = false;
    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!mResolvingError) {  // more about this later
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
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

    @Override
    public void onConnected(Bundle bundle) {
        Location mLastLocation=null;
        try {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
        }
        catch(Exception e){
            Log.i(TAG,"Location ERROR");
        }

        TextView latText = (TextView) findViewById(R.id.latValue);
        TextView longText = (TextView) findViewById(R.id.longValue);
        if (mLastLocation != null) {
            latText.setText(String.valueOf(mLastLocation.getLatitude()));
            longText.setText(String.valueOf(mLastLocation.getLongitude()));

        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG,"Connection Suspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG,"Connection Failed");
    }

    // Button Handlers
    /** Called when the user clicks the Send button */
    public void sendMessage(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, CreateEventActivity.class);

        //Extract lat and long values
        EditText latitude = (EditText) findViewById(R.id.latValue);
        EditText longitude = (EditText) findViewById(R.id.longValue);

        //Pass Lat and Long values to next activity
        intent.putExtra(LATITUDE_MSG_KEY, latitude.getText().toString());
        intent.putExtra(LONGITUDE_MSG_KEY, longitude.getText().toString());

        startActivity(intent);
    }

    public void searchEvents(View view){
        // Do something in response to button
        Intent intent = new Intent(this, ListEvent.class);

        //Extract lat and long values
        EditText latitude = (EditText) findViewById(R.id.latValue);
        EditText longitude = (EditText) findViewById(R.id.longValue);

        //Pass Lat and Long values to next activity
        intent.putExtra(LATITUDE_MSG_KEY, latitude.getText().toString());
        intent.putExtra(LONGITUDE_MSG_KEY, longitude.getText().toString());

        startActivity(intent);

    }

}
