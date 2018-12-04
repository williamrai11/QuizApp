package com.example.quizapp.quizss.util;

import android.util.Log;

/**
 * Created by william on 3/23/18.
 */

public class LogUtil {
    private static final boolean LOGGING = true; //set this to false if release version


    public static void dLog(String TAG, String message) {
        if (LOGGING)
            Log.d(TAG, message);
    }

    public static void eLog(String TAG, String message) {
        if (LOGGING)
            Log.e(TAG, message);
    }

    public static void wLog(String TAG, String message) {
        if (LOGGING)
            Log.w(TAG, message);
    }

    public static void iLog(String TAG, String message) {
        if (LOGGING)
            Log.i(TAG, message);
    }

}
