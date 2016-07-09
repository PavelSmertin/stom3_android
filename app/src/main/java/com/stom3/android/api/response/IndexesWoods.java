package com.stom3.android.api.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.LinkedList;

public class IndexesWoods implements Parcelable {

    int id;
    String name;
    LinkedList<IndexValue> sizes;

    public IndexesWoods(int id, String name, LinkedList<IndexValue> values) {
        this.id = id;
        this.name = name;
        this.sizes = values;
    }

    public IndexesWoods(Parcel parcel) {
        this.id = parcel.readInt();
        this.name = parcel.readString();
        parcel.readTypedList(sizes, IndexValue.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        if(sizes != null) {
            parcel.writeTypedList(sizes);
        }
    }

    public static final Creator<IndexesWoods> CREATOR = new Creator<IndexesWoods>() {
        // распаковываем объект из Parcel
        public IndexesWoods createFromParcel(Parcel in) {
            return new IndexesWoods(in);
        }

        public IndexesWoods[] newArray(int size) {
            return new IndexesWoods[size];
        }
    };


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LinkedList<IndexValue> getValues() {
        return sizes;
    }

}
