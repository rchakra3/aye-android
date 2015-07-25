package com.aye.rohan.locationapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class TemporarySuccessActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temporary_success);

        Intent intent = getIntent();

        //retrieving contents from past activity
        String latitude = intent.getStringExtra(CreateEventActivity.LAT_KEY);
        String longitude = intent.getStringExtra(CreateEventActivity.LONG_KEY);
        String description = intent.getStringExtra(CreateEventActivity.DESC_KEY);
        String u_name = intent.getStringExtra(CreateEventActivity.U_NAME_KEY);

        //Text Views on Activity
        TextView txt_latitude = (TextView) findViewById(R.id.latitude);
        TextView txt_longitude = (TextView) findViewById(R.id.longitude);
        TextView txt_description = (TextView) findViewById(R.id.description);
        TextView txt_user_name = (TextView) findViewById(R.id.user_name);


        //Setting Text Values of Lat-Long-Desc-Uname
        txt_latitude.setText(latitude);
        txt_longitude.setText(longitude);
        txt_description.setText(description);
        txt_user_name.setText(u_name);

        try {
            new Handler().postDelayed(new Runnable() {

                public void run() {

                    Intent intent = new     Intent(TemporarySuccessActivity.this,MainActivity.class);
                    startActivity(intent);

                    TemporarySuccessActivity.this.finish();
                    

                    //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                }


            }, 100*1000);

            new Handler().postDelayed(new Runnable() {
                public void run() {
                }
            }, 100*1000);
        } catch(Exception e){}

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_temporary_success, menu);
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
