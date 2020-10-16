package edu.pg.DiA.helpers;

import androidx.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimestampConverter {

    @TypeConverter
    public static Date fromTimestamp(String value) {
        try {
            return value == null ? null : new SimpleDateFormat("dd/MM/yyyy").parse(value);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @TypeConverter
    public static String dateToTimestamp(Date date) {

        String strDate = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(date);
        return date == null ? null : strDate;
    }
}
