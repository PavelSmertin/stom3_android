package com.stom3.android.api.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.LinkedList;

public class IndexesMarket implements Parcelable {


    int id;
    String name;

    @SerializedName("wood_types")
    LinkedList<IndexesWoodType> woodTypes;

    public IndexesMarket(int id, String name, LinkedList<IndexesWoodType> woodTypes) {
        this.id = id;
        this.name = name;
        this.woodTypes = woodTypes;
    }

    public IndexesMarket(Parcel parcel) {
        this.id = parcel.readInt();
        this.name = parcel.readString();
        parcel.readTypedList(woodTypes, IndexesWoodType.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        if(woodTypes != null) {
            parcel.writeTypedList(woodTypes);
        }
    }

    public static final Parcelable.Creator<IndexesMarket> CREATOR = new Parcelable.Creator<IndexesMarket>() {
        // распаковываем объект из Parcel
        public IndexesMarket createFromParcel(Parcel in) {
            return new IndexesMarket(in);
        }

        public IndexesMarket[] newArray(int size) {
            return new IndexesMarket[size];
        }
    };



    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LinkedList<IndexesWoodType> getWoodTypes() {
        return woodTypes;
    }

}
