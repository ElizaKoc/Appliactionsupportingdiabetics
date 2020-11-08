package edu.pg.DiA.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.List;

import edu.pg.DiA.database.dao.BodyWeightMeasurementDao;
import edu.pg.DiA.database.dao.GlucoseMeasurementDao;
import edu.pg.DiA.database.dao.MedicineDao;
import edu.pg.DiA.database.dao.MedicineReminderDao;
import edu.pg.DiA.database.dao.ReminderDao;
import edu.pg.DiA.database.dao.UnitDao;
import edu.pg.DiA.database.dao.UserDao;

//models
import edu.pg.DiA.models.BodyWeightMeasurement;
import edu.pg.DiA.models.GlucoseMeasurement;
import edu.pg.DiA.models.Meal;
import edu.pg.DiA.models.MealInstance;
import edu.pg.DiA.models.MealType;
import edu.pg.DiA.models.Medicine;
import edu.pg.DiA.models.MedicineReminder;
import edu.pg.DiA.models.Note;
import edu.pg.DiA.models.Product;
import edu.pg.DiA.models.ProductMeal;
import edu.pg.DiA.models.Reminder;
import edu.pg.DiA.models.Unit;
import edu.pg.DiA.models.User;

@Database(entities = {
        User.class,
        BodyWeightMeasurement.class,
        GlucoseMeasurement.class,
        Meal.class,
        MealInstance.class,
        MealType.class,
        Medicine.class,
        MedicineReminder.class,
        Note.class,
        Product.class,
        ProductMeal.class,
        Reminder.class,
        Unit.class
    },
    version = 7)

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
    public abstract MedicineDao medicineDao();
    public abstract UnitDao unitDao();
    public abstract GlucoseMeasurementDao glucoseMeasurementDao();
    public abstract BodyWeightMeasurementDao bodyWeightMeasurementDao();
    public abstract ReminderDao reminderDao();
    public abstract MedicineReminderDao medicineReminderDao();
}
