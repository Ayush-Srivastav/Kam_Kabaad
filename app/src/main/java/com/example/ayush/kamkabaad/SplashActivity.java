package com.example.ayush.kamkabaad;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* TODO :
                if user is logged out direct to login page
                else if he is user direct to user dashboard
                else if he is driver direct to driver dashboard */

                startActivity(new Intent(SplashActivity.this, RoutingActivity.class));
            }
        } , 3000);
    }
}
