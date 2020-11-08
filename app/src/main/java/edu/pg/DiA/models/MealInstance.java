package edu.pg.DiA.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

import edu.pg.DiA.helpers.TimestampConverter;

@Entity(tableName = "meal_instance",
        foreignKeys = {
                @ForeignKey(
                        entity = Meal.class,
                        parentColumns = "id",
                        childColumns = "meal_id",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {@Index("meal_id")}
)

public class MealInstance {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int mliId;

    @ColumnInfo(name = "meal_id")
    public int mlId;

    @ColumnInfo(name = "date")
    @TypeConverters({TimestampConverter.class})
    public Date date;

    public MealInstance(int mlId, Date date) {
        this.mlId = mlId;
        this.date = date;
    }
}
