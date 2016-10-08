package com.stom3.android.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;

import com.stom3.android.api.ResponseCallback;
import com.stom3.android.api.User;
import com.stom3.android.storage.PreferencesHelper;

import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RegistrationIntentService extends IntentService {

    public static final String TAG = "RegIntentService";

    public RegistrationIntentService() {
        super(TAG);
    }

    private ResponseCallback responseCallback;

    @Override
    protected void onHandleIntent(Intent intent) {


        ExecutorService worker = Executors.newSingleThreadExecutor();
        worker.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {

                GcmTokenHelper tokenHelper = new GcmTokenHelper();
                String token = tokenHelper.getGcmToken(RegistrationIntentService.this);
                PreferencesHelper prefs = PreferencesHelper.getInstance();
                prefs.setGcmToken(token);

                String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

                User.uuidConnect(androidId, token, new ResponseCallback<HashMap<String, String>>() {
                    @Override
                    public void onResponse(HashMap<String, String> response) {
                        Log.d(TAG, "uuid registered");
                    }

                    @Override
                    public void onError(String error) {
                        String errorStr = "uuid not registered:" + error;
                        Log.e(TAG, errorStr);
                    }
                });
                return null;
            }

        });

        worker.shutdown();
        try {
            worker.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {

        }

    }
}
