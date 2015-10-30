package com.vihaan.testapilevel14;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.localytics.android.Localytics;

/**
 * Note: You will not get push received events tracked when you use your own BroadcastReceiver
 **/
public class CustomPushReceiver extends BroadcastReceiver {
    private final static String tag = CustomPushReceiver.class.getSimpleName();
    private final int YOUR_NOTIFICATION_ID = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        Context appContext = context.getApplicationContext();
        // Safeguard for bad integration. Call integrate() only if key is in manifest
        Localytics.integrate(appContext);
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(appContext);
        String messageType = gcm.getMessageType(intent);
        if (!extras.isEmpty()) // has effect of unparcelling bundle
        {
            if (messageType == GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE) {
                startNotification(appContext, intent);
                //handlePushReceived(appContext, intent);
            }
        }
    }


    private void startNotification(Context context, Intent intent) {
        try {
            if (intent.getExtras().getString("PushImageHandler") == null) {
            showNotification(context, intent);
            } else {
                long DB_rowID = -1;
                SendNotification async = new SendNotification(context, intent, DB_rowID);
                async.execute(intent.getExtras().getString("PushImageHandler"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void showNotification(Context context, Intent intent) {
        NotificationManager notificationManager = Utils.getNotificationManager();

        Intent resultIntent = new Intent(context, MainActivity.class);
        resultIntent.putExtras(intent.getExtras());

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(App.getAppContext());
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Bitmap icon = BitmapFactory.decodeResource(context.getResources(), getNotificationIcon());
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Notification notification = new NotificationCompat.Builder(context)
                .setSmallIcon(getNotificationIcon())
                .setAutoCancel(true)
                .setLargeIcon(icon)
                .setContentIntent(pendingIntent)
                .setContentTitle("Mirraw")
                .setContentText(intent.getExtras().getString("message"))
                .setSound(alarmSound)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .build();

        int id = (int) System.currentTimeMillis();
        notificationManager.notify(id, notification);
        Log.v(tag, "Notification ID: " + id);
    }

    private int getNotificationIcon() {
        boolean whiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        return whiteIcon ? R.mipmap.ic_launcher : R.mipmap.ic_launcher;
        //return R.mipmap.ic_launcher;
    }


}