package com.stom3.android.api.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class IndexValue implements Parcelable{




    @SerializedName("c")
    private String categoryId;

    @SerializedName("s")
    private String size;

    @SerializedName("v")
    private int value;

    @SerializedName("d")
    private Dinamic dinamic;

    public IndexValue(Parcel parcel) {
        this.categoryId = parcel.readString();
        this.size = parcel.readString();
        this.value = parcel.readInt();
        this.dinamic = Dinamic.fromKey(parcel.readInt());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(categoryId);
        parcel.writeString(size);
        parcel.writeInt(value);
        if(dinamic != null) {
            parcel.writeInt(dinamic.getKey());
        }
    }


    public static final Parcelable.Creator<IndexValue> CREATOR = new Parcelable.Creator<IndexValue>() {
        // распаковываем объект из Parcel
        public IndexValue createFromParcel(Parcel in) {
            return new IndexValue(in);
        }

        public IndexValue[] newArray(int size) {
            return new IndexValue[size];
        }
    };



    public String getCategoryId() {
        return categoryId;
    }

    public String getSize() {
        return size;
    }

    public int getValue() {
        return value;
    }

    public Dinamic getDinamic() {
        return dinamic;
    }




}
