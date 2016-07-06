package com.stom3.android.gcm;

import android.content.Context;
import android.os.AsyncTask;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.stom3.android.R;
import com.stom3.android.storage.PreferencesHelper;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class GcmTokenHelper {

    public String generateGcmToken(final Context context) throws ExecutionException, InterruptedException {
        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String token = null;
                try {
                    InstanceID instanceID = InstanceID.getInstance(context);
                    String senderId =  context.getString(R.string.gcm_defaultSenderId);
                    token = instanceID.getToken(senderId, GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                } catch (final IOException e) {
                }
                return token;
            }
        };
        return task.execute().get();
    }

    public String getGcmToken(Context context){
        PreferencesHelper prefs = PreferencesHelper.getInstance();
        String gcmToken = prefs.getGcmToken();
        if(gcmToken==null){
            try {
                gcmToken = generateGcmToken(context);
            } catch (ExecutionException | InterruptedException e) {
            }
        }
        return gcmToken;
    }
}
