package com.example.quizapp.quizss.features.quizpage;


import android.content.Intent;
import android.content.SharedPreferences;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizapp.quizss.R;
import com.example.quizapp.quizss.data.local.providers.DbDao;
import com.example.quizapp.quizss.data.local.model.InsertOneCsv;
import com.example.quizapp.quizss.data.local.model.QueryOneCsv;
import com.example.quizapp.quizss.features.ResultPage.Result;
import com.example.quizapp.quizss.util.LogUtil;


import java.util.Collections;
import java.util.List;


public class QuizPage extends AppCompatActivity {

    private TextView txtQuestions, txtCurrentQuestions,
            txtCorrectAnswer;
    private Button btnChoiceOne, btnChoiceTwo;
    private String TAG = "quizPage";


    private SharedPreferences preferences, defaultPref, checkPref, catPref;
    private SharedPreferences.Editor editor, defaultEditor, checkEditor;
    private String categoryName;

    private static int currentQuestion = 0, queryId = 0;
    private int correctAnswer;
    private int total = 5;
    private boolean nextRound;

    private List<QueryOneCsv> queryOneCsvsLists;
    private List<InsertOneCsv> insertOneCsvsLists;
    private DbDao dbDao;
    private List<Integer> datasIdLists;

    private TextView questions;
    private Button optionA, optionB;

