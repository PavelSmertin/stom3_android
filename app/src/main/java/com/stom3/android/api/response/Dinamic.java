package com.stom3.android.api.response;

import com.google.gson.annotations.SerializedName;

enum Dinamic {
    @SerializedName("up") UP(1),
    @SerializedName("down") DOWN(2),
    @SerializedName("none") NONE(3),
    UNKNOWN(4);

    private final Integer key;

    Dinamic(int key) {
        this.key = key;
    }

    public static Dinamic fromKey(int key) {
        for(Dinamic type : Dinamic.values()) {
            if(type.getKey() == key) {
                return type;
            }
        }
        return null;
    }

    public Integer getKey() {
        return this.key;
    }
}