package com.stom3.android.api.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

public class MarketIndexes implements Parcelable {

    String name;
    HashMap<String, HashMap<String, HashMap<String, HashMap<String, HashMap<String, IndexValue>>>>> indexes;

    public MarketIndexes(String name, HashMap<String, HashMap<String, HashMap<String, HashMap<String, HashMap<String, IndexValue>>>>> indexes) {
        this.name = name;
        this.indexes = indexes;
    }

    public MarketIndexes(Parcel parcel) {
        this.name = parcel.readString();
        this.indexes = parcel.readHashMap(HashMap.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        if(indexes != null) {
            parcel.writeMap(indexes);
        }
    }

    public static final Parcelable.Creator<MarketIndexes> CREATOR = new Parcelable.Creator<MarketIndexes>() {
        // распаковываем объект из Parcel
        public MarketIndexes createFromParcel(Parcel in) {
            return new MarketIndexes(in);
        }

        public MarketIndexes[] newArray(int size) {
            return new MarketIndexes[size];
        }
    };


    public String getName() {
        return name;
    }

    public HashMap<String, HashMap<String, HashMap<String, HashMap<String, HashMap<String, IndexValue>>>>> getIndexes() {
        return indexes;
    }

}
