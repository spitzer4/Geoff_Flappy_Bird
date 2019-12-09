package com.example.geoffflappybird;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.net.Inet4Address;

public class GameOverActivity extends AppCompatActivity {

    private Button restartGame;
    private TextView totalScore;
    private String score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        score = getIntent().getExtras().get("score").toString();

        restartGame = (Button) findViewById(R.id.playAgain);

        totalScore = (TextView) findViewById(R.id.totalScore);

        restartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main = new Intent(GameOverActivity.this, MainActivity.class);
                startActivity(main);
            }
        });

        totalScore.setText("Score: " + score);
    }
}
