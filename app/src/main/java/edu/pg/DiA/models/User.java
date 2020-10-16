package edu.pg.DiA.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class User {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int uId;

    @ColumnInfo(name = "first_name")
    public String firstName;

    @ColumnInfo(name = "last_name")
    public String lastName;

    @ColumnInfo(name = "age")
    public int age;

    @ColumnInfo(name = "height_cm")
    public int height_cm;

    @ColumnInfo(name = "sex")
    public String sex;

    public User(String firstName, String lastName, int age, int height_cm, String sex) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.height_cm = height_cm;
        this.sex = sex;
    }
}
