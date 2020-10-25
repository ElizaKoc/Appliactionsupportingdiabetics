package edu.pg.DiA.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import edu.pg.DiA.database.AppDatabase;

@Entity(tableName = "user")
public class User {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int uId;

    @ColumnInfo(name = "first_name")
    public String firstName;

    @ColumnInfo(name = "last_name")
    public String lastName;

    @ColumnInfo(name = "birth_year")
    public int birth_year;

    @ColumnInfo(name = "height_cm")
    public int height_cm;

    @ColumnInfo(name = "sex")
    public String sex;

    public User(String firstName, String lastName, int birth_year, int height_cm, String sex) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birth_year = birth_year;
        this.height_cm = height_cm;
        this.sex = sex;
    }
}
