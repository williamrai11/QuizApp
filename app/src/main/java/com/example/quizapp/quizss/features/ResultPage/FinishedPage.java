package com.example.quizapp.quizss.features.ResultPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.quizapp.quizss.R;
import com.example.quizapp.quizss.features.quizpage.TestQuizPage;

public class FinishedPage extends AppCompatActivity implements View.OnClickListener {

    private Button easy, medium, hard;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.you_have_finished_layout);

        easy = findViewById(R.id.easy);
        medium = findViewById(R.id.medium);
        hard = findViewById(R.id.hard);

        easy.setOnClickListener(this::onClick);
        medium.setOnClickListener(this::onClick);
        hard.setOnClickListener(this::onClick);


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.easy:
                startQuiz("easy");
                break;
            case R.id.medium:
                startQuiz("medium");
                break;
            case R.id.hard:
                startQuiz("hard");
                break;

            default:

        }
    }

    void startQuiz(String level){
        Intent intent = new Intent(this,TestQuizPage.class);
        intent.putExtra("difficulty",level);
        startActivity(intent);

    }
}
