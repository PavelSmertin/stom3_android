package com.stom3.android.statistic;


import com.stom3.android.api.User;
import com.stom3.android.storage.PreferencesHelper;

public class StatisticAdapter {

    public static void sendButtonEvent(ButtonParamsPull params){
        //Flurry.getInstance().sendButtonEvent(params);
    }

    public static void sendPageEvent(ButtonParamsPull pararms){
        //Flurry.getInstance().sendPageEvent(pararms.getId());
    }

    public static void endPageEvent(ButtonParamsPull pararms){
        //Flurry.getInstance().endPageEvent(pararms.getId());
    }

    public static void setUser(){
        if(PreferencesHelper.getInstance().isAuth()) {
            //String hash = HashUtils.getSha256(Integer.toString(user.getId()));
            String hash = Integer.toString(User.getInstance().getId());
            //Flurry.getInstance().setUser(hash);
            //Crashlytics.setUserIdentifier(hash);
        }
    }
}
