package edu.pg.DiA.helpers;

import androidx.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimestampConverter {

    @TypeConverter
    public static Date fromTimestamp(String value) {
        try {
            return value == null ? null : new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(value);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @TypeConverter
    public static String dateToTimestamp(Date date) {

        String strDate;

        if(date != null) {
            if(date.getTime() != 0) {
                strDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
            } else {
                strDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
            }
            return date == null ? null : strDate;
        }
        return null;
    }
}
