package edu.pg.DiA.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

import edu.pg.DiA.helpers.TimestampConverter;

@Entity(tableName = "note",
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

public class Note {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int nId;

    @ColumnInfo(name = "user_id")
    public int userId;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "date")
    @TypeConverters({TimestampConverter.class})
    public Date date;

    public Note(int nId, int userId, String name, String description, Date date) {
        this.nId = nId;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.date = date;
    }
}
