package edu.pg.DiA.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import edu.pg.DiA.models.BodyWeightMeasurement;

@Dao
public interface BodyWeightMeasurementDao {

    @Query("SELECT * FROM body_weight_measurement WHERE user_id = :userId")
    LiveData<List<BodyWeightMeasurement>> getAllMeasurements(int userId);

    @Query("SELECT weight_kg FROM body_weight_measurement WHERE user_id = :userId ORDER BY date DESC LIMIT 1")
    LiveData<Float> getLatestWeight(int userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<BodyWeightMeasurement> measurements);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(BodyWeightMeasurement measurement);

    @Delete
    void delete(BodyWeightMeasurement measurement);

    @Query("DELETE FROM body_weight_measurement")
    void deleteAll();
}
