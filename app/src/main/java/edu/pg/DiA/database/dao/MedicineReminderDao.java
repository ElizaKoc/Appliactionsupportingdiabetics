package edu.pg.DiA.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import edu.pg.DiA.models.MedicineReminderWithMedicineAndReminder;
import edu.pg.DiA.models.MedicineReminder;

@Dao
public interface MedicineReminderDao {

    @Transaction
    @Query("SELECT * FROM medicine_reminder WHERE medicine_id = :mId")
    LiveData<List<MedicineReminderWithMedicineAndReminder>> getAll(int mId);

    @Transaction
    @Query("SELECT * FROM medicine_reminder WHERE medicine_id = :mId")
    List<MedicineReminderWithMedicineAndReminder> getRemindersList(int mId);

    @Query("SELECT medicine_id FROM medicine_reminder WHERE reminder_id = :rId")
    int getMedicineId(int rId);

    @Query("SELECT dose_units FROM medicine_reminder WHERE reminder_id = :rId")
    String getDose(int rId);

    @Query("UPDATE medicine_reminder SET medicine_id = :mId, dose_units = :dose WHERE reminder_id = :rId")
    void updateMedicineReminder(int mId, String dose, int rId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(MedicineReminder medicine_reminder);
}
