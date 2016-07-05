package com.stom3.android.api.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

public class WoodTypeIndexes implements Parcelable {

    String name;
    HashMap<String, HashMap<String, HashMap<String, HashMap<String, IndexValue>>>> indexes;

    public WoodTypeIndexes(String name, HashMap<String, HashMap<String, HashMap<String, HashMap<String, IndexValue>>>> indexes) {
        this.name = name;
        this.indexes = indexes;
    }

    public WoodTypeIndexes(Parcel parcel) {
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

    public static final Creator<WoodTypeIndexes> CREATOR = new Creator<WoodTypeIndexes>() {
        // распаковываем объект из Parcel
        public WoodTypeIndexes createFromParcel(Parcel in) {
            return new WoodTypeIndexes(in);
        }

        public WoodTypeIndexes[] newArray(int size) {
            return new WoodTypeIndexes[size];
        }
    };


    public String getName() {
        return name;
    }

    public HashMap<String, HashMap<String, HashMap<String, HashMap<String, IndexValue>>>> getIndexes() {
        return indexes;
    }

}
