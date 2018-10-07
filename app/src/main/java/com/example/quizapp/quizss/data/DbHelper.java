package com.example.quizapp.quizss.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by william on 3/21/18.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "quiz.db";
    private static final int DATABASE_VERSION = 1;
    private static DbHelper mInstance = null;


    /*public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
*/
    public static DbHelper getInstance(Context ctx) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (mInstance == null) {
            mInstance = new DbHelper(ctx.getApplicationContext());
        }
        return mInstance;
    }

    /**
     * Constructor should be private to prevent direct instantiation.
     * make call to static factory method "getInstance()" instead.
     */
    private DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
     db.execSQL(DbContract.CsvEntry.CREATE_CSV_TABLE);
     db.execSQL(DbContract.TrackerEntry.CREATE_TRACKER_TABLE);
     db.execSQL(DbContract.MarksEntry.CREATE_MARKS_TABLE);
     db.execSQL(DbContract.DataEntry.CREATE_DATA_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {

            //  db.execSQL("ALTER TABLE schedules ADD COLUMN position");
            // db.execSQL("ALTER TABLE students ADD COLUMN isChecked");
        }


    }
}
