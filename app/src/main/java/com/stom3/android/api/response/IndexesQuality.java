package com.stom3.android.api.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.LinkedList;

public class IndexesQuality implements Parcelable {

    int id;
    String name;
    LinkedList<IndexesLength> lengths;

    public IndexesQuality(int id, String name, LinkedList<IndexesLength> lengths) {
        this.id = id;
        this.name = name;
        this.lengths = lengths;
    }

    public IndexesQuality(Parcel parcel) {
        this.id = parcel.readInt();
        this.name = parcel.readString();
        if(lengths != null) {
            parcel.readTypedList(lengths, IndexesLength.CREATOR);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        if(lengths != null) {
            parcel.writeTypedList(lengths);
        }
    }

    public static final Creator<IndexesQuality> CREATOR = new Creator<IndexesQuality>() {
        // распаковываем объект из Parcel
        public IndexesQuality createFromParcel(Parcel in) {
            return new IndexesQuality(in);
        }

        public IndexesQuality[] newArray(int size) {
            return new IndexesQuality[size];
        }
    };


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LinkedList<IndexesLength> getLengths() {
        return lengths;
    }

}
