package edu.pg.DiA.models;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

import edu.pg.DiA.helpers.TimestampConverter;

@Entity(tableName = "reminder",
        foreignKeys = {
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "id",
                        childColumns = "user_id",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {@Index("user_id")}
)
public class Reminder {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int rId;

    @ColumnInfo(name = "user_id")
    public int uId;

    @ColumnInfo(name = "alarm")
    public String alarm;

    @ColumnInfo(name = "weekday")
    public String weekday;

    @ColumnInfo(name = "time")
    public String time;

    @ColumnInfo(name = "date")
    @TypeConverters({TimestampConverter.class})
    public Date date;

    public Reminder(int rId, int uId, String alarm, @Nullable String weekday, String time, @Nullable Date date) {
        this.rId = rId;
        this.uId = uId;
        this.alarm = alarm;
        this.weekday = weekday;
        this.time = time;
        this.date = date;
    }
}
