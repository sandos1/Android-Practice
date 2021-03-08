package com.example.applemusicapicall;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MusicPlayer extends AppCompatActivity implements View.OnClickListener {
    public static final String MUSIC_URL = "Music Url";
    public static String ArtistName ="artistName";
    public static String songName="song Name";


    //creating UI component field
    private Button prevbtn,playbtn,nextbtn;
    private TextView leftTimeTV,rightTimeTv,songTv,artistTv;
    private SeekBar seekBar;


    private MediaPlayer mediaPlayer;
    private Handler handler;
    private Thread thread;

    //get url from API call
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        try {
            setupUI();
        } catch (IOException e) {
            e.printStackTrace();
        }

        getBundleObject();

        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    mediaPlayer.seekTo(progress);
                }
                SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");
                //getCurrentPosition() This method returns the current position of song in milliseconds
                int currentPosition = mediaPlayer.getCurrentPosition();
                int duration = mediaPlayer.getDuration();
                leftTimeTV.setText(dateFormat.format(new Date(currentPosition)));

                rightTimeTv.setText(dateFormat.format(new Date(duration-currentPosition)));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });




    }

    private void getBundleObject(){
        //get extras intent
        Bundle extras = getIntent().getExtras();

        //set artistName and songName
        String song = extras.getString(songName);
        songTv.setText(song);
        String artistName= extras.getString(ArtistName);
        artistTv.setText(artistName);

        //get the music url get from MainActivity
        url = extras.getString(MUSIC_URL);
    }


    public void setupUI() throws IOException {

        mediaPlayer = new MediaPlayer();
        // below line is use to set the audio
        // stream type for our media player.
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        //instantiating UI
        songTv=(TextView)findViewById(R.id.textView) ;
        artistTv=(TextView)findViewById(R.id.textView2) ;
        prevbtn = (Button)findViewById(R.id.prevbtn);
        playbtn = (Button)findViewById(R.id.playbtn);
        nextbtn = (Button)findViewById(R.id.nextbtn);

        prevbtn.setOnClickListener(this);
        playbtn.setOnClickListener(this);
        nextbtn.setOnClickListener(this);

        leftTimeTV =(TextView)findViewById(R.id.leftTime);
        rightTimeTv =(TextView)findViewById(R.id.rigthTime);
        seekBar =(SeekBar)findViewById(R.id.seekBar);


    }

    public void updateThread(){
        thread = new Thread(){
            @Override
            public void run(){
                try{
                    while (mediaPlayer!=null && mediaPlayer.isPlaying()){
                        Thread.sleep(50);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                int currentPosition = mediaPlayer.getCurrentPosition();
                                int newMax = mediaPlayer.getDuration();
                                seekBar.setMax(newMax);
                                seekBar.setProgress(currentPosition);

                                //update the text
                                SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");
                                leftTimeTV.setText(dateFormat.format(new Date(currentPosition)));

                                rightTimeTv.setText(dateFormat.format(new Date(newMax-currentPosition)));

                            }
                        });
                    }

                }catch (Exception e){
                    e.printStackTrace();

                }
            }


        };
        thread.start();


    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.prevbtn:{
                backMusic();
            }
            break;
            case R.id.playbtn:{
                if(mediaPlayer.isPlaying()){
                    //stop and give option to start again
                    pauseMusic();

                }else{
                    startMusic();

                }
            }
            break;
            case R.id.nextbtn:{
                nextMusic();
            }
            break;
        }
    }

    //if the media exist/playing you can pause the music and display Play button
    //to let the user know it can press play button to start the music again and show Pause btn.
    //Play button and Pause btn switch state.

    public void pauseMusic(){
        if(mediaPlayer!=null){
            mediaPlayer.pause();
            playbtn.setBackgroundResource(android.R.drawable.ic_media_play);
        }
    }

    public void startMusic(){
        if(mediaPlayer!=null){
            // below line is use to set the audio
            // stream type for our media player.
           // mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

            // below line is use to set our
            // url to our media player.
            try {
                mediaPlayer.setDataSource(url);
                // below line is use to prepare
                // and start our media player.
                mediaPlayer.prepare();
                mediaPlayer.start();

            } catch (IOException e) {
                e.printStackTrace();
            }
            updateThread();
            playbtn.setBackgroundResource(android.R.drawable.ic_media_pause);
        }
    }

    public void backMusic(){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.seekTo(0);
            //mediaPlayer.reset();
        }
    }

    public void nextMusic(){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.seekTo(seekBar.getMax());

        }
    }

    //it is important to clean up memory or purging data of mediaPlayer
    @Override
    public void onDestroy() {
        if(mediaPlayer!=null && mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer=null;
        }
        super.onDestroy();
    }



}