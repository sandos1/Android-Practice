package com.example.activitylifecycleandstate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {
    // Unique tag for the intent reply.
    public static final String EXTRA_REPLY =
            "com.example.android.twoactivities.extra.REPLY";
    private static final String LOG_TAG = SecondActivity.class.getSimpleName();

    // EditText for the reply.
    private EditText mReply;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Log the start of the onCreate() method.
        Log.d(LOG_TAG, "-------");
        Log.d(LOG_TAG, "onCreate Activity 2");
        // Initialize view variables.
        mReply = findViewById(R.id.editText_second);

        // Get the intent that launched this activity, and the message in
        // the intent extra.
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Put that message into the text_message TextView
        TextView textView = findViewById(R.id.text_message);
        textView.setText(message);


    }
    //Activity start in the background not visible
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart Activity 2");
    }

    //onRestart execute if user press back button to restart an Activity which was on stop
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(LOG_TAG, "onRestart Activity 2");
    }

    //onResume you can Update the UI
    //Activity A become visible
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume Activity 2");
    }


    //Activity A is OnPause when when Activity A become invisible because another Activity B is about to start
    //when this new Activity B become visible. Activity A now OnStop state.
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "onPause Activity 2");
    }

    //Activity A (UI) no longer visible if another Activity is on top of the Activity stack.
    //or Activity B is running(Visible and interactive)
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "onStop Activity 2");
    }

    //when user close the App
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy Activity 2");
    }
    public void returnReply(View view) {
        // Get the reply message from the edit text.
        String reply = mReply.getText().toString();

        // Create a new intent for the reply, add the reply message to it
        // as an extra, set the intent result, and close the activity.
        Intent replyIntent = new Intent();
        replyIntent.putExtra(EXTRA_REPLY, reply);
        //setResult send the response back to the activity that send the request
        setResult(RESULT_OK, replyIntent);
        //finish() method close the Activity. it get destroy
        finish();
    }
}