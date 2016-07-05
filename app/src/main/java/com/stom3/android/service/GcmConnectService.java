package com.stom3.android.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.stom3.android.api.ResponseCallback;
import com.stom3.android.api.User;
import com.stom3.android.helpers.GcmTokenHelper;

public class GcmConnectService extends Service {

    public GcmConnectService() {}

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {}

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        GcmTokenHelper tokenHelper = new GcmTokenHelper();
        String gcmToken = tokenHelper.getGcmToken(this);
        if (gcmToken != null) {
            User.uuidConnect(gcmToken, new ResponseCallback() {
                @Override
                public void onResponse(Object response) {
                }

                @Override
                public void onError(String error) {
                    stopSelf();
                }
            });
        }
        return START_REDELIVER_INTENT;
    }

}
