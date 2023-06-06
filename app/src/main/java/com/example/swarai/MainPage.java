package com.example.swarai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.VideoView;

public class MainPage extends AppCompatActivity {

    ImageView anuvad, tts, shrutlekh, gender, noice12;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        anuvad = findViewById(R.id.anuvad);
        tts = findViewById(R.id.tts);
        shrutlekh = findViewById(R.id.shrutlekh);
        gender = findViewById(R.id.gender);
        noice12 = findViewById(R.id.noice12);


        anuvad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainPage.this, Anuvad_Activity.class));
            }
        });

        tts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainPage.this, PaatParivartak_Activity.class));
            }
        });

        shrutlekh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainPage.this, Shrutlekh_Activity.class));
            }
        });

        gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainPage.this, Gender_Activity.class));
            }
        });

        noice12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainPage.this, NoiseCancellation_Activity.class));
            }
        });


//        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.back1);
//        videoView.setVideoURI(uri);
//        videoView.start();
//
//        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mediaPlayer) {
//                mediaPlayer.setLooping(true);
//
//            }
//        });
//
//    }
//    @Override
//    protected void onResume() {
//        videoView.resume();
//        super.onResume();
//    }
//
//    @Override
//    protected void onRestart() {
//        videoView.start();
//        super.onRestart();
//    }
//
//    @Override
//    protected void onPause() {
//        videoView.suspend();
//        super.onPause();
//    }
//
//    @Override
//    protected void onDestroy() {
//        videoView.stopPlayback();
//        super.onDestroy();
//    }
    }
}