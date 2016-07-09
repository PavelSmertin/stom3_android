package com.stom3.android.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stom3.android.api.response.IndexesMarket;
import com.stom3.android.auth.AuthActivity;

import java.lang.reflect.Type;
import java.util.LinkedList;

public class PreferencesHelper {

    private static final PreferencesHelper INSTANCE = new PreferencesHelper();

    public static final String NAME_APP = "stog";
    public static final String KEY_GCM_TOKEN = "gcm_token";
    public static final String KEY_AUTH_TOKEN = "auth_token";
    public static final String KEY_LOGIN = "login";
    public static final String KEY_AUTH_STATE = "auth_state";

    public static final String KEY_INDEXES = "indexes";

    private SharedPreferences sharedPreferences;


    public static PreferencesHelper getInstance() {
        return INSTANCE;
    }

    private PreferencesHelper() {

    }

    public void setSharedPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(NAME_APP, Context.MODE_PRIVATE);
    }

    public void setGcmToken(String token) {
        Editor editor = sharedPreferences.edit();
        editor.putString(KEY_GCM_TOKEN, token);
        editor.apply();
    }

    public String getGcmToken() {
        return sharedPreferences.getString(KEY_GCM_TOKEN, null);
    }

    public void setAuthToken(String token) {
        Editor editor = sharedPreferences.edit();
        editor.putString(KEY_AUTH_TOKEN, token);
        editor.apply();
    }

    public String getAuthToken() {
        return sharedPreferences.getString(KEY_AUTH_TOKEN, null);
    }

    public boolean isAuth() {
        return getAuthToken() != null;
    }

    public void setLogin(String login) {
        Editor editor = sharedPreferences.edit();
        editor.putString(KEY_LOGIN, login);
        editor.apply();
    }

    public String getLogin() {
        return sharedPreferences.getString(KEY_LOGIN, null);
    }

    public void setAuthState(AuthActivity.AuthState state) {
        Editor editor = sharedPreferences.edit();
        editor.putString(KEY_AUTH_STATE, state.toString());
        editor.apply();
    }

    public AuthActivity.AuthState getAuthState() {
        String value = sharedPreferences.getString(KEY_AUTH_STATE, null);
        if (value != null) {
            return AuthActivity.AuthState.valueOf(value);
        } else {
            return AuthActivity.AuthState.NONE;
        }
    }

    public void clearChangePinState() {
        if (getAuthState() == AuthActivity.AuthState.CHANGE_PIN) {
            setAuthState(AuthActivity.AuthState.SIGNIN);
        }
    }

    public void signout() {
        Editor editor = sharedPreferences.edit();
        editor.remove(KEY_AUTH_TOKEN);
        editor.remove(KEY_AUTH_STATE);
        editor.apply();
    }


    public void saveIndexes(String indexes) {
        Editor editor = sharedPreferences.edit();
        editor.putString(KEY_INDEXES, indexes);
        editor.apply();
    }

    public LinkedList<IndexesMarket> getIndexes() {
        Gson gson = new Gson();
        String indexes = sharedPreferences.getString(KEY_INDEXES, null);

        Type type = new TypeToken<LinkedList<IndexesMarket>>() {}.getType();

        return gson.fromJson(indexes, type);
    }

}
