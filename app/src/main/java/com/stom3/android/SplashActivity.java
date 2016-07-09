package com.stom3.android;

import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;
import com.stom3.android.api.Indexes;
import com.stom3.android.api.ResponseCallback;
import com.stom3.android.api.response.IndexesMarket;
import com.stom3.android.auth.AuthActivity;
import com.stom3.android.storage.PreferencesHelper;

import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ScheduledExecutorService worker = Executors.newSingleThreadScheduledExecutor();
        worker.schedule(new Runnable() {
            @Override
            public void run() {
                Indexes.getIndexes(new ResponseCallback<LinkedList<IndexesMarket>>() {
                    @Override
                    public void onResponse(LinkedList<IndexesMarket> response) {
                        PreferencesHelper.getInstance().saveIndexes(new Gson().toJson(response));
                        openMain();
                    }

                    @Override
                    public void onError(String error) {
                        openMain();
                    }
                });

            }
        }, 2, TimeUnit.SECONDS);
    }

    private void openLogin() {
        Intent intent = new Intent(SplashActivity.this, AuthActivity.class);
        startActivity(intent);
        finish();
    }

    private void openMain() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
