package com.vihaan.testapilevel14;

import android.app.NotificationManager;
import android.content.Context;

/**
 * Created by pavitra on 20/10/15.
 */
public class Utils {

    public static NotificationManager getNotificationManager() {
        return (NotificationManager) App.getAppContext().getSystemService(Context.NOTIFICATION_SERVICE);
    }
}
