package edu.pg.DiA.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.io.File;
import java.util.List;

import edu.pg.DiA.database.dao.UserDao;

//models
import edu.pg.DiA.models.Body_weight_measurement;
import edu.pg.DiA.models.Glucose_measurement;
import edu.pg.DiA.models.Meal;
import edu.pg.DiA.models.Meal_instance;
import edu.pg.DiA.models.Meal_type;
import edu.pg.DiA.models.Medicine;
import edu.pg.DiA.models.Medicine_reminder;
import edu.pg.DiA.models.Note;
import edu.pg.DiA.models.Product;
import edu.pg.DiA.models.Product_meal;
import edu.pg.DiA.models.Reminder;
import edu.pg.DiA.models.Unit;
import edu.pg.DiA.models.User;

@Database(entities = {
        User.class,
        Body_weight_measurement.class,
        Glucose_measurement.class,
        Meal.class,
        Meal_instance.class,
        Meal_type.class,
        Medicine.class,
        Medicine_reminder.class,
        Note.class,
        Product.class,
        Product_meal.class,
        Reminder.class,
        Unit.class
    },
    version = 2)

public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase single_instance = null;

    public static AppDatabase getInstance(Context getApplicationContext)
    {
        if (single_instance == null)
            single_instance = Room.databaseBuilder(getApplicationContext,
                    AppDatabase.class, "DiA").allowMainThreadQueries().addCallback(new Callback() {
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    //single_instance.rePopulateDB();
                }
            }).fallbackToDestructiveMigration().createFromAsset("database_defaults/default_data.db").build();

        return single_instance;
    }

    private void rePopulateDB() {
        this.userDao().deleteAll();

        LiveData<List<User>> list =  this.userDao().getAll();
        Log.d("STATE", list.toString());
    }

    public abstract UserDao userDao();
}
