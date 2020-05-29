package com.example.jeffquarnbergtrackingapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import androidx.core.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

public class AlarmReceiver extends BroadcastReceiver {
    private static int notificationId;
    private static final String COURSE_START_CHANNEL_ID = "course_start_channel";
    private NotificationManager mNotificationManager;
    private String message;

    public void createNotificationChannel() {

        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {

            NotificationChannel notificationChannel = new NotificationChannel(COURSE_START_CHANNEL_ID,
                    "Course Start Alert",
                    NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Alert for start of Course");
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        mNotificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        createNotificationChannel();
        Intent contentIntent = new Intent(context, AddCourseActivity.class);
        PendingIntent contentPendingIntent = PendingIntent.getActivity(context,
                notificationId, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (intent.hasExtra("start_message")) {
            message = intent.getStringExtra("start_message");

            NotificationCompat.Builder startBuilder = new NotificationCompat.Builder(context, COURSE_START_CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_alert)
                    .setContentTitle("WGU Alert")
                    .setContentText(message)
                    .setContentIntent(contentPendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true)
                    .setDefaults(NotificationCompat.DEFAULT_ALL);

            mNotificationManager.notify(notificationId++, startBuilder.build());
        } else if (intent.hasExtra("end_message")) {
            message = intent.getStringExtra("end_message");

            NotificationCompat.Builder endBuilder = new NotificationCompat.Builder(context, COURSE_START_CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_alert)
                    .setContentTitle("WGU Alert")
                    .setContentText(message)
                    .setContentIntent(contentPendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true)
                    .setDefaults(NotificationCompat.DEFAULT_ALL);

            mNotificationManager.notify(notificationId++, endBuilder.build());
        } else if (intent.hasExtra("goal_message")) {
            message = intent.getStringExtra("goal_message");

            NotificationCompat.Builder endBuilder = new NotificationCompat.Builder(context, COURSE_START_CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_alert)
                    .setContentTitle("WGU Alert")
                    .setContentText(message)
                    .setContentIntent(contentPendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true)
                    .setDefaults(NotificationCompat.DEFAULT_ALL);

            mNotificationManager.notify(notificationId++, endBuilder.build());
        }
    }
}
