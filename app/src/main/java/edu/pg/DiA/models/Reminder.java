package edu.pg.DiA.models;

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

    @ColumnInfo(name = "repeat")
    public int repeat = 0;

    @ColumnInfo(name = "weekday")
    public String weekday;

    @ColumnInfo(name = "time")
    public String time;

    @ColumnInfo(name = "date")
    @TypeConverters({TimestampConverter.class})
    public Date date;
}
