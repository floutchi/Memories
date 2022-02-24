package org.helmo.memories.utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {

    private static DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    public static String fromDate(Date date) {
        return df.format(date);
    }

    public static Date toDate(String date) {
        try {
            return df.parse(date);
        } catch (ParseException e) {
            Log.e("DateFormatter", "Problem to parse date " + date + ". The format must be dd/MM/yyyy", e);
        }
        return null;
    }
}
