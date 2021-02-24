package com.example.activityexample;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

//if you ever want to pass an object of a class through a Bundle or Intent
//you MUST implement Parcelable (or Serializable)
//generally implemented any time you want to convert an object to a bytestream

public class RandomClass implements Parcelable {
    private String name;
    public RandomClass(String name) {
        this.name = name;
    }

    protected RandomClass(Parcel in) {
        name = in.readString();
    }

    public static final Creator<RandomClass> CREATOR = new Creator<RandomClass>() {
        @Override
        public RandomClass createFromParcel(Parcel in) {
            return new RandomClass(in);
        }

        @Override
        public RandomClass[] newArray(int size) {
            return new RandomClass[size];
        }
    };

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }
}
