package com.example.quizapp.quizss.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

public static String getCurrentDate(){
    SimpleDateFormat sdf = new SimpleDateFormat("MMM-dd-yyyy  hh:mm");
    Date date = Calendar.getInstance().getTime();
    String currentDate = sdf.format(date);

    return currentDate;
}

}
