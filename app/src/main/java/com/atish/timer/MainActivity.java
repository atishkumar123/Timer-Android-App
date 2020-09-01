package com.atish.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import static com.atish.timer.R.*;

public class MainActivity extends AppCompatActivity {
    CountDownTimer countDownTimer;
    TextView timerTextView;
    SeekBar timerSeekbar;
    Button gobutton;
    boolean counterActive=false;
    int setprogress;



    public void finishTimer(){
        countDownTimer.cancel();
        timerSeekbar.setProgress(0);
        timerTextView.setText("FINISHED");
        gobutton.setText("START");
        counterActive=false;
    }


    public void restartTimer(){
        countDownTimer.cancel();
        timerSeekbar.setProgress(setprogress);
        gobutton.setText("START");
        counterActive=false;
    }




    public void startButton (View view) {

        if (counterActive) {
            restartTimer();
        }
        else {
            counterActive = true;
            timerSeekbar.setEnabled(false);
            gobutton.setText("stop");
            countDownTimer = new CountDownTimer(timerSeekbar.getProgress() * 1000, 1000) {

                @Override
                public void onTick(long l) {
                    updateTimer((int) l / 1000);
                    setprogress = (int) l / 1000;
                }

                @Override
                public void onFinish() {
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), raw.air);
                    mediaPlayer.start();
                    finishTimer();
                }

            }.start();

        }
    }
    public void resetButton(View view){

        counterActive = false;
        countDownTimer.cancel();
        timerSeekbar.setProgress(0);
        timerTextView.setText("00:00");
        timerSeekbar.setEnabled(true);
        gobutton.setText("START");

    }


    public void updateTimer(int secondsleft) {
        int minutes = secondsleft / 60;
        int seconds = secondsleft - (minutes * 60);
        String secondString = Integer.toString(seconds);
        if (seconds <= 9) {
            secondString = "0" + secondString;
        }

        timerTextView.setText(Integer.toString(minutes) + ":" + secondString);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
        timerTextView=(TextView)findViewById(id.timerTextView);
        gobutton=(Button)findViewById(id.timerButton);
        timerSeekbar=(SeekBar)findViewById(id.seekBar);
        timerSeekbar.setMax(600);
        timerSeekbar.setProgress(30);
        timerSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateTimer(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}