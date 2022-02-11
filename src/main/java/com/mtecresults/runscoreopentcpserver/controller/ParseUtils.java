package com.mtecresults.runscoreopentcpserver.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class ParseUtils {

    private static final ThreadLocal<DateFormat> dateFormat = new ThreadLocal<DateFormat>(){
        @Override
        public DateFormat get() {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            return sdf;
        }
    };

    public static long parseTimeString(String timeString) throws ParseException {
        return dateFormat.get().parse(timeString).getTime();
    }
}
