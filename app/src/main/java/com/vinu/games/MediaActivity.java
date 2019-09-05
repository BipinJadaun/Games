package com.vinu.games;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

public class MediaActivity extends AppCompatActivity {

    MediaPlayer mPlayer;
    AudioManager systemSound;
    SeekBar volumeController;
    SeekBar mediaController;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);
        volumeController = findViewById(R.id.volumeControl);
        mediaController = findViewById(R.id.mediaControl);
        progressBar = findViewById(R.id.mediaProgressBar);

        mPlayer = MediaPlayer.create(this, R.raw.ringtone);
        systemSound = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        int maxVolume = systemSound.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentVolume = systemSound.getStreamVolume(AudioManager.STREAM_MUSIC);

        volumeController.setMax(maxVolume);
        volumeController.setProgress(currentVolume);

        volumeController.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                systemSound.setStreamVolume(AudioManager.STREAM_MUSIC, i, 0);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        mediaController.setMax(mPlayer.getDuration());
        /*new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                mediaController.set
            }
        },0, 10);*/

        mediaController.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mPlayer.seekTo(i);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        progressBar.setMax(mPlayer.getDuration());

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                progressBar.setProgress(mPlayer.getCurrentPosition());
            }
        },0, 100);



    }

    public void play(View view) {
        mPlayer = MediaPlayer.create(this, R.raw.ringtone);
        mPlayer.start();
    }

    public void pause(View view) {
        mPlayer.pause();
    }

    public void stop(View view) {
        mPlayer.stop();
        mediaController.setProgress(0);
    }
}
