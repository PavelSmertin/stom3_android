package com.stom3.android.api.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.LinkedList;

public class IndexesLength implements Parcelable {

    int id;
    String name;
    LinkedList<IndexesWoods> woods;

    public IndexesLength(int id, String name, LinkedList<IndexesWoods> lengths) {
        this.id = id;
        this.name = name;
        this.woods = lengths;
    }

    public IndexesLength(Parcel parcel) {
        this.id = parcel.readInt();
        this.name = parcel.readString();
        parcel.readTypedList(woods, IndexesWoods.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        if(woods != null) {
            parcel.writeTypedList(woods);
        }
    }

    public static final Creator<IndexesLength> CREATOR = new Creator<IndexesLength>() {
        // распаковываем объект из Parcel
        public IndexesLength createFromParcel(Parcel in) {
            return new IndexesLength(in);
        }

        public IndexesLength[] newArray(int size) {
            return new IndexesLength[size];
        }
    };


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LinkedList<IndexesWoods> getWoods() {
        return woods;
    }

}