    private boolean check;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_layout_two);

        catPref = getSharedPreferences("categoryName", 0);

        categoryName = catPref.getString("categoryName", null);
        nextRound = getIntent().getBooleanExtra("nextRound", false);

        Log.d(TAG, "onCreate: " + categoryName);


        txtCurrentQuestions = findViewById(R.id.txt_current_questions);
        txtCorrectAnswer = findViewById(R.id.txt_correct_answer);


       /* txtQuestions = findViewById(R.id.txt_questions);
        btnChoiceOne = findViewById(R.id.txt_choice_one);
        btnChoiceTwo = findViewById(R.id.txt_choice_two);
*/

        questions = findViewById(R.id.Questions);
        optionA = findViewById(R.id.OptionA);
        optionB = findViewById(R.id.OptionB);

        checkPref = getSharedPreferences("checkPref", 0);
        preferences = getSharedPreferences("tracker", 0);
        defaultPref = PreferenceManager.getDefaultSharedPreferences(this);
        defaultEditor = defaultPref.edit();


        dbDao = new DbDao(this);


        optionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswers(0);
            }
        });

        optionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswers(1);
            }
        });

        boolean storeData = defaultPref.getBoolean(categoryName, false);


        check = checkPref.getBoolean(categoryName, false);
        datasIdLists = dbDao.getDatasId(categoryName);
        Log.d(TAG, "onCreates: " + datasIdLists.size());
        editor = preferences.edit();

        queryId = preferences.getInt(categoryName, 0);


        if (queryId < datasIdLists.size()) {
            editor.putInt(categoryName, queryId);
            editor.apply();
        } else {
            editor.putInt(categoryName, 0);
            editor.apply();
        }

        queryOneCsvsLists = dbDao.getAllDatas(categoryName,"",0);


        if (!storeData) {
          /*  defaultEditor.putBoolean(categoryName, true);
            defaultEditor.apply();
          */
            dbDao.insert(queryOneCsvsLists, categoryName);

        }


        if (check) {
            queryId = preferences.getInt(categoryName, 0);
            queryOneCsvsLists = dbDao.getQuestionData(queryId, categoryName);
            /*checkEditor = checkPref.edit();
            checkEditor.putBoolean(categoryName,false);
            checkEditor.apply();*/
            Log.d(TAG, "pagal: " + queryOneCsvsLists.size());
        }

        /*Bundle bundle = new Bundle();
        bundle.putString("categoryName",categoryName);
        bundle.putBoolean("nextRound",nextRound);

        Questions questions = new Questions();
        questions.setArguments(bundle);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();


        transaction.replace(R.id.relative_container,questions).commit();
*/
        bgProcess(dbDao.checkForDataInTrackerDb(categoryName), queryOneCsvsLists);


        //set title of toolbar
        //getSupportActionBar().setTitle(categoryName);


    }


    private void checkAnswers(int id) {
        if (id == 0) {
            int index = preferences.getInt("currentQuestion", 0);
            Log.d(TAG, "checkAnswers: " + index);

            if (optionA.getText().toString().equals(queryOneCsvsLists.get(currentQuestion).getAnswer())) {
                createDialog(R.layout.explain_dialog, queryOneCsvsLists.get(currentQuestion).getExplanation(), 0);
                Toast.makeText(this, "Correct Answer", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "InCorrect Answer", Toast.LENGTH_SHORT).show();
                createDialog(R.layout.wrong_dialog, optionB.getText().toString(), 1);
            }


        } else {
            int index = preferences.getInt("currentQuestion", 0);
            if (optionB.getText().toString().equals(queryOneCsvsLists.get(currentQuestion).getAnswer())) {
                createDialog(R.layout.explain_dialog, queryOneCsvsLists.get(currentQuestion).getExplanation(), 0);
                Toast.makeText(this, "Correct Answer", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "InCorrect Answer", Toast.LENGTH_SHORT).show();
                createDialog(R.layout.wrong_dialog, optionA.getText().toString(), 1);

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

   /* String rawQuery = "Select category,question,answer,explanation,choiceOne,choiceTwo from " +
                "data where dataId == " + id + " AND category = \'" + categoryName + "\'";

        DbHelper dbHelper = DbHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(rawQuery, null);
        String section = "",difficulty="",part="", question = "", answer = "", explanation = "", choice_one = "", choice_two = "";

        if (cursor != null) {
            while (cursor.moveToNext()) {
                section = cursor.getString(cursor.getColumnIndex(DbContract.DataEntry.category));
                difficulty = cursor.getString(cursor.getColumnIndex(DbContract.DataEntry.))
                question = cursor.getString(cursor.getColumnIndex(DbContract.DataEntry.question));
                answer = cursor.getString(cursor.getColumnIndex(DbContract.DataEntry.answer));
                explanation = cursor.getString(cursor.getColumnIndex(DbContract.DataEntry.explanation));
                choice_one = cursor.getString(cursor.getColumnIndex(DbContract.DataEntry.choiceOne));
                choice_two = cursor.getString(cursor.getColumnIndex(DbContract.DataEntry.choiceTwo));

                csvModels.add(new QueryOneCsv(
                        section,
                        difficulty,
                        part,
                        question,
                        answer,
                        explanation,
                        choice_one,
                        choice_two));

            }
        }
        cursor.close();
*/
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setLayout((6 * width) / 7, (3 * height) / 6);

        insertOneCsvsLists = dbDao.getDatasFromTracker(categoryName);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Correct Answer", Toast.LENGTH_SHORT).show();

                add(insertOneCsvsLists, queryOneCsvsLists, id);

                dialog.dismiss();

            }
        });


    }

    //add initial datas of each category to tracker Db0
    private void bgProcess(boolean check, List<QueryOneCsv> csvEntryList) {
        if (!check) {
            InsertOneCsv insertOneCsv = new InsertOneCsv(0, 0, categoryName);
            dbDao.insert(insertOneCsv);

            questions.setText(csvEntryList.get(currentQuestion).getQuestion());
            optionA.setText(csvEntryList.get(currentQuestion).getChoice_one());
            optionB.setText(csvEntryList.get(currentQuestion).getChoice_two());


        } else {
            currentQuestion = preferences.getInt("currentQuestion", 1);
            correctAnswer = preferences.getInt("correctAnswer", 0);
            try {
                Log.d(TAG, "asdasd: " + currentQuestion);
                Collections.shuffle(csvEntryList);
                questions.setText(csvEntryList.get(currentQuestion).getQuestion());
                optionA.setText(csvEntryList.get(currentQuestion).getChoice_one());
                optionB.setText(csvEntryList.get(currentQuestion).getChoice_two());

                txtCurrentQuestions.setText("Que " + (currentQuestion + 1) + "/" + total);
                txtCorrectAnswer.setText("Correct " + (correctAnswer + 1) + "/" + total);
            } catch (Exception e) {
                Log.e(TAG, "bgProcess: " + e.getMessage());
               /* editor = preferences.edit();

                editor.putInt(categoryName,0);
                editor.putInt("correctAnswer",0);
                editor.putInt("currentQuestion",0);
                editor.apply();
                currentQuestion = 0;

                checkEditor = checkPref.edit();
                checkEditor.putBoolean(categoryName,false);
                checkEditor.apply();*/


            }
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d(TAG, "onBackPressed: pressed");
        checkEditor = checkPref.edit();
        checkEditor.putBoolean(categoryName, false);
        checkEditor.apply();

    }

    //method to update the tracker Db
    private void add(List<InsertOneCsv> trackEntry, List<QueryOneCsv> queryOneCsvsLists, int id) {

        currentQuestion = trackEntry.get(0).getCurrentQuestion() + 1;
        correctAnswer = trackEntry.get(0).getCorrectAnswer() + 1;

        Log.d(TAG, "add: " + currentQuestion + " :: " + correctAnswer);

        editor = preferences.edit();
        editor.putInt("currentQuestion", currentQuestion);
        editor.putInt("correctAnswer", correctAnswer);
        editor.putInt("trackerId", trackEntry.get(0).getId());
        editor.apply();


        if (currentQuestion == 5) {
            Log.d(TAG, "exit: " + currentQuestion + " :: " + correctAnswer);

            editor.putInt(categoryName, queryId + 1);
            editor.putInt("correctAnswer", 0);
            editor.putInt("currentQuestion", 0);
            editor.apply();
            currentQuestion = 0;

            checkEditor = checkPref.edit();
            checkEditor.putBoolean(categoryName, true);
            checkEditor.apply();

            InsertOneCsv insertOneCsv = new InsertOneCsv(trackEntry.get(0).getId(), 0, 0, categoryName);
            dbDao.updateTracker(insertOneCsv);


            Intent intent = new Intent(QuizPage.this, Result.class);
            intent.putExtra("score", correctAnswer);
            intent.putExtra("incorrectAnswer", total - correctAnswer);
            intent.putExtra("categoryName", categoryName);
            startActivity(intent);
            finish();
        } else {

            if (id == 0) {
                try {
                    Log.d(TAG, "correct: " + currentQuestion + " :: " + correctAnswer);
                    questions.setText(this.queryOneCsvsLists.get(currentQuestion).getQuestion());
                    optionA.setText(queryOneCsvsLists.get(currentQuestion).getChoice_one());
                    optionB.setText(queryOneCsvsLists.get(currentQuestion).getChoice_two());
                    txtCurrentQuestions.setText("Que " + (currentQuestion + 1) + "/" + total);
                    txtCorrectAnswer.setText("Correct " + (correctAnswer) + "/" + total);

                    InsertOneCsv insertOneCsv = new InsertOneCsv(trackEntry.get(0).getId(), currentQuestion, correctAnswer, categoryName);
                    dbDao.updateTracker(insertOneCsv);

                } catch (Exception e) {
                    Log.e(TAG, "onClick: " + e.getMessage());
               /* Intent intent = new Intent(QuizPage.this, Result.class);
                intent.putExtra("score",correctAnswer);
                intent.putExtra("incorrectAnswer",total-correctAnswer);
                intent.putExtra("categoryName",categoryName);
                startActivity(intent);*/


                }

            } else {

                try {
                    Log.d(TAG, "incorrect: " + currentQuestion + " :: " + correctAnswer);

                    correctAnswer = trackEntry.get(0).getCorrectAnswer();
                    questions.setText(this.queryOneCsvsLists.get(currentQuestion).getQuestion());
                    optionA.setText(queryOneCsvsLists.get(currentQuestion).getChoice_one());
                    optionB.setText(queryOneCsvsLists.get(currentQuestion).getChoice_two());
                    txtCurrentQuestions.setText("Que " + (currentQuestion + 1) + "/" + total);
                    txtCorrectAnswer.setText("Correct " + (correctAnswer) + "/" + total);

                    InsertOneCsv insertOneCsv = new InsertOneCsv(trackEntry.get(0).getId(), currentQuestion, correctAnswer, categoryName);
                    dbDao.updateTracker(insertOneCsv);

                } catch (Exception e) {
               /* Log.e(TAG, "onClick: " + e.getMessage());
                Intent intent = new Intent(QuizPage.this, Result.class);
                startActivity(intent);*/

                }

            }


        }

    }


}
