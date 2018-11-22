package com.xavi.alquiler;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.iid.FirebaseInstanceId;
import com.xavi.alquiler.Services.Token;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Token.currentToken = FirebaseInstanceId.getInstance().getToken();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // This method will be executed once the timer is over
                Intent i = new Intent( SplashActivity.this, Principal.class);
                startActivity(i);
                finish();
            }
        }, 2000);
    }
}
