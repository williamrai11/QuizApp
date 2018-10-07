package com.example.quizapp.quizss.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizapp.quizss.R;

public class ScoreCard extends AppCompatActivity {
    TextView a1, a2, a3, a4, a5, a6, a7, a8, a9, a10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_card);
        getSupportActionBar().setTitle("Scores");

        SharedPreferences sharedPreferences = getSharedPreferences("score", Context.MODE_PRIVATE);
        a1 = (TextView) findViewById(R.id.computer);
        a2 = (TextView) findViewById(R.id.sports);
        a3 = (TextView) findViewById(R.id.inventions);
        a4 = (TextView) findViewById(R.id.general);
        a5 = (TextView) findViewById(R.id.science);
        try {
            a1.setText("" + sharedPreferences.getInt("General", 0));
            a2.setText("" + sharedPreferences.getInt("History", 0));
            a3.setText("" + sharedPreferences.getInt("Culture", 0));
            a4.setText("" + sharedPreferences.getInt("Nature", 0));
            a5.setText("" + sharedPreferences.getInt("Science", 0));

        } catch (Exception e) {
            Toast.makeText(ScoreCard.this, "" + e, Toast.LENGTH_SHORT).show();
        }
    }

}
