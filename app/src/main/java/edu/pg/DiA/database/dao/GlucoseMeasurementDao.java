package edu.pg.DiA.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import edu.pg.DiA.models.Glucose_measurement;

@Dao
public interface GlucoseMeasurementDao {

    @Query("SELECT * FROM glucose_measurement WHERE user_id = :userId")
    LiveData<List<Glucose_measurement>> getAllMeasurements(int userId);

    @Query("SELECT date FROM glucose_measurement WHERE user_id = :gmId")
    String getDate(int gmId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Glucose_measurement>measurements);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Glucose_measurement measurement);

    @Delete
    void delete(Glucose_measurement measurement);

    @Query("DELETE FROM glucose_measurement")
    void deleteAll();
}
