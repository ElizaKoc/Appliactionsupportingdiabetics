package edu.pg.DiA.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.Date;
import java.util.List;

import edu.pg.DiA.models.Body_weight_measurement;

@Dao
public interface BodyWeightMeasurementDao {

    @Query("SELECT * FROM body_weight_measurement WHERE user_id = :userId")
    LiveData<List<Body_weight_measurement>> getAllMeasurements(int userId);

    @Query("SELECT weight_kg FROM body_weight_measurement WHERE user_id = :userId ORDER BY date DESC LIMIT 1")
    LiveData<Float> getLatestWeight(int userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Body_weight_measurement> measurements);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Body_weight_measurement measurement);

    @Delete
    void delete(Body_weight_measurement measurement);

    @Query("DELETE FROM body_weight_measurement")
    void deleteAll();
}
