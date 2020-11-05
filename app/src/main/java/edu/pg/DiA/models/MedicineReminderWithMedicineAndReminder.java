package edu.pg.DiA.models;

import androidx.room.Embedded;
import androidx.room.Relation;

public class MedicineReminderWithMedicineAndReminder {
    @Embedded
    public Medicine_reminder medicine_reminder;

    @Relation(
            parentColumn = "reminder_id",
            entityColumn = "id",
            entity = Reminder.class
    ) public Reminder reminder;

    @Relation(
            parentColumn = "medicine_id",
            entityColumn = "id",
            entity = Medicine.class
    ) public Medicine medicine;
}
