package com.clarigo.propertyfinderagent.webservice;

import android.app.Activity;
import android.net.ParseException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by rohits on 24/4/19.
 */

public class Constant {
    public static final String MAIN_URL = "http://clarigoinfotech.co.in/property/Api/";
    public static final String UPDATELOCATION = "save_agent_lat_long";



    public static String getMethodUrl(String methodName) {
          return MAIN_URL + methodName;

    }


    public static String converDateFormate(String Date, String Oformat, String Tformat, Activity activity) {

        String formattedDate = "";
        try {
            DateFormat originalFormat = new SimpleDateFormat(Oformat);
            DateFormat targetFormat = new SimpleDateFormat(Tformat);
            java.util.Date date = originalFormat.parse(Date);
            formattedDate = targetFormat.format(date);
        } catch (Exception e) {
        }
        return formattedDate;
    }

    public static Date converStringToDate(String String_date, String date_format) {
        SimpleDateFormat format = new SimpleDateFormat(date_format);
        Date date = null;
        try {
            try {
                date = format.parse(String_date);
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
