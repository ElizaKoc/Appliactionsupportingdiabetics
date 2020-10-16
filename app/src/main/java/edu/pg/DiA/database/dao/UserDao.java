package edu.pg.DiA.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import edu.pg.DiA.models.User;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user ORDER BY first_name ASC")
    LiveData<List<User>> getAll();

    /*@Query("SELECT * FROM user WHERE id IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
    User findByName(String first, String last);*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<User>users);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Delete
    void delete(User user);

    @Query("DELETE FROM user")
    void deleteAll();
}
