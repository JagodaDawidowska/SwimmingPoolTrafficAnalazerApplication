package com.jdawidowska.myapplication3.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
    //                [{downloadDate":"2023-05-09T19:59:58.780184+02:00"}]
    public static String returnDateFrom() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSXXX");
        Date currentTime = Calendar.getInstance().getTime();
        String formattedTime = sdf.format(currentTime);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentTime);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        String sHour = Integer.toString(hour);
        String sHourMinusOne = Integer.toString(hour - 1);
        int minute = calendar.get(Calendar.MINUTE);
        String sMinute = Integer.toString(minute);
        if (minute < 30) {
            if (formattedTime.contains("T" + sHour + ":" + sMinute)) {
                String formattedString1 = formattedTime.replace("T" + sHour + ":" + sMinute, ("T" + sHourMinusOne + ":30"));
                return formattedString1;
            }
        } else {
            if (formattedTime.contains("T" + sHour + ":" + sMinute)) {
                String formattedString = formattedTime.replace("T" + sHour + ":" + sMinute, ("T" + sHour + ":00"));
                return formattedString;
            }
        }
        return "error";
    }

    public static String returnDateFromDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSXXX");
        Date currentTime = Calendar.getInstance().getTime();
        String formattedTime = sdf.format(currentTime);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentTime);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        String sHour = Integer.toString(hour);

        int minute = calendar.get(Calendar.MINUTE);
        String sMinute = Integer.toString(minute);

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String sDay = Integer.toString(day);

        if (formattedTime.contains(sDay + "T")) {
            String formattedString1 = formattedTime.replace("T" + sHour + ":" + sMinute, ("T" + "06:" + sMinute));
            return formattedString1;
        } else return "error";
    }

    public static String returnDateTo() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSXXX");
        Date currentTime = Calendar.getInstance().getTime();
        String formattedTime = sdf.format(currentTime);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentTime);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        String sHour = Integer.toString(hour);
        String sHourPlusOne = Integer.toString((hour + 1));
        int minute = calendar.get(Calendar.MINUTE);
        String sMinute = Integer.toString(minute);

        if (formattedTime.contains("T" + sHour + ":" + sMinute)) {
            String formattedString = formattedTime.replace("T" + sHour + ":" + sMinute, ("T" + sHourPlusOne + ":00"));
            return formattedString;
        } return "error";
    }

    public static String returnDateToDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSXXX");
        Date currentTime = Calendar.getInstance().getTime();
        String formattedTime = sdf.format(currentTime);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentTime);

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        String sHour = Integer.toString(hour);

        int minute = calendar.get(Calendar.MINUTE);
        String sMinute = Integer.toString(minute);

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String sDay = Integer.toString(day);

        if (formattedTime.contains(sDay + "T")) {
            String formattedString1 = formattedTime.replace("T" + sHour + ":" + sMinute, ("T" + "21:" + sMinute));
            return formattedString1;
        } else
            return "error";
    }


    public static String getMinutes() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSXXX");
        Date currentTime = Calendar.getInstance().getTime();
        String formattedTime = sdf.format(currentTime);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentTime);
        int minute = calendar.get(Calendar.MINUTE);
        String sMinute = Integer.toString(minute);
        return sMinute;
    }
}
