package com.example.fragmentexample2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.secondFragmentContainer,new ChoiceFragment(),"ChoiceFragment")
                .commit();


        Bundle extras=getIntent().getExtras();
        String choice = extras.getString("choice");
        Log.d("TAG", "onCreate: "+choice);
        //pass data to the target Fragment
        ChoiceFragment.setChoice(choice);




        //ChoiceFragment.setBundle();





    }
}