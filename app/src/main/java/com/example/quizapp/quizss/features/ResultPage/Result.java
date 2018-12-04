package com.example.quizapp.quizss.features.ResultPage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.quizapp.quizss.features.quizpage.TestQuizPage;
import com.example.quizapp.quizss.ui.MainActivity;
import com.example.quizapp.quizss.R;
import com.example.quizapp.quizss.data.local.providers.DbDao;
import com.example.quizapp.quizss.data.local.model.MarksModel;
import com.example.quizapp.quizss.features.quizpage.QuizPage;
import com.example.quizapp.quizss.util.DateUtil;

public class Result extends AppCompatActivity {

    private int incorrectAnswer;
    private TextView correct, incorrect, attempted, score, remarks;
    private String TAG = "result";
    int cor = 0, scor = 0, idTracker = 0;
    private ImageButton btn_again, btn_exit;
    private SharedPreferences preferences, scorePref;
    private SharedPreferences.Editor scoreEditor;
    private String categoryName, difficulty;
    private long part;

    private DbDao dbDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results_two);

        dbDao = new DbDao(this);

        preferences = getSharedPreferences("tracker", 0);
        scorePref = getSharedPreferences("score", 0);
        scoreEditor = scorePref.edit();

        idTracker = preferences.getInt("trackerId", 0);
        cor = getIntent().getIntExtra("score", 0);
        incorrectAnswer = getIntent().getIntExtra("incorrectAnswer", 0);
        categoryName = getIntent().getStringExtra("categoryName");
        difficulty = getIntent().getStringExtra("difficulty");
        part = getIntent().getLongExtra("part", 1);


        scor = 10 * cor;
        correct = findViewById(R.id.correct);
        incorrect = findViewById(R.id.incorrect);
        score = findViewById(R.id.score);
        remarks = findViewById(R.id.remarks);
        btn_again = findViewById(R.id.play_again);
        btn_exit = findViewById(R.id.exit);

        correct.setText("  " + cor);
        incorrect.setText("  " + incorrectAnswer);
        score.setText(String.valueOf(scor));
        float x1 = (cor * 100) / 5;
        if (x1 < 20)
            remarks.setText("You Need Improvement");
        else if (x1 < 28)
            remarks.setText("You are an average Quizzer");
        else if (x1 < 48)
            remarks.setText("You are an above average Quizzer ");
        else
            remarks.setText("You are a brilliant  Quizzer ");


        scoreEditor.putInt(categoryName, scor);
        scoreEditor.apply();


        MarksModel marksModel = new MarksModel(scor, incorrectAnswer, categoryName, DateUtil.getCurrentDate(), difficulty, part);
        dbDao.insert(marksModel);


        btn_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Result.this, TestQuizPage.class);
                intent.putExtra("categoryName", categoryName);
                intent.putExtra("difficulty", difficulty);
                startActivity(intent);

            }
        });

        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Result.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }
}
