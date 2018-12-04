package com.example.quizapp.quizss.root;

import android.app.Application;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.util.Log;


import com.example.quizapp.quizss.R;
import com.example.quizapp.quizss.data.local.providers.DbContract;
import com.example.quizapp.quizss.data.local.model.CsvModel;
import com.example.quizapp.quizss.data.local.providers.DbHelper;
import com.facebook.stetho.Stetho;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;


public class App extends Application {

    /**
     * This is the class which runs at first of initialization of APP
     * In OnCreate Method a shared Preference is created for storing version code internally
     * this is to check the version of the app for updating database and other stuff
     * In every run csv data is read once and datas are stored or updated to database
     * The sole purpose of this class is to store csv data to database
     */


    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor defaultEditor = sharedPreferences.edit();
        int vCode = sharedPreferences.getInt("versionCode", 0);

        DbHelper dbHelper = DbHelper.getInstance(getApplicationContext());
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();



        try {
            PackageInfo packageManager = getPackageManager().getPackageInfo(getPackageName(), 0);
            int versionCode = packageManager.versionCode;
            if (versionCode > vCode) {
                Log.d("test", "onCreate: " + versionCode);

                sqLiteDatabase.delete(DbContract.CsvEntry.TABLE_NAME,null,null);

                ArrayList<CsvModel> csvModelArrayList = new ArrayList<>();
                for (int i = 0; i < (readCsv().size() - 1); i++) {
                    String[] datas = parseCSVLine(readCsv().get(i));
                    Log.d("asshole", "onCreate: " + i + " " + readCsv().get(i));
                    CsvModel csvModel = new CsvModel(
                            datas[0],
                            datas[1],
                            datas[2],
                            datas[3],
                            datas[4],
                            datas[5],
                            datas[6],
                            datas[7],
                            datas[8]
                    );
                    csvModelArrayList.add(csvModel);
                }

                csvModelArrayList.remove(0);


                try {
                    Log.d("asd", "onCreate: " + csvModelArrayList.size());
                    addCsv(csvModelArrayList);

                } catch (Exception e) {
                    Log.d("asshole", "onCreate: " + e.getMessage());
                }
                defaultEditor.putInt("versionCode", versionCode);
                defaultEditor.apply();

            } else {
                Log.d("test", "onCreate: done already");
            }


        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void addCsv(List<CsvModel> list) {
        int counter = 0;
        Log.d("hello", "addCsv: ");
        ContentValues contentValues = new ContentValues();
        for (CsvModel datas : list) {

            contentValues.put(DbContract.CsvEntry.id, datas.getIndex());
            contentValues.put(DbContract.CsvEntry.section, datas.getSection());
            contentValues.put(DbContract.CsvEntry.difficulty, datas.getDifficulty());
            contentValues.put(DbContract.CsvEntry.part, datas.getPart());
            contentValues.put(DbContract.CsvEntry.question, datas.getQuestion());
            contentValues.put(DbContract.CsvEntry.answer, datas.getAnswer());

            contentValues.put(DbContract.CsvEntry.explanation, datas.getExplanation());
            contentValues.put(DbContract.CsvEntry.choiceOne, datas.getChoiceOne());
            contentValues.put(DbContract.CsvEntry.choiceTwo, datas.getChoiceTwo());


            getContentResolver().insert(DbContract.CsvEntry.csvUri, contentValues);
        }
    }

    //read data from csv
    private ArrayList<String> readCsv() {


        Scanner scanner = new Scanner(getResources().openRawResource(R.raw.data));
        scanner.useDelimiter(Pattern.compile(";"));


        ArrayList<String> arrayList = new ArrayList<>();

        while (scanner.hasNext()) {
            String wholeString = scanner.next();

            arrayList.add(wholeString);

        }


        return arrayList;
    }


    public String[] parseCSVLine(String line) {
        // Create a pattern to match breaks
        Pattern p =
                Pattern.compile(",(?=([^\"]*\"[^\"]*\")*(?![^\"]*\"))");
        // Split input with the pattern
        String[] fields = p.split(line);
        for (int i = 0; i < fields.length; i++) {
            // Get rid of residual double quotes
            fields[i] = fields[i].replace("\"", "");
        }
        return fields;
    }


}
