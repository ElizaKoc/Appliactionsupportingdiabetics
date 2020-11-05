package edu.pg.DiA.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import edu.pg.DiA.models.Medicine;
import edu.pg.DiA.models.User;

@Dao
public interface MedicineDao {

    @Query("SELECT * FROM medicine WHERE user_id = :userId")
    LiveData<List<Medicine>> getAllMedicines(int userId);

    @Query("SELECT * FROM medicine WHERE id = :mId")
    LiveData<Medicine> getMedicine(int mId);


    @Query("SELECT name FROM medicine WHERE id = :mId")
    String getName(int mId);

    @Query("SELECT description FROM medicine WHERE  id = :mId")
    String getDescription(int mId);

    @Query("SELECT name FROM medicine WHERE user_id = :userId")
    List<String> getAllNames(int userId);

    @Query("SELECT unit_id FROM medicine WHERE name = :name")
    int getUnitId(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Medicine>medicines);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Medicine medicine);

    @Delete
    void delete(Medicine medicine);

    @Query("DELETE FROM medicine")
    void deleteAll();
}
