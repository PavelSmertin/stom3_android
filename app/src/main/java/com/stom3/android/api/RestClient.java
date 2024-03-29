package com.stom3.android.api;

import android.content.Context;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.MySSLSocketFactory;
import com.loopj.android.http.SyncHttpClient;
import com.stom3.android.api.response.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.conn.scheme.Scheme;
import cz.msebera.android.httpclient.conn.ssl.SSLSocketFactory;
import cz.msebera.android.httpclient.conn.ssl.X509HostnameVerifier;
import cz.msebera.android.httpclient.entity.StringEntity;

public class RestClient {

    private static final RestClient INSTANCE = new RestClient();

    private static final String BASE_URL = "https://100m3.com/api/v1/";

    private static final String HEADER_AUTH_LOGIN = "X-User-Email";
    private static final String HEADER_AUTH_TOKEN = "X-User-Token";

    private List<OnLogoutListener> logoutListeners = new ArrayList<>();

    private AsyncHttpClient client = new AsyncHttpClient();
    public AsyncHttpClient syncHttpClient= new SyncHttpClient();

    {
        X509HostnameVerifier hostnameVerifier = MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
        SSLSocketFactory socketFactory = MySSLSocketFactory.getSocketFactory();
        socketFactory.setHostnameVerifier(hostnameVerifier);
        Scheme sch = new Scheme("https", socketFactory, 443);
        client.getHttpClient().getConnectionManager().getSchemeRegistry().register(sch);
        syncHttpClient.getHttpClient().getConnectionManager().getSchemeRegistry().register(sch);
    }

    public static RestClient getInstance() {
        return INSTANCE;
    }

    private RestClient() {

    }


    public void setHeaderLogin(String login){
        client.addHeader(HEADER_AUTH_LOGIN, login);
        client.removeHeader(HEADER_AUTH_TOKEN);
        syncHttpClient.addHeader(HEADER_AUTH_LOGIN, login);
        syncHttpClient.removeHeader(HEADER_AUTH_TOKEN);
    }

    public void setHeaders(String login, String token){
        client.addHeader(HEADER_AUTH_LOGIN, login);
        client.addHeader(HEADER_AUTH_TOKEN, token);
        syncHttpClient.addHeader(HEADER_AUTH_LOGIN, login);
        syncHttpClient.addHeader(HEADER_AUTH_TOKEN, token);
    }

    /**
     * @return an async client when calling from the main thread, otherwise a sync client.
     */
    private AsyncHttpClient getClient() {
        AsyncHttpClient cl = client;
        // Return the synchronous HTTP client when the thread is not prepared
        if (Looper.myLooper() == null) {
            cl = syncHttpClient;
        }
        return cl;
    }

    public void addOnLogoutListener(OnLogoutListener onLogoutListener) {
        if (logoutListeners.contains(onLogoutListener)) {
            return;
        }
        logoutListeners.add(onLogoutListener);
    }

    public void removeOnLogoutListener(OnLogoutListener onLogoutListener) {
        if (!logoutListeners.contains(onLogoutListener)) {
            return;
        }
        logoutListeners.remove(onLogoutListener);
    }

    private void notifyOnLogoutListeners() {
        for (OnLogoutListener listener : logoutListeners) {
            listener.onLogout();
        }
    }

    public <T> void get(String method, InitialRequestParams params, Type type, ResponseCallback<T> responseHandler) {
        getClient().get(getAbsoluteUrl(method), params, getCallback(type, responseHandler));
    }

    public <T> void post(String method, InitialRequestParams params, Type type, ResponseCallback<T> responseHandler) {
        getClient().post(getAbsoluteUrl(method), params, getCallback(type, responseHandler));
    }

    public <T> void postRaw(Context context, String method, InitialRequestParams params, StringEntity entity, Type type, ResponseCallback<T> responseHandler) {
        getClient().post(context, getAbsoluteUrl(method), entity, "application/json",  getCallback(type, responseHandler));
    }


    public <T> void put(String method, InitialRequestParams params, Type type, ResponseCallback<T> responseHandler) {
        getClient().put(getAbsoluteUrl(method), params, getCallback(type, responseHandler));
    }

    public <T> void delete(String method, InitialRequestParams params, Type type, ResponseCallback<T> responseHandler) {
        getClient().delete(getAbsoluteUrl(method), params, getCallback(type, responseHandler));
    }

    private <T> JsonHttpResponseHandler getCallback(final Type type, final ResponseCallback<T> methodCallback){
        return new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Gson gson = new Gson();
                Response<T> resp = gson.fromJson(response.toString(), type);
                methodCallback.onResponse(resp.getData());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //ErrorHelper.errorHandle(new Exception(exp));
                Gson gson = new Gson();
                Type errorType = new TypeToken<Response<HashMap<String, List<String>>>>() {}.getType();
                Response<HashMap<String, List<String>>> resp = gson.fromJson(errorResponse.toString(), errorType);

                String error = resp.getInfo();
                if (resp.getData() != null ) {
                    error = displayErrorMessage(resp.getData());
                }

                methodCallback.onError(error);
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                //ErrorHelper.errorHandle(new Exception(exp));
                Gson gson = new Gson();
                Type errorType = new TypeToken<Response<HashMap<String, List<String>>>>() {}.getType();
                Response<HashMap<String, List<String>>> resp = gson.fromJson(errorResponse.toString(), errorType);

                String error = null;
                if (resp.getData() != null ) {
                    error = displayErrorMessage(resp.getData());
                }

                methodCallback.onError(error);

                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                //ErrorHelper.errorHandle(new Exception(exp));
                methodCallback.onError(responseString);
            }
        };
    }

    private String displayErrorMessage(HashMap<String, List<String>> data) {

        StringBuilder builder = new StringBuilder();
        for (Map.Entry< String, List<String>> entry: data.entrySet()) {
            for (String errorString : entry.getValue()) {
                builder.append(errorString);
                builder.append("\n");
            }
        }

        String result = builder.toString();
        if(result.length() > 1) {
            result = result.substring(0, result.length()-1);
        }

        return result;
    }

    private String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    public interface OnLogoutListener {
        void onLogout();
    }

}