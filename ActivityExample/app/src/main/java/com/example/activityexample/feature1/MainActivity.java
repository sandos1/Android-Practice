package com.example.activityexample.feature1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.activityexample.ActivityB;
import com.example.activityexample.R;
import com.example.activityexample.RandomClass;

public class MainActivity extends AppCompatActivity {

    public static final String LIFECYCLE_LOGS = "LIFECYCLE_LOGS";
    public int START_ACTIVITY_B_REQUEST = 1000;
    public int START_OTHER_ACTIVITY = 2000;
    public static final String NAME_TAG = "NAME_TAG";
    private TextView mHelloText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.v(LIFECYCLE_LOGS, "Activity is created");

        mHelloText = (TextView) findViewById(R.id.hello_text);

        /**
         * EXAMPLE FOR STARTING AN ACTIVITY TO OBTAIN A RESULT
         *
         * @param v
         */
        mHelloText.setOnClickListener(new View.OnClickListener() {

            //FIRST I want to start an activity; easiest way to do that is on a
            //button click
            @Override
            public void onClick(View v) {
                //I start activity B here
                Log.d("text_click", "is it clicked?");
                //when creating an intent, you NEED the argument Context.
                //the class argument is optional
                Intent intent = new Intent(MainActivity.this, ActivityB.class);

                //say I want to create a RandomClass object, and want to pass it to actitvty B:
                RandomClass randomObject = new RandomClass("name");

                intent.putExtra("object_tag", randomObject);

//                intent.setAction()
//                startActivityForResult(intent, START_ACTIVITY_B_REQUEST);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v(LIFECYCLE_LOGS, "Activity A is started");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(LIFECYCLE_LOGS, "Activity A onResumed");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(LIFECYCLE_LOGS, "Activity A Paused");

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        //bundle is used to save data within an activity
        outState.putString(NAME_TAG, "ABEDUR");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v(LIFECYCLE_LOGS, "Activity A Stopped");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(LIFECYCLE_LOGS, "ACtiviyt A Destroyed");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==START_ACTIVITY_B_REQUEST) {
            if (resultCode == RESULT_OK) {
                if (data !=null) {
                    String example = data.getStringExtra(NAME_TAG);
                    Log.v("name_example", example);
                }
            }
        }
    }
}