package com.stom3.android.api.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.LinkedList;

public class IndexesWoodType implements Parcelable {

    int id;
    String name;
    LinkedList<IndexesQuality> qualities;

    public IndexesWoodType(int id, String name, LinkedList<IndexesQuality> qualities) {
        this.id = id;
        this.name = name;
        this.qualities = qualities;
    }

    public IndexesWoodType(Parcel parcel) {
        this.id = parcel.readInt();
        this.name = parcel.readString();
        parcel.readTypedList(qualities, IndexesQuality.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        if(qualities != null) {
            parcel.writeTypedList(qualities);
        }
    }

    public static final Creator<IndexesWoodType> CREATOR = new Creator<IndexesWoodType>() {
        // распаковываем объект из Parcel
        public IndexesWoodType createFromParcel(Parcel in) {
            return new IndexesWoodType(in);
        }

        public IndexesWoodType[] newArray(int size) {
            return new IndexesWoodType[size];
        }
    };



    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LinkedList<IndexesQuality> getQualities() {
        return qualities;
    }

}
