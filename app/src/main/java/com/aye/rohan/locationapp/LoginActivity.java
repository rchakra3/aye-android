package com.aye.rohan.locationapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;


public class LoginActivity extends ActionBarActivity {

    private String accessToken = null;
    private String userID = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_login);
    }

    @Override
    protected void onResume(){
        super.onResume();
        checkForLogin();
    }

    private void checkForLogin() {

        if(AccessToken.getCurrentAccessToken() != null) {
            accessToken = AccessToken.getCurrentAccessToken().getToken();
            userID = AccessToken.getCurrentAccessToken().getUserId();
            openMainActivity(userID,accessToken);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

    private void openMainActivity(String userID, String accessToken){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("userID",userID);
        intent.putExtra("accessToken",accessToken);
        Log.e("AYE","STARTING MAIN ACTIVITY");
        startActivity(intent);
    }
}
