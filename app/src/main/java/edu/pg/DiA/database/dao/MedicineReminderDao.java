package edu.pg.DiA.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import edu.pg.DiA.models.Medicine;
import edu.pg.DiA.models.MedicineReminderWithMedicineAndReminder;
import edu.pg.DiA.models.Medicine_reminder;

@Dao
public interface MedicineReminderDao {

    @Transaction
    @Query("SELECT * FROM medicine_reminder WHERE medicine_id = :mId")
    LiveData<List<MedicineReminderWithMedicineAndReminder>> getAll(int mId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Medicine_reminder medicine_reminder);
}
