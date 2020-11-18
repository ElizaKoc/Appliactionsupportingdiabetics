package edu.pg.DiA.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import org.jetbrains.annotations.Nullable;

import java.util.Date;

import edu.pg.DiA.helpers.TimestampConverter;

@Entity(tableName = "glucose_measurement",
        foreignKeys = {
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "id",
                        childColumns = "user_id",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Reminder.class,
                        parentColumns = "id",
                        childColumns = "reminder_id",
                        onDelete = ForeignKey.SET_NULL
                )
        },
        indices = {@Index("user_id"), @Index("reminder_id")}
    )

public class GlucoseMeasurement {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int gmId;

    @ColumnInfo(name = "reminder_id", defaultValue = "NULL")
    public Integer reminderId;

    @ColumnInfo(name = "user_id")
    public int userId;

    @ColumnInfo(name = "measurements_mg/dL")
    public float dose;

    @ColumnInfo(name = "date")
    @TypeConverters({TimestampConverter.class})
    public Date date;

    public GlucoseMeasurement(int gmId, @Nullable Integer reminderId, int userId, float dose, Date date) {
        this.gmId = gmId;
        this.reminderId = reminderId;
        this.userId = userId;
        this.dose = dose;
        this.date = date;
    }
}
