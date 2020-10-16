package edu.pg.DiA.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "meal_type")
public class Meal_type {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int mtId;

    @ColumnInfo(name = "name")
    public String name;
}
