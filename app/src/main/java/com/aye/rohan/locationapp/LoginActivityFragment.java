package com.aye.rohan.locationapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;


/**
 * A placeholder fragment containing a simple view.
 */
public class LoginActivityFragment extends Fragment {

    public LoginActivityFragment() {
    }

    private LoginButton loginButton;
    private CallbackManager callbackManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        callbackManager = CallbackManager.Factory.create();

        View v = inflater.inflate(R.layout.fragment_login, container, false);

        loginButton = (LoginButton) v.findViewById(R.id.login_button);

        if(loginButton == null){
            Log.e("AYEEEE","ERROR ERROR ERORRR");
        }
        loginButton.setFragment(this);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.e("aye", "SUCCESS");
                openMainActivity(loginResult.getAccessToken().getUserId(), loginResult.getAccessToken().getToken());
            }

            @Override
            public void onCancel() {
                Log.e("aye", "CANCEL");
            }

            @Override
            public void onError(FacebookException e) {
                Log.e("aye", "Error");
            }
        });
        return v;
    }

    private void openMainActivity(String userID, String accessToken){
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.putExtra("userID",userID);
        intent.putExtra("accessToken",accessToken);
        Log.e("AYE","STARTING MAIN ACTIVITY");
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
