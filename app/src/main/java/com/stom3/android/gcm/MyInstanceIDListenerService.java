package com.stom3.android.gcm;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.iid.InstanceIDListenerService;

public class MyInstanceIDListenerService extends InstanceIDListenerService {
    private final String LOG_TAG = "Instance_ID";

    @Override
    public void onTokenRefresh() {
        Log.d(LOG_TAG, "onTokenRefresh");
        startService(new Intent(this, RegistrationIntentService.class));
    }
}
