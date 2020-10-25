package edu.pg.DiA.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "unit")
public class Unit {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int ujId;

    @ColumnInfo(name = "name")
    public String name;

    public Unit(String name) {
        this.name = name;
    }
}
