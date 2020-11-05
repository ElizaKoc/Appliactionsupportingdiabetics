package edu.pg.DiA.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import edu.pg.DiA.models.Unit;

@Dao
public interface UnitDao {

    @Query("SELECT * FROM unit")
    LiveData<List<Unit>> getAll();

    @Query("SELECT name FROM unit")
    List<String> getAllNames();

    @Query("SELECT name FROM unit WHERE id = :ujId")
    String getName(int ujId);

    @Query("SELECT id FROM unit WHERE name = :name")
    int getUnitId(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Unit>units);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Unit user);

    @Delete
    void delete(Unit unit);

    @Query("DELETE FROM unit")
    void deleteAll();
}
