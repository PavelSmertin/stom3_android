package com.stom3.android.gcm;

import android.app.IntentService;
import android.content.Intent;

import com.stom3.android.api.ResponseCallback;
import com.stom3.android.api.User;
import com.stom3.android.api.response.Response;
import com.stom3.android.storage.PreferencesHelper;

public class RegistrationIntentService extends IntentService {

    public static final String TAG = "RegIntentService";

    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        GcmTokenHelper tokenHelper = new GcmTokenHelper();
        String token = tokenHelper.getGcmToken(this);
        PreferencesHelper prefs = PreferencesHelper.getInstance();
        prefs.setGcmToken(token);

        User.uuidConnect(token, new ResponseCallback<Response>() {
            @Override
            public void onResponse(Response response) {
            }

            @Override
            public void onError(String error) {
            }
        });
    }
}
