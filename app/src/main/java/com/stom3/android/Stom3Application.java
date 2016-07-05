package com.stom3.android;

import android.app.Application;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.stom3.android.statistic.StatisticAdapter;
import com.stom3.android.storage.PreferencesHelper;


public class Stom3Application extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        PreferencesHelper.getInstance().setSharedPreferences(this);


        //GA.getInstance(getApplicationContext()).sendScreenName("launch application"); // ga wrapper

        //Fabric.with(this, new Crashlytics());



        //JellyfishStorage.createInstance(this);

        /*FlurryAgent.setLogEnabled(false);

        if(BuildConfig.DEBUG) {
            //FlurryAgent.init(this, "FV7FJH9YBM8YPN2DCVTB");
        } else {
            FlurryAgent.init(this, "PB9R8WZK3S6XCMN7N6XN");
        }

        Flurry.newInstance(); // flurry wrapper*/

        StatisticAdapter.setUser();

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                //.imageDownloader(new SslImageDownloader(getBaseContext(), 1000, 1000))
                .writeDebugLogs()
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);

    }

    @Override
    public void onTerminate() {
        PreferencesHelper.getInstance().clearChangePinState();
        super.onTerminate();
    }
}
