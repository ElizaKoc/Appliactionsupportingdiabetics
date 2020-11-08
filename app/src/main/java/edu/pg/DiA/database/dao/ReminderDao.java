package edu.pg.DiA.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import edu.pg.DiA.models.Reminder;

@Dao
public interface ReminderDao {

    @Query("SELECT * FROM reminder")
    LiveData<List<Reminder>> getAll();

    @Query("SELECT id FROM reminder  ORDER BY id DESC LIMIT 1")
    Integer getLatesReminderId();

    @Query("SELECT user_id FROM reminder  WHERE id = :rId")
    int getUserId(int rId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Reminder> reminders);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Reminder reminder);

    @Delete
    void delete(Reminder reminder);

    @Query("DELETE FROM reminder")
    void deleteAll();
}
