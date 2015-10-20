package com.vihaan.testapilevel14;

import android.app.Application;
import android.content.Context;

import com.localytics.android.Localytics;
import com.localytics.android.LocalyticsActivityLifecycleCallbacks;

/**
 * Created by vihaan on 19/10/15.
 */
public class App extends Application {

    private static Context sContext;

    @Override
    public void onCreate()
    {
        super.onCreate();
        sContext = getApplicationContext();

        // Register LocalyticsActivityLifecycleCallbacks
        registerActivityLifecycleCallbacks(
                new LocalyticsActivityLifecycleCallbacks(this));

        Localytics.setLoggingEnabled(true);
    }

    public static Context getAppContext() {
        return sContext;
    }
}
