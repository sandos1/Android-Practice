package com.example.parcelableandserializable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();
        Person person = (Person)intent.getSerializableExtra("Person");
        Student student = (Student)intent.getParcelableExtra("Student");
        Log.d("TAG", person.getFirstName()+" "+person.getLastName());
        Log.d("TAG", student.getFirstName()+" "+student.getLastName());
    }
}