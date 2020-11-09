package edu.pg.DiA.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.Date;
import java.util.List;

import edu.pg.DiA.models.Reminder;

@Dao
public interface ReminderDao {

    @Query("SELECT * FROM reminder")
    LiveData<List<Reminder>> getAll();

    @Query("SELECT * FROM reminder WHERE user_id = :userId AND (date = :date OR weekday = :weekday)")
    LiveData<List<Reminder>> getAllUserRemindersByDate(int userId, String date, String weekday);

    @Query("SELECT date FROM reminder WHERE user_id = :userId AND date IS NOT NULL")
    LiveData<List<String>> getAllUserReminderDates(int userId);


    @Query("SELECT weekday FROM reminder WHERE user_id = :userId AND weekday IS NOT NULL")
    LiveData<List<String>> getAllUserReminderWeekdays(int userId);

    @Query("SELECT id FROM reminder  ORDER BY id DESC LIMIT 1")
    Integer getLatesReminderId();

    @Query("SELECT user_id FROM reminder  WHERE id = :rId")
    int getUserId(int rId);

    @Query("SELECT * FROM reminder  WHERE alarm = :alarm AND user_id = :userId")
    LiveData<List<Reminder>> getMeasurementReminders(String alarm, int userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Reminder> reminders);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Reminder reminder);

    @Delete
    void delete(Reminder reminder);

    @Query("DELETE FROM reminder")
    void deleteAll();
}
