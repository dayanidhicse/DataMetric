package com.example.dayanidhi.datametrics;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        /****** Create Thread that will sleep for 2 seconds *************/
        Thread background = new Thread() {
            public void run() {

                try {
                    // Thread will sleep for 2 seconds
                    sleep(2*1000);

                    // After 2 seconds redirect to another intent
                    Intent i=new Intent(getBaseContext(),HomeActivity.class);
                    startActivity(i);

                    //Remove activity
                    finish();

                } catch (Exception e) {

                }
            }
        };

        // start thread
        background.start();
    }
    @Override
    protected void onDestroy() {

        super.onDestroy();

    }
}
