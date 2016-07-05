package com.stom3.android.api;

import com.google.gson.reflect.TypeToken;
import com.stom3.android.api.response.IndexValue;
import com.stom3.android.api.response.Response;
import com.stom3.android.storage.PreferencesHelper;

import java.lang.reflect.Type;
import java.util.HashMap;

public class Indexes {

    public static void getIndexes(final ResponseCallback<HashMap<String, HashMap<String, HashMap<String, HashMap<String, HashMap<String, HashMap<String, IndexValue>>>>>>> responseHandler) {
        InitialRequestParams params = new InitialRequestParams();


        Type type = new TypeToken<Response<HashMap<String, HashMap<String, HashMap<String, HashMap<String, HashMap<String, HashMap<String,  IndexValue>>>>>>>>() {}.getType();


        RestClient.getInstance().setHeaders(PreferencesHelper.getInstance().getLogin(), PreferencesHelper.getInstance().getAuthToken());
        RestClient.getInstance().get("indexes", params, type, responseHandler);
    }
}
