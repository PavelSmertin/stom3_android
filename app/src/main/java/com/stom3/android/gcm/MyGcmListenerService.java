package com.stom3.android.gcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GcmListenerService;
import com.stom3.android.R;
import com.stom3.android.SplashActivity;

public class MyGcmListenerService extends GcmListenerService {

    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;

    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        if(message != null) {

            Intent activityIntent = new Intent(this, SplashActivity.class);

            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, activityIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            int stringId = getApplicationInfo().labelRes;
            String appName = getResources().getString(stringId);

            mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.ic_stat_logo)
                            .setContentTitle(appName)
                            .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                            .setContentText(message)
                            .setAutoCancel(true);

            mBuilder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
            mBuilder.setContentIntent(contentIntent);
            mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
        }
    }

}