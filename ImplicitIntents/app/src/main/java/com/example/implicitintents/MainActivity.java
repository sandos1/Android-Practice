package com.example.implicitintents;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mWebsiteEditText;
    private EditText mLocationEditText;
    private EditText mShareTextEditText;

    private Button webbtn,locationbtn,shareTxtbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWebsiteEditText = (EditText)findViewById(R.id.website_edittext) ;
        mLocationEditText =(EditText) findViewById(R.id.location_edittext);
        mShareTextEditText = (EditText)findViewById(R.id.share_edittext);

        webbtn =(Button)findViewById(R.id.open_website_button);
        locationbtn =(Button)findViewById(R.id.open_location_button);
        shareTxtbtn =(Button)findViewById(R.id.share_text_button);

        webbtn.setOnClickListener(this);
        locationbtn.setOnClickListener(this);
        shareTxtbtn.setOnClickListener(this);


    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.open_website_button:{
                // Get the URL text.
                String url = mWebsiteEditText.getText().toString();

                // Parse the URI and create the intent.
                Uri webpage = Uri.parse(url);

                //ACTION_EDIT (to edit the given data), or ACTION_DIAL (to dial a phone number).
                // Intent.ACTION_VIEW send Action to display a webpage
                //implicit Intent you specify an action and the data for that action.
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

                //after Action is send the android system start an intent resolution to find or filter
                //all component that can respond or receive the intent acttion
                //if there are multiple component android display a list from which a user can choose

                // Find an activity to hand the intent and start that activity.
                //Use the resolveActivity() method and the Android package manager to find an Activity
                // that can handle your implicit Intent. Make sure that the request resolved successfully.
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Log.d("ImplicitIntents", "Can't handle this intent!");
                }
            }

                break;
            case R.id.open_location_button:{
                // Get the string indicating a location. Input is not validated; it is
                // passed to the location handler intact.
                String loc = mLocationEditText.getText().toString();

                // Parse the location and create the intent.
                Uri addressUri = Uri.parse("geo:0,0?q=" + loc);
                Intent intent = new Intent(Intent.ACTION_VIEW, addressUri);
                // Find an activity to handle the intent, and start that activity.
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Log.d("ImplicitIntents", "Can't handle this intent!");
                }
            }

                break;
            case R.id.share_text_button:{
                //text/message to share
                String txt = mShareTextEditText.getText().toString();
                //type of data to share
                String mimeType = "text/plain";

                //ShareCompat.IntentBuilder helper class to make implementing sharing easy. You can use ShareCompat.IntentBuilder
                // to build an Intent and launch a chooser to let the user choose the destination app for sharing.
                ShareCompat.IntentBuilder
                        .from(this)     //The Activity that launches this share Intent (this).
                        .setType(mimeType)   //The MIME type of the item to be shared.
                        .setChooserTitle(R.string.share_text_with) //The title that appears on the system app chooser.
                        .setText(txt)  //The actual text to be shared
                        .startChooser();  //Show the system app chooser and send the Intent.
            }

                break;
        }
    }
}