package edu.pg.DiA.models;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

import edu.pg.DiA.helpers.TimestampConverter;

@Entity(tableName = "reminder")
public class Reminder {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int rId;

    @ColumnInfo(name = "alarm")
    public String alarm;

    @ColumnInfo(name = "weekday")
    public String weekday;

    @ColumnInfo(name = "time")
    public String time;

    @ColumnInfo(name = "date")
    @TypeConverters({TimestampConverter.class})
    public Date date;

    public Reminder(int rId, String alarm, @Nullable String weekday, String time, @Nullable Date date) {
        this.rId = rId;
        this.alarm = alarm;
        this.weekday = weekday;
        this.time = time;
        this.date = date;
    }
}
