package com.vihaan.testapilevel14;

import android.app.Application;

import com.localytics.android.Localytics;
import com.localytics.android.LocalyticsActivityLifecycleCallbacks;

/**
 * Created by vihaan on 19/10/15.
 */
public class App extends Application {

    @Override
    public void onCreate()
    {
        super.onCreate();

        // Register LocalyticsActivityLifecycleCallbacks
        registerActivityLifecycleCallbacks(
                new LocalyticsActivityLifecycleCallbacks(this));

        Localytics.setLoggingEnabled(true);
    }
}
