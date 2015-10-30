package com.vihaan.testapilevel14;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by pavitra on 20/10/15.
 */
public class SendNotification extends AsyncTask<String, Void, Bitmap> {

    Context mContext;
    Intent intent;
    String url;
    String tag = SendNotification.class.getSimpleName();
    long DB_rowID = -1;

    public SendNotification(Context context, Intent intent, long DB_rowID) {
        super();
        this.DB_rowID = DB_rowID;
        this.mContext = context;
        this.intent = intent;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        InputStream inputStream;
        this.url = params[0];

        try {
            URL url1 = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
            connection.setDoInput(true);
            inputStream = connection.getInputStream();
            Bitmap remoteBitmap = BitmapFactory.decodeStream(inputStream);
            return remoteBitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    //static Boolean saved = true;

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);

        try {
            NotificationManager notificationManager = Utils.getNotificationManager();

            Bitmap remoteBitmap = bitmap;

            NotificationCompat.BigPictureStyle notificationStyle = new NotificationCompat.BigPictureStyle();
            notificationStyle.setBigContentTitle("Mirraw");
            notificationStyle.setSummaryText(intent.getExtras().getString("message"));


            notificationStyle.bigPicture(remoteBitmap);

            Intent resultIntent = new Intent(mContext, MainActivity.class);
                /*Bundle bundle = new Bundle();

                bundle.putString("title", "Test Title");
                bundle.putString("key", "collection");
                bundle.putString("value", "Treat Yourself");*/
            resultIntent.putExtras(intent.getExtras());

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(App.getAppContext());

            stackBuilder.addParentStack(MainActivity.class);

            stackBuilder.addNextIntent(resultIntent);

            PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

            Bitmap icon = BitmapFactory.decodeResource(mContext.getResources(), getNotificationIcon());

            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            Notification notification = null;

            /*if (saved)
                bitmap = null;*/
            if (bitmap != null) {
                notification = new NotificationCompat.Builder(mContext)
                        .setSmallIcon(getNotificationIcon())
                        .setAutoCancel(true)
                        .setLargeIcon(icon)
                        .setContentIntent(pendingIntent)
                        .setContentTitle("Mirraw")
                        .setContentText(intent.getExtras().getString("message"))
                        .setStyle(notificationStyle)
                        .setSound(alarmSound)
                        .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})/*
                            .setColor(mContext.getResources().getColor(R.color.button_green))*/
                        .build();

                int id = (int) System.currentTimeMillis();
                notificationManager.notify(id, notification);
                Log.v(tag, "Notification ID: " + id);

                /*if(DB_rowID>-1){
                    new SyncRequestManager().deleteRow(DB_rowID);
                }*/
            } else {
                //saved = false;
                /*if (DB_rowID==-1) {
                    saveNotificationToShowLater(mContext, intent, url);
                }*/

                notification = new NotificationCompat.Builder(mContext)
                        .setSmallIcon(getNotificationIcon())
                        .setAutoCancel(true)
                        .setLargeIcon(icon)
                        .setContentIntent(pendingIntent)
                        .setContentTitle("Mirraw")
                        .setContentText(intent.getExtras().getString("message"))
                        .setSound(alarmSound)
                        .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                                //.setColor(mContext.getResources().getColor(R.color.button_green))
                        .build();

                int id = (int) System.currentTimeMillis();
                notificationManager.notify(id, notification);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private int getNotificationIcon() {
        boolean whiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        return whiteIcon ? R.mipmap.ic_launcher : R.mipmap.ic_launcher;
        //return R.mipmap.ic_launcher;
    }

   /* private void saveNotificationToShowLater(Context context, Intent intent, String url) {
        JSONObject json = new JSONObject();
        Set<String> keys = intent.getExtras().keySet();
        HashMap<String, String> body = new HashMap<>();
        for (String key : keys) {
            try {
                // json.put(key, bundle.get(key)); see edit below
                json.put(key, intent.getExtras().get(key));
                body.put(key, intent.getExtras().get(key).toString());
            } catch (JSONException e) {
                //Handle exception here
                e.printStackTrace();
            }
        }

        Log.v(tag, "JSON Object: " + json.toString());

        HashMap<String, String> header = new HashMap<>();
        header.put("Notification", "YES");
        Request request = new Request.RequestBuilder(url, Request.RequestTypeEnum.GET).setHeaders(header).setBody(body).build();
        new SyncRequestManager().insertRow(new Gson().toJson(request));
    }*/
}

