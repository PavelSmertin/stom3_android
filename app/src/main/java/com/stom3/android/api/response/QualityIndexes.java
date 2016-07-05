package com.stom3.android.api.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

public class QualityIndexes implements Parcelable {

    String name;
    HashMap<String, HashMap<String, HashMap<String, IndexValue>>> indexes;

    public QualityIndexes(String name, HashMap<String, HashMap<String, HashMap<String, IndexValue>>> indexes) {
        this.name = name;
        this.indexes = indexes;
    }

    public QualityIndexes(Parcel parcel) {
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

    public static final Creator<QualityIndexes> CREATOR = new Creator<QualityIndexes>() {
        // распаковываем объект из Parcel
        public QualityIndexes createFromParcel(Parcel in) {
            return new QualityIndexes(in);
        }

        public QualityIndexes[] newArray(int size) {
            return new QualityIndexes[size];
        }
    };


    public String getName() {
        return name;
    }

    public HashMap<String, HashMap<String, HashMap<String, IndexValue>>> getIndexes() {
        return indexes;
    }

}
