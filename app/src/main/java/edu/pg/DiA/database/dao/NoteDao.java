package edu.pg.DiA.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.TypeConverters;

import java.util.Date;
import java.util.List;

import edu.pg.DiA.helpers.TimestampConverter;
import edu.pg.DiA.models.Note;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM note WHERE user_id = :userId")
    LiveData<List<Note>> getAllNotes(int userId);

    @Query("SELECT name FROM note WHERE id = :nId")
    String getName(int nId);

    @Query("SELECT description FROM note WHERE  id = :nId")
    String getDescription(int nId);

    @Query("SELECT * FROM note WHERE id = :nId")
    Note getNote(int nId);

    @TypeConverters({TimestampConverter.class})
    @Query("UPDATE note SET name = :name, description = :description, date = :date WHERE id = :nId")
    void updateNote(String name, String description, Date date, int nId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Note> notes);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Note note);

    @Delete
    void delete(Note note);

    @Query("DELETE FROM note")
    void deleteAll();
}
