package com.example.parcelableandserializable;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * The order in which you write these values is important.
 * When collecting these values later you will need to collect them in the same order.
 * If you’re going to send boolean values (for example the featured property).
 * You will have to use writeValue and then force cast it to a boolean on the other side as
 * there is no native method for adding booleans.
 * eg:  featured = (Boolean) parcel.readValue(null);
 */
public class Student implements Parcelable {

    //instance variable of the class
    private String lastName;
    private String firstName;

    //constructor
    public Student(String lastName,String firstName){
        this.lastName=lastName;
        this.firstName=firstName;
    }

    //constructor used for parcel
    //This constructor is where you collect the values and set up the properties of the object:
    protected Student(Parcel in) {
        //deserializing instance (read) and set properties
        //read and set saved values from parcel
        lastName = in.readString();
        firstName = in.readString();
    }

    //creator - used when un-parceling our parcel (creating the object)
    //creator is like deserializing(un_parceling) the object parcel(serialize)
    //Parcelable requires this method to bind everything together. There’s little you need to do here
    // as the createFromParcel method will return your newly populated object.
    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    //write object values to parcel for storage
    //saving object variable to to parcel object
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //write all properties to the parcel
        dest.writeString(lastName);
        dest.writeString(firstName);
    }
}
