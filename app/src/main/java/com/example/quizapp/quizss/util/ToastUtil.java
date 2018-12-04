package com.example.quizapp.quizss.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {


    public static void shortToast(Context context, String values) {
        Toast.makeText(context, values, Toast.LENGTH_SHORT).show();

    }

    public static void longToast(Context context, String values) {
        Toast.makeText(context, values, Toast.LENGTH_LONG).show();

    }
}
