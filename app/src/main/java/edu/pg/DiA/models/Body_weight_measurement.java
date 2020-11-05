package edu.pg.DiA.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

import edu.pg.DiA.helpers.TimestampConverter;

@Entity(tableName = "body_weight_measurement",
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

public class Body_weight_measurement {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int bwmId;

    @ColumnInfo(name = "user_id")
    public int userId;

    @ColumnInfo(name = "weight_kg")
    public float weightKg;

    @ColumnInfo(name = "date")
    @TypeConverters({TimestampConverter.class})
    public Date date;

    public Body_weight_measurement(int bwmId, int userId, float weightKg, Date date) {
        this.bwmId = bwmId;
        this.userId = userId;
        this.weightKg = weightKg;
        this.date = date;
    }
}