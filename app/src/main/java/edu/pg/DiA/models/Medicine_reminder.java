package edu.pg.DiA.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "medicine_reminder",
        primaryKeys = {"medicine_id", "reminder_id"},
        foreignKeys = {
                @ForeignKey(
                        entity = Medicine.class,
                        parentColumns = "id",
                        childColumns = "medicine_id",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Reminder.class,
                        parentColumns = "id",
                        childColumns = "reminder_id",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {@Index("medicine_id"), @Index("reminder_id")}
    )

public class Medicine_reminder {
    @ColumnInfo(name = "medicine_id")
    public int medicineId;

    @ColumnInfo(name = "reminder_id")
    public int reminderId;

    @ColumnInfo(name = "dose_units")
    public String doseUnits;

    public Medicine_reminder(int medicineId, int reminderId, String doseUnits) {
        this.medicineId = medicineId;
        this.reminderId = reminderId;
        this.doseUnits = doseUnits;
    }
}
