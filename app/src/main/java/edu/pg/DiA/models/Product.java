package edu.pg.DiA.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "product",
        foreignKeys = {
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "id",
                        childColumns = "user_id",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Unit.class,
                        parentColumns = "id",
                        childColumns = "unit_id",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {@Index("user_id"), @Index("unit_id")}
)

public class Product {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int pId;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "user_id")
    public int userId;

    @ColumnInfo(name = "unit_id")
    public int unitId;

    @ColumnInfo(name = "kcal_per_unit")
    public float kcalPerUnit;

    @ColumnInfo(name = "sugar_grams_per_unit")
    public float sugarGramsPerUnit;
}
