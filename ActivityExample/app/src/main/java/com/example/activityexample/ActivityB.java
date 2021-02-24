package com.example.activityexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import static com.example.activityexample.feature1.MainActivity.LIFECYCLE_LOGS;
import static com.example.activityexample.feature1.MainActivity.NAME_TAG;

public class ActivityB extends AppCompatActivity {
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);

        Log.v(LIFECYCLE_LOGS, "Activity B is created");
        mButton = findViewById(R.id.finish_button);

        //I can retrieve the Intent I get from Main activity here if I want
        Intent intentRecieved = getIntent();

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(LIFECYCLE_LOGS, "Finish is called");

                //here i can set the result to whatever; by default, a
                //result is set to OK; but I can manually set it also
                setResult(RESULT_OK);
                if (intentRecieved!= null) {
                    intentRecieved.putExtra(NAME_TAG, "Alex");
                }


                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v(LIFECYCLE_LOGS, "Activity B is started");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(LIFECYCLE_LOGS, "Activity B onResumed");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(LIFECYCLE_LOGS, "Activity B Paused");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v(LIFECYCLE_LOGS, "Activity B Stopped");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(LIFECYCLE_LOGS, "ACtiviyt  B Destroyed");
    }
}