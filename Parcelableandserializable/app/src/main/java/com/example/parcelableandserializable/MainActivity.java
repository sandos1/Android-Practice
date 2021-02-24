package com.example.parcelableandserializable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button sendbtn = (Button)findViewById(R.id.sendbtn);

        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                Person person =new Person("Soro","Sandona");
                Student student = new Student("Quattara","Methan");
                Bundle bundle = new Bundle();
                bundle.putSerializable("Person",person);
                bundle.putParcelable("Student",student);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}