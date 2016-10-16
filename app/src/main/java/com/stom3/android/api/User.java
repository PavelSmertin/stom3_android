package com.stom3.android.api;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.stom3.android.api.response.AuthResponse;
import com.stom3.android.api.response.CheckLoginResponse;
import com.stom3.android.api.response.Response;
import com.stom3.android.storage.PreferencesHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;

import cz.msebera.android.httpclient.entity.StringEntity;

public class User {
    private int id;
    private String email;

    /*SINGLETON*/
    public static User getInstance() {
        return SingletonHelper.INSTANCE;
    }
    private User(){}
    private static class SingletonHelper{
        private static final User INSTANCE = new User();
    }

    /*SETTERS GETTERS*/
    public int getId() {
        return id;
    }


    /* API */
    public static void signup(Context context, String login, String pin, final ResponseCallback<AuthResponse> responseHandler){
        InitialRequestParams params = new InitialRequestParams();

        JSONObject jsonUserParams = new JSONObject();
        JSONObject jsonParams = new JSONObject();

        try {
            jsonParams.put("email", login);
            jsonParams.put("password", pin);
            jsonUserParams.put("customer", jsonParams);
        } catch (JSONException ecxeption) {

        }

        try {
            StringEntity entity = new StringEntity(jsonUserParams.toString());
            Type type = new TypeToken<Response<AuthResponse>>() {}.getType();

            RestClient.getInstance().postRaw(context, "register", params, entity, type, responseHandler);
        } catch (UnsupportedEncodingException exception) {

        }

    }

    public static void signin(Context context, String login, String pin, final ResponseCallback<AuthResponse> responseHandler){
        InitialRequestParams params = new InitialRequestParams();

        JSONObject jsonUserParams = new JSONObject();
        JSONObject jsonParams = new JSONObject();

        try {
            jsonParams.put("email", login);
            jsonParams.put("password", pin);
            jsonUserParams.put("customer", jsonParams);
        } catch (JSONException ecxeption) {

        }

        try {
            StringEntity entity = new StringEntity(jsonUserParams.toString());
            Type type = new TypeToken<Response<AuthResponse>>() {}.getType();

            RestClient.getInstance().postRaw(context, "sessions", params, entity, type, responseHandler);
        } catch (UnsupportedEncodingException exception) {

        }
    }

    public static void signout(final ResponseCallback responseHandler){
        InitialRequestParams params = new InitialRequestParams();
        Type type = new TypeToken<Response>() {}.getType();
        RestClient.getInstance().delete("sessions", params, type, responseHandler);
    }

    public static void remove(final ResponseCallback responseHandler){
        InitialRequestParams params = new InitialRequestParams();
        Type type = new TypeToken<Response>() {}.getType();
        RestClient.getInstance().delete("register", params, type, responseHandler);
    }

    public static void changePassword(final ResponseCallback responseHandler){
        InitialRequestParams params = new InitialRequestParams();
        Type type = new TypeToken<Response>() {}.getType();
        RestClient.getInstance().delete("user_change_password", params, type, responseHandler);
    }

    public static void checkLogin(String login, final ResponseCallback<CheckLoginResponse> responseHandler) {
        InitialRequestParams params = new InitialRequestParams();
        Type type = new TypeToken<Response<CheckLoginResponse>>() {}.getType();
        RestClient.getInstance().setHeaderLogin(login);
        RestClient.getInstance().get("user_check_exist", params, type, responseHandler);
    }

    public static void uuidConnect(String androidId, String uuid, final ResponseCallback<HashMap<String, String>> responseHandler) {
        InitialRequestParams params = new InitialRequestParams();
        params.add("device_id", androidId);
        params.add("token", uuid);
        Type type = new TypeToken<Response<HashMap<String, String>>>() {}.getType();

        RestClient.getInstance().setHeaders(PreferencesHelper.getInstance().getLogin(), PreferencesHelper.getInstance().getAuthToken());
        RestClient.getInstance().post("uuid_connect", params, type, responseHandler);
    }

    public static void subscribeCategory(String androidId, String categoryId,  final ResponseCallback responseHandler) {
        InitialRequestParams params = new InitialRequestParams();
        Type type = new TypeToken<Response>() {}.getType();

        params.add("category_id", categoryId);
        params.add("device_id", androidId);

        RestClient.getInstance().setHeaders(PreferencesHelper.getInstance().getLogin(), PreferencesHelper.getInstance().getAuthToken());
        RestClient.getInstance().post("category_subscribe", params, type, responseHandler);
    }



}
