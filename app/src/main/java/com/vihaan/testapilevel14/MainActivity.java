package com.vihaan.testapilevel14;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.localytics.android.Localytics;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // If you're using Localytics Push Messaging
//        Localytics.registerPush("YOUR_PROJECT_NUMBER");
        Localytics.registerPush("186772346249");

        // Activity Creation Code
    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
        setIntent(intent);
    }
}
