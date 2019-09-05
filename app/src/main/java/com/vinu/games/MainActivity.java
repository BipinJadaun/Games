package com.vinu.games;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void playConnect3(View view){
        Intent intent = new Intent(getApplicationContext(), Connect3Activity.class);
        startActivity(intent);
    }

    public void playGuessWho(View view){
        Intent intent = new Intent(getApplicationContext(), GuessWhoActivity.class);
        startActivity(intent);
    }
}
