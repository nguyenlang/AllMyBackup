package com.example.yuclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.google.android.
public class GoogleSignInActivity extends AppCompatActivity implements GoogleApiClient{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_sign_in);
    }
}