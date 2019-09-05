package com.vinu.games;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Connect3Activity extends AppCompatActivity {

    boolean gameActive = true;
    int activePlayer = 0;
    int[] gameState = {2,2,2,2,2,2,2,2,2};
    int[][] winingPos = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};


    public void clickIn(View view){
        ImageView clickedShell = (ImageView) view;
        int clickedShellNo = Integer.parseInt(clickedShell.getTag().toString());

        if(gameState[clickedShellNo] == 2 && gameActive){
            gameState[clickedShellNo] = activePlayer;

            if(activePlayer == 0){
                activePlayer = 1;
                clickedShell.setImageResource(R.drawable.circle);
            }else if(activePlayer == 1){
                activePlayer = 0;
                clickedShell.setImageResource(R.drawable.cross);
            }

            for(int[] win : winingPos){
                if(gameState[win[0]] == gameState[win[1]] && gameState[win[0]] == gameState[win[2]] && gameState[win[0]] != 2){
                    gameActive = false;
                    String winner = "Circles";
                    if(gameState[win[0]] == 1){
                        winner = "Crosses";
                    }
                    RelativeLayout layout = findViewById(R.id.resultLayout);
                    TextView message = findViewById(R.id.resultMessage);
                    message.setText(" Congratulations! " + winner +" Won.");
                    message.setTextColor(Color.GREEN);
                    layout.setVisibility(View.VISIBLE);
                }
                else{
                    boolean gameOver = true;
                    for(int state: gameState){
                        if(state == 2) {
                            gameOver = false;
                            break;
                        }
                    }
                    if(gameOver){
                        RelativeLayout layout = findViewById(R.id.resultLayout);
                        TextView message = findViewById(R.id.resultMessage);
                        message.setText(" Game Over!");
                        message.setTextColor(Color.RED);
                        layout.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }

    public void playAgain(View view){
        gameActive = true;
        GridLayout gridLayout = findViewById(R.id.gameLayout);
        RelativeLayout layout = findViewById(R.id.resultLayout);
        for(int i=0;i<9;i++){
            gameState[i] = 2;
            ((ImageView)gridLayout.getChildAt(i)).setImageResource(0);
        }
        layout.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect3);
    }

    public void backToHome(View view){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
