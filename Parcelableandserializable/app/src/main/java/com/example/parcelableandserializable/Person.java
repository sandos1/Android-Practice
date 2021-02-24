package com.example.parcelableandserializable;

import java.io.Serializable;

public class Person implements Serializable {

    private String lastName;
    private String firstName;

    public Person(String lastName,String firstName){
        this.lastName=lastName;
        this.firstName=firstName;
    }

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
}
