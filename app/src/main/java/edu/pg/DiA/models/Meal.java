package edu.pg.DiA.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "meal",
        foreignKeys = {
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "id",
                        childColumns = "user_id",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Meal_type.class,
                        parentColumns = "id",
                        childColumns = "meal_type_id",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {@Index("user_id"), @Index("meal_type_id")}
)

public class Meal {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int mlId;

    @ColumnInfo(name = "user_id")
    public int userId;

    @ColumnInfo(name = "meal_type_id")
    public int mealTypeId;

    @ColumnInfo(name = "description")
    public String description;
}
