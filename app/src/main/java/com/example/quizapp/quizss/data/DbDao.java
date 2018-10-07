package com.example.quizapp.quizss.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.quizapp.quizss.data.model.CsvModel;
import com.example.quizapp.quizss.data.model.Datas;
import com.example.quizapp.quizss.data.model.InsertOneCsv;
import com.example.quizapp.quizss.data.model.MarksModel;
import com.example.quizapp.quizss.data.model.QueryOneCsv;

import java.util.ArrayList;
import java.util.List;

public class DbDao implements DAO {

    private Context context;

    //constructor
    public DbDao(Context context){
        this.context = context;
    }


    @Override
    public List<String> getAllCategory() {
        List<String> strings = new ArrayList<>();
        String rawQuery = "Select distinct section from csv";

        DbHelper dbHelper = DbHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(rawQuery,null);
        String categoryNames = "";

        if (cursor!=null){
            while (cursor.moveToNext()){
                categoryNames = cursor.getString(cursor.getColumnIndex(DbContract.CsvEntry.section));
                strings.add(categoryNames);
            }
        }

        return strings;
    }

    @Override
    public void insert(InsertOneCsv insertOneCsv) {
        Log.d("insert", "insert: "+insertOneCsv.getCategoryName());
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbContract.TrackerEntry.currentQuestion,insertOneCsv.getCurrentQuestion());
        contentValues.put(DbContract.TrackerEntry.correctAnswer,insertOneCsv.getCorrectAnswer());
        contentValues.put(DbContract.TrackerEntry.categoryName,insertOneCsv.getCategoryName());
        context.getContentResolver().insert(DbContract.TrackerEntry.trackerUri,contentValues);

    }

    @Override
    public void updateTracker(InsertOneCsv insertOneCsv) {
        ContentValues trackerUpdate = new ContentValues();
        trackerUpdate.put(DbContract.TrackerEntry.correctAnswer, insertOneCsv.getCorrectAnswer());
        trackerUpdate.put(DbContract.TrackerEntry.currentQuestion, insertOneCsv.getCurrentQuestion());
        trackerUpdate.put(DbContract.TrackerEntry.categoryName, insertOneCsv.getCategoryName());

        String where = DbContract.TrackerEntry.id + " = " + insertOneCsv.getId();

        context.getContentResolver().update(DbContract.TrackerEntry.trackerUri, trackerUpdate,
                where, null);
    }

    //
    @Override
    public List<Integer> getDatasId(String category) {
        List<Integer> lists = new ArrayList<>();
        String rawQuery = "Select distinct dataId from data where category = \'"+category+"\'";

        DbHelper dbHelper = DbHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(rawQuery,null);

        int id;
        if (cursor!=null){
            while (cursor.moveToNext()){
               id = cursor.getInt(cursor.getColumnIndex(DbContract.DataEntry.dataId));
               lists.add(id);
            }

        }
        cursor.close();



        return  lists;
    }

    static int counter = 0;

    @Override
    public List<QueryOneCsv> getQuestionData(int id,String categoryName) {
        List<QueryOneCsv> csvModels = new ArrayList<>();
        String rawQuery = "Select category,question,answer,explanation,choiceOne,choiceTwo from " +
                "data where dataId == " + id+" AND category = \'"+categoryName+"\'";

        DbHelper dbHelper = DbHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(rawQuery,null);
        String section="",question ="",answer="",explanation="",choice_one="",choice_two="";

        if (cursor!=null){
            while (cursor.moveToNext()){
                section = cursor.getString(cursor.getColumnIndex(DbContract.DataEntry.category));
                question = cursor.getString(cursor.getColumnIndex(DbContract.DataEntry.question));
                answer = cursor.getString(cursor.getColumnIndex(DbContract.DataEntry.answer));
                explanation = cursor.getString(cursor.getColumnIndex(DbContract.DataEntry.explanation));
                choice_one = cursor.getString(cursor.getColumnIndex(DbContract.DataEntry.choiceOne));
                choice_two = cursor.getString(cursor.getColumnIndex(DbContract.DataEntry.choiceTwo));

                csvModels.add(new QueryOneCsv(section,
                        question,
                        answer,
                        explanation,
                        choice_one,
                        choice_two));

            }
        }
        cursor.close();

        return csvModels;
    }

    @Override
    public void insert(List<QueryOneCsv> queryOneCsvs,String categoryName) {
        ContentValues contentValues = new ContentValues();
        for (int i=0;i<queryOneCsvs.size();i++){
            contentValues.put(DbContract.DataEntry.dataId,counter);
            contentValues.put(DbContract.DataEntry.category,categoryName);
            contentValues.put(DbContract.DataEntry.question,queryOneCsvs.get(i).getQuestion());
            contentValues.put(DbContract.DataEntry.answer,queryOneCsvs.get(i).getAnswer());
            contentValues.put(DbContract.DataEntry.explanation,queryOneCsvs.get(i).getExplanation());
            contentValues.put(DbContract.DataEntry.choiceOne,queryOneCsvs.get(i).getChoice_one());
            contentValues.put(DbContract.DataEntry.choiceTwo,queryOneCsvs.get(i).getChoice_two());

            if ( i > 3 && (i % 5) == 0){
                counter ++;
                contentValues.put(DbContract.DataEntry.dataId,counter);
            }

            context.getContentResolver().insert(DbContract.DataEntry.dataUri,contentValues);
        }

        counter = 0 ;


    }

    @Override
    public List<InsertOneCsv> getDatasFromTracker(String category) {
        List<InsertOneCsv> list = new ArrayList<>();

        DbHelper dbHelper = DbHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        String rawQuery = "Select * from tracker where categoryName == \'"+category+"\'";
        Cursor cursor = sqLiteDatabase.rawQuery(rawQuery,null);
        int id , currentQuestion ,correctAnswer;
                String categoryName="";

        if (cursor!=null){
            while (cursor.moveToNext()){
                id = cursor.getInt(cursor.getColumnIndex(DbContract.TrackerEntry.id));
                currentQuestion = cursor.getInt(cursor.getColumnIndex(DbContract.TrackerEntry.currentQuestion));
                correctAnswer = cursor.getInt(cursor.getColumnIndex(DbContract.TrackerEntry.correctAnswer));
                categoryName = cursor.getString(cursor.getColumnIndex(DbContract.TrackerEntry.categoryName));
                list.add(new InsertOneCsv(id,currentQuestion,correctAnswer,categoryName));

            }
            cursor.close();
        }

        return list;
    }

    @Override
    public void insert(MarksModel marksModel) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbContract.MarksEntry.correctAnswer,marksModel.getScore());
        contentValues.put(DbContract.MarksEntry.incorrectAnswer,marksModel.getIncorrectAnswer());
        contentValues.put(DbContract.MarksEntry.categoryName,marksModel.getCategoryName());
        contentValues.put(DbContract.MarksEntry.created_date,marksModel.getDate());
        context.getContentResolver().insert(DbContract.MarksEntry.marksUri,contentValues);
    }

    @Override
    public boolean checkForDataInTrackerDb(String category) {
        String rawQuery = "Select _id from tracker where categoryName == \'"+category +"\'";

        DbHelper dbHelper = DbHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(rawQuery,null);

        if (cursor!=null){
           if (cursor.getCount() <= 0 ){
               return false;
           }
        }
        cursor.close();


        return true;

    }

    @Override
    public List<QueryOneCsv> getAllDatas(String category) {
        List<QueryOneCsv> csvModels = new ArrayList<>();
        String rawQuery = "Select section,question,answer,explanation,choice_one,choice_two from csv where section == \'"+category +"\'";

        DbHelper dbHelper = DbHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(rawQuery,null);
        String section="",question ="",answer="",explanation="",choice_one="",choice_two="";

        if (cursor!=null){
            while (cursor.moveToNext()){
                section = cursor.getString(cursor.getColumnIndex(DbContract.CsvEntry.section));
                question = cursor.getString(cursor.getColumnIndex(DbContract.CsvEntry.question));
                answer = cursor.getString(cursor.getColumnIndex(DbContract.CsvEntry.answer));
                explanation = cursor.getString(cursor.getColumnIndex(DbContract.CsvEntry.explanation));
                choice_one = cursor.getString(cursor.getColumnIndex(DbContract.CsvEntry.choiceOne));
                choice_two = cursor.getString(cursor.getColumnIndex(DbContract.CsvEntry.choiceTwo));

                csvModels.add(new QueryOneCsv(section,
                        question,
                        answer,
                        explanation,
                        choice_one,
                        choice_two));

            }
        }
        cursor.close();

        return csvModels;
    }
}
