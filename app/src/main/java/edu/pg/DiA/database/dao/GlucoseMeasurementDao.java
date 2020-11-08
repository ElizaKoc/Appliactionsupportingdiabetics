package edu.pg.DiA.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import edu.pg.DiA.models.GlucoseMeasurement;

@Dao
public interface GlucoseMeasurementDao {

    @Query("SELECT * FROM glucose_measurement WHERE user_id = :userId")
    LiveData<List<GlucoseMeasurement>> getAllMeasurements(int userId);

    @Query("SELECT date FROM glucose_measurement WHERE user_id = :gmId")
    String getDate(int gmId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<GlucoseMeasurement>measurements);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(GlucoseMeasurement measurement);

    @Delete
    void delete(GlucoseMeasurement measurement);

    @Query("DELETE FROM glucose_measurement")
    void deleteAll();
}
