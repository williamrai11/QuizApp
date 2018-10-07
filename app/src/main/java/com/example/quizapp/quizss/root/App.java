package com.example.quizapp.quizss.root;

import android.app.Application;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;


import com.example.quizapp.quizss.R;
import com.example.quizapp.quizss.data.DbContract;
import com.example.quizapp.quizss.data.model.CsvModel;
import com.facebook.stetho.Stetho;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class App extends Application {

    private List<CsvModel> csvModelArrayList = new ArrayList<>();



    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor defaultEditor = sharedPreferences.edit();
        boolean onlyOnce = sharedPreferences.getBoolean("onlyOnce", false);


        if (!onlyOnce) {
            List<CsvModel> list = null;

            try {
                list = readCsv();
                Log.d("butt", "onCreate: "+list.size());
                addCsv(list);
            }catch (Exception e){
                Log.d("root", "onCreate: "+e.getMessage());
            }



            defaultEditor.putBoolean("onlyOnce", true);
            defaultEditor.apply();
        }

    }

    private void addCsv(List<CsvModel> list) {
        int counter = 0;
        Log.d("asd", "addCsv: "+list.size());
        ContentValues contentValues = new ContentValues();
        for (CsvModel datas : list) {

            contentValues.put(DbContract.CsvEntry.section,datas.getSection());
            contentValues.put(DbContract.CsvEntry.question,datas.getQuestion());
            contentValues.put(DbContract.CsvEntry.answer,datas.getAnswer());

            contentValues.put(DbContract.CsvEntry.explanation,datas.getExplanation());
            contentValues.put(DbContract.CsvEntry.choiceOne,datas.getChoiceOne());
            contentValues.put(DbContract.CsvEntry.choiceTwo,datas.getChoiceTwo());


            getContentResolver().insert(DbContract.CsvEntry.csvUri,contentValues);
        }
    }

    //read data from csv
    private List<CsvModel> readCsv() throws IOException {
        InputStream inputStream = getResources().openRawResource(R.raw.data);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;

        Log.d("butt", "readCsv: "+reader.readLine());


/*        while ((line = reader.readLine()) != null) {
            //Split by ','

           line.replaceAll("\"","");
            Log.d("haha", "readCsv: "+line.toString());
            String[] tokens = line.split(",");


            //Read Data
            //tokens[0] : Part , tokern[1] = Chapter, token[2] = Section, token[3] = Questions
            //token[4] : Answer , token[5] = Difficulty, token[6] = Explanation, token[7] = ChoiceOne, token[8] = ChoiceTwo
            //token[9]: mark
            CsvModel csvModel = new CsvModel(
                    tokens[0],
                    tokens[1],
                    tokens[2],
                    tokens[3],
                    tokens[4],
                    tokens[5]
            );

            csvModelArrayList.add(csvModel);
            Log.d("butt", "readCsv: "+csvModelArrayList);




        }*/


        while (( line = reader.readLine()) != null) {
            String[] tokens = parseCSVLine(line);

                CsvModel csvModel = new CsvModel(
                        tokens[0],
                        tokens[1],
                        tokens[2],
                        tokens[3],
                        tokens[4],
                        tokens[5]

                );
                csvModelArrayList.add(csvModel);

            }







        //csvModelArrayList.remove(0);

        return csvModelArrayList;
    }


    public  String[] parseCSVLine(String line) {
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
