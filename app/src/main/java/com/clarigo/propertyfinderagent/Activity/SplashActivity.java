package com.clarigo.propertyfinderagent.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.clarigo.propertyfinderagent.Navigation_Drawer.LocaleHelper;
import com.clarigo.propertyfinderagent.R;
import com.clarigo.propertyfinderagent.app.SessionManager;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 1000;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sessionManager = new SessionManager(this);
        startSplashTimer();
    }

    private void startSplashTimer() {
        new Handler().postDelayed(new Runnable() {
            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                if (sessionManager.isLogin()) {
                    Intent i1 = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i1);
                    finish();
                } else {
                    Intent i = new Intent(SplashActivity.this, SignInActivity.class);
                    startActivity(i);
                }
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));

    }
}

