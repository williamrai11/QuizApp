package com.example.quizapp.quizss.features.quizpage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizapp.quizss.R;
import com.example.quizapp.quizss.data.local.model.InsertOneCsv;
import com.example.quizapp.quizss.data.local.model.QueryOneCsv;
import com.example.quizapp.quizss.data.local.providers.DbDao;
import com.example.quizapp.quizss.features.ResultPage.FinishedPage;
import com.example.quizapp.quizss.features.ResultPage.Result;
import com.example.quizapp.quizss.util.LogUtil;
import com.example.quizapp.quizss.util.ToastUtil;

import java.util.List;

public class TestQuizPage extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "test";

    private TextView txtCurrentQuestions,
            txtCorrectAnswer;
    private TextView questions;
    private Button optionA, optionB;
    private DbDao dbDao;

    private SharedPreferences counterPref, catPref, trackerPref;
    private android.content.SharedPreferences.Editor counterEditor, trackerEditor;
    private String categoryName;

    private List<QueryOneCsv> queryOneCsvsLists;
    private int counter; //for question stat
    private int correctCounter; //counter for correct answer
    private int questionCounter; //for questions
    private String difficulty;
    private int questionsCount; //total question count
    private long partCounter; //after finished a part counter


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_layout_two);

        catPref = getSharedPreferences("categoryName", 0);
        counterPref = getSharedPreferences("counter", 0);
        trackerPref = getSharedPreferences("tracker", 0);

        categoryName = catPref.getString("categoryName", null);

        difficulty = getIntent().getStringExtra("difficulty");

        txtCurrentQuestions = findViewById(R.id.txt_current_questions);
        txtCorrectAnswer = findViewById(R.id.txt_correct_answer);
        questions = findViewById(R.id.Questions);
        optionA = findViewById(R.id.OptionA);
        optionB = findViewById(R.id.OptionB);

        dbDao = new DbDao(this);

        //this sharedPref value helps in going to next part of quiz
        //Here counter is the difficulty's part
        //Update this when part is finished
        partCounter = counterPref.getLong("part_counter_"+categoryName + "_" + difficulty, 1);

        //gets AllDatas of a category according to difficulty
        queryOneCsvsLists = dbDao.getAllDatas(categoryName, difficulty,partCounter);

        Log.d(TAG, "onCreate: "+difficulty);


        LogUtil.dLog(TAG+" asd", String.valueOf(queryOneCsvsLists.size()));

        //gets the total count of questions in the db acc to category, difficulty and part
        questionsCount = dbDao.getCountOfQuestions(categoryName, difficulty, partCounter);
        int totalPart = dbDao.getPart(categoryName,difficulty);

        questionCounter = trackerPref.getInt("question_"+categoryName+"_"+difficulty+"_"+partCounter,0);
        correctCounter = trackerPref.getInt("answer_"+categoryName+"_"+difficulty+"_"+partCounter,0);
        counter = trackerPref.getInt("counter",1);


        LogUtil.dLog(TAG, String.valueOf(categoryName+ " "+difficulty+" __ "+queryOneCsvsLists.size()+"__"+questionCounter+" "+correctCounter+" "+counter+"_"+totalPart+" "+partCounter));

        if (partCounter > totalPart){
            startActivity(new Intent(this,FinishedPage.class));
        }

        try {
            //default questions
            questions.setText(queryOneCsvsLists.get(questionCounter).getQuestion());
            optionA.setText(queryOneCsvsLists.get(questionCounter).getChoice_one());
            optionB.setText(queryOneCsvsLists.get(questionCounter).getChoice_two());

            txtCurrentQuestions.setText("Que " + counter + "/" + questionsCount);
            txtCorrectAnswer.setText("Ans " + correctCounter + "/" + questionsCount);
        }catch (Exception e){
            LogUtil.dLog(TAG,e.getMessage());
        }
        ToastUtil.shortToast(this, difficulty);

    }

    //Click Events of the Choices in the Quiz
    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.OptionA:
                checkAnswer(0);

                break;

            case R.id.OptionB:
                checkAnswer(1);
                break;

            default:
                ToastUtil.shortToast(getApplicationContext(), "Not Clicked");
        }
    }


    private void checkAnswer(int id) {
        if (id == 0) {
            if (optionA.getText().toString().equals(queryOneCsvsLists.get(questionCounter).getAnswer())) {
                createDialog(R.layout.explain_dialog, queryOneCsvsLists.get(questionCounter).getExplanation(), 0);
                counter++;
                correctCounter++;
                questionCounter++;
                ToastUtil.shortToast(getApplicationContext(), "Option A Correct");
            } else {
                createDialog(R.layout.wrong_dialog, queryOneCsvsLists.get(questionCounter).getExplanation(), 1);
                counter++;
                questionCounter++;
                ToastUtil.shortToast(getApplicationContext(), "Option A InCorrect");

            }

        } else {
            if (optionB.getText().toString().equals(queryOneCsvsLists.get(questionCounter).getAnswer())) {
                createDialog(R.layout.explain_dialog, queryOneCsvsLists.get(questionCounter).getExplanation(), 0);
                ToastUtil.shortToast(getApplicationContext(), "Option B Correct");

                questionCounter++;
                correctCounter++;
                counter++;


            } else {
                createDialog(R.layout.wrong_dialog, queryOneCsvsLists.get(questionCounter).getExplanation(), 1);
                ToastUtil.shortToast(getApplicationContext(), "Option B InCorrect");

                questionCounter++;
                counter++;

            }

        }

    }

    //Dialog when Correct and inCorrect Answer
    private void createDialog(int layout, String explanation, final int id) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(layout, null);
        TextView txtExplainView = view.findViewById(R.id.txt_explaination);
        Button btnContinue = view.findViewById(R.id.btn_continue);

        txtExplainView.setText(explanation);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setLayout((6 * width) / 7, (3 * height) / 6);


        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Correct Answer", Toast.LENGTH_SHORT).show();
                trackerEditor = trackerPref.edit();

                if (questionsCount == questionCounter) {
                    counterEditor = counterPref.edit();
                    trackerEditor = trackerPref.edit();

                    trackerEditor.putInt("question_"+categoryName + "_" + difficulty+"_"+partCounter,0);
                    trackerEditor.putInt("answer_"+categoryName + "_" + difficulty+"_"+partCounter,0);
                    trackerEditor.putInt("counter",1);
                    trackerEditor.apply();


                    Intent intent = new Intent(TestQuizPage.this, Result.class);
                    intent.putExtra("score", correctCounter);
                    intent.putExtra("incorrectAnswer", questionsCount - correctCounter);
                    intent.putExtra("categoryName", categoryName);
                    intent.putExtra("difficulty", difficulty);
                    intent.putExtra("part",partCounter);

                    partCounter++;
                    counterEditor.putLong("part_counter_"+categoryName+"_"+difficulty,partCounter);
                    counterEditor.apply();


                    startActivity(intent);

                    ToastUtil.shortToast(getApplicationContext(), "Open Activity");

                } else {
                    if (id == 0) {
                        questions.setText(queryOneCsvsLists.get(questionCounter).getQuestion());
                        optionA.setText(queryOneCsvsLists.get(questionCounter).getChoice_one());
                        optionB.setText(queryOneCsvsLists.get(questionCounter).getChoice_two());
                        txtCurrentQuestions.setText("Que " + (counter) + "/" + questionsCount);
                        txtCorrectAnswer.setText("Correct " + (correctCounter) + "/" + questionsCount);

                        //Saves the current questions and answers
                        trackerEditor.putInt("question_"+categoryName + "_" + difficulty+"_"+partCounter,questionCounter);
                        trackerEditor.putInt("answer_"+categoryName + "_" + difficulty+"_"+partCounter,correctCounter);
                        trackerEditor.putInt("counter",counter);
                        trackerEditor.apply();

                    } else {
                        questions.setText(queryOneCsvsLists.get(questionCounter).getQuestion());
                        optionA.setText(queryOneCsvsLists.get(questionCounter).getChoice_one());
                        optionB.setText(queryOneCsvsLists.get(questionCounter).getChoice_two());
                        txtCurrentQuestions.setText("Que " + (counter) + "/" + questionsCount);

                        //Saves the current questions and answers
                        trackerEditor.putInt("question_"+categoryName + "_" + difficulty+"_"+partCounter,questionCounter);
                        trackerEditor.putInt("answer_"+categoryName + "_" + difficulty+"_"+partCounter,correctCounter);
                        trackerEditor.putInt("counter",counter);
                        trackerEditor.apply();
                    }

                }

                dialog.dismiss();

            }
        });


    }


}
