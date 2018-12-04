package com.example.quizapp.quizss.data.local.providers;

import android.content.Context;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by william on 3/21/18.
 */

public final class DbContract {

//Test
    /*private static DbContract INSTANCE = null;

    public static DbContract getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (DbContract.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DbContract();
                }
            }
        }
        return INSTANCE;
    }

    *//**
     * @param isForeignKey
     * @param ForeginKeyNames
     * @param names           In names parameter  Id and rest can be anythin
     *//*

    public void create(boolean isForeignKey,String tableName,String primaryKey,String[] ForeginKeyNames,  String... names) {

        if (isForeignKey) {

        } else {
            for (String name : names) {
                Log.d("test", "setColumns: " + name);
            }
        }
    }
*/

    public static final String CONTENT_AUTHORITY = "com.example.quizapp.quizss.quizprovider";

    public static final String SCHEDULE_PATH = "schedules";
    public static final String CATEGORY = "category";
    public static final String CHAPTER = "chapter";
    public static final String QUESTION = "question";
    public static final String ANSWER = "answer";
    public static final String CSVENTRY = "csv";
    public static final String TRACKENTRY = "tracker";
    public static final String MARKSENTRY = "marks";
    public static final String DATAENTRY = "data";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    public static final class CsvEntry implements BaseColumns {
        public static final Uri csvUri = Uri.withAppendedPath(BASE_CONTENT_URI, CSVENTRY);

        public static final String TABLE_NAME = "csv";

        public static final String id = BaseColumns._ID;
        public static final String chapter = "chapter";
        public static final String section = "section";
        public static final String question = "question";
        public static final String answer = "answer";
        public static final String difficulty = "difficulty";
        public static final String part = "part";
        public static final String explanation = "explanation";
        public static final String choiceOne = "choice_one";
        public static final String choiceTwo = "choice_two";

        public static final String CREATE_CSV_TABLE = "create table csv (\n" +
                "_id integer primary key, \n" +
                "section text, \n" +
                "difficulty text, \n" +
                "part integer, \n" +
                "question text, \n" +
                "answer text, \n" +
                "explanation text, \n" +
                "choice_one text, \n" +
                "choice_two text \n" +
                ")";

    }


    public static final class TrackerEntry implements BaseColumns {
        public static final Uri trackerUri = Uri.withAppendedPath(BASE_CONTENT_URI, TRACKENTRY);

        public static final String id = BaseColumns._ID;
        public static final String currentQuestion = "currentQuestion";
        public static final String correctAnswer = "correctAnswer";
        public static final String categoryName = "categoryName";

        public static final String CREATE_TRACKER_TABLE = "create table tracker (\n" +
                "_id integer primary key, \n" +
                "currentQuestion integer, \n" +
                "correctAnswer integer, \n" +
                "categoryName text \n" +
                ")";

    }

    public static final class MarksEntry implements BaseColumns {
        public static final Uri marksUri = Uri.withAppendedPath(BASE_CONTENT_URI, MARKSENTRY);

        public static final String id = BaseColumns._ID;
        public static final String correctAnswer = "correctAnswer";
        public static final String incorrectAnswer = "incorrectAnswer";
        public static final String categoryName = "categoryName";
        public static final String difficulty = "difficulty";
        public static final String part = "part";
        public static final String created_date = "created_date";

        public static final String CREATE_MARKS_TABLE = "create table marks (\n" +
                "_id integer primary key, \n" +
                "correctAnswer integer, \n" +
                "incorrectAnswer integer, \n" +
                "categoryName text, \n" +
                "difficulty text, \n" +
                "part text, \n" +
                "created_date text \n" +
                ")";

    }

    public static final class DataEntry implements BaseColumns {
        public static final Uri dataUri = Uri.withAppendedPath(BASE_CONTENT_URI, DATAENTRY);

        public static final String id = BaseColumns._ID;
        public static final String question = "question";
        public static final String category = "category";
        public static final String difficulty = "difficulty";
        public static final String part = "part";
        public static final String dataId = "dataId";
        public static final String answer = "answer";
        public static final String explanation = "explanation";
        public static final String choiceOne = "choiceOne";
        public static final String choiceTwo = "choiceTwo";


        public static final String CREATE_DATA_TABLE = "create table data (\n" +
                "_id integer primary key, \n" +
                "dataId integer, \n" +
                "category text, \n" +
                "difficulty text, \n" +
                "part integer, \n" +
                "question text, \n" +
                "answer text, \n" +
                "explanation text, \n" +
                "choiceOne text, \n" +
                "choiceTwo text \n" +
                ")";

    }






}