package edu.pg.DiA.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import edu.pg.DiA.models.Body_weight_measurement;

@Dao
public interface BodyWeightMeasurementDao {

    @Query("SELECT * FROM body_weight_measurement")
    List<Body_weight_measurement> getAll();

    @Insert
    void insertAll(Body_weight_measurement... measurements);

    @Delete
    void delete(Body_weight_measurement measurement);
}
