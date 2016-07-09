package com.stom3.android.api;

import com.google.gson.reflect.TypeToken;
import com.stom3.android.api.response.IndexesMarket;
import com.stom3.android.api.response.Response;
import com.stom3.android.storage.PreferencesHelper;

import java.lang.reflect.Type;
import java.util.LinkedList;

public class Indexes {

    public static void getIndexes(final ResponseCallback<LinkedList<IndexesMarket>> responseHandler) {
        InitialRequestParams params = new InitialRequestParams();


        Type type = new TypeToken<Response<LinkedList<IndexesMarket>>>() {}.getType();


        RestClient.getInstance().setHeaders(PreferencesHelper.getInstance().getLogin(), PreferencesHelper.getInstance().getAuthToken());
        RestClient.getInstance().get("indexes", params, type, responseHandler);
    }
}
