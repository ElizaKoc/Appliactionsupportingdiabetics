package edu.pg.DiA.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "medicine",
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

public class Medicine {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int mId;

    @ColumnInfo(name = "user_id")
    public int userId;

    @ColumnInfo(name = "unit_id")
    public int unitId;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "description")
    public String description;

    public Medicine(int userId, int unitId, String name, String description) {
        this.userId = userId;
        this.unitId = unitId;
        this.name = name;
        this.description = description;
    }
}
