package com.vinu.games;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.logging.Logger;

public class TimerActivity extends AppCompatActivity {

    SeekBar seekBar;
    TextView timer;
    Button controllerButton;
    boolean timerActive = false;
    CountDownTimer countDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        seekBar = findViewById(R.id.timerSeekBar);
        timer = findViewById(R.id.timer);
        controllerButton = findViewById(R.id.controllerButton);
        seekBar.setMax(10 * 60);
        seekBar.setProgress(0);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                startTimer(i);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
    }
     public void timerController(View view){
        if(timerActive == false) {
            timerActive = true;
            seekBar.setEnabled(false);
            controllerButton.setText("Stop");

            countDown = new CountDownTimer(seekBar.getProgress() * 1000 + 100, 1000) {
                @Override
                public void onTick(long l)
                {
                    startTimer((int) l / 1000);
                }

                @Override
                public void onFinish() {
                    stopTimer();
                }
            }.start();
        }else{
            stopTimer();
        }
     }


    private void startTimer(int  time){
        int min = time / 60;
        int sec = time % 60;
        String seconds  = (sec <=9) ? "0"+sec : Integer.toString(sec);
        timer.setText(min + ":" + seconds);
        seekBar.setProgress(time);
    }

    private void stopTimer(){
        countDown.cancel();
        timerActive = false;
        seekBar.setEnabled(true);
        seekBar.setProgress(0);
        controllerButton.setText("Go!");
        timer.setText("0:00");

    }
}
