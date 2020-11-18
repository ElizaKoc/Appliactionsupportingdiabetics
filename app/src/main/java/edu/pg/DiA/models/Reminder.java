package edu.pg.DiA.models;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

import edu.pg.DiA.R;
import edu.pg.DiA.database.AppDatabase;
import edu.pg.DiA.database.dao.MedicineDao;
import edu.pg.DiA.database.dao.MedicineReminderDao;
import edu.pg.DiA.database.dao.UnitDao;
import edu.pg.DiA.helpers.TimestampConverter;
import edu.pg.DiA.ui.reminder.Receiver;

@Entity(tableName = "reminder",
        foreignKeys = {
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "id",
                        childColumns = "user_id",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {@Index("user_id")}
)
public class Reminder {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int rId;

    @ColumnInfo(name = "user_id")
    public int uId;

    @ColumnInfo(name = "alarm")
    public String alarm;

    @ColumnInfo(name = "weekday")
    public String weekday;

    @ColumnInfo(name = "time")
    public String time;

    @ColumnInfo(name = "date")
    @TypeConverters({TimestampConverter.class})
    public Date date;

    public Reminder(int rId, int uId, String alarm, @Nullable String weekday, String time, @Nullable Date date) {
        this.rId = rId;
        this.uId = uId;
        this.alarm = alarm;
        this.weekday = weekday;
        this.time = time;
        this.date = date;
    }

    public String getNotificationTitle() {
        if(this.alarm.equals("lekarstwo")) {
            return "PRZYPOMNIENIE O LEKU";
        }
        else if(this.alarm.equals("pomiar glukozy")) {
            return "PRZYPOMNIENIE O POMIARZE GLUKOZY";
        }
        return "";
    }

    public String getNotificationContent(Context context) {

        if(this.alarm.equals("lekarstwo")) {

            AppDatabase db = AppDatabase.getInstance(context);
            MedicineReminderDao medicineReminderDao = db.medicineReminderDao();
            MedicineDao medicineDao = db.medicineDao();
            UnitDao unitDao = db.unitDao();

            int medicineId = medicineReminderDao.getMedicineId(this.rId);
            String unit = unitDao.getName(medicineDao.getUnitId(medicineId));

            return medicineDao.getName(medicineId)  + " dawka: " + medicineReminderDao.getDose(this.rId) + " " + unit;
        }
        else if(this.alarm.equals("pomiar glukozy")) {
            return "Zmierz poziom glukozy we krwi";
        }
        return "";
    }

    public int getNotificationIcon() {

        if(this.alarm.equals("lekarstwo")) {
            return R.drawable.ic_baseline_local_pharmacy_24;
        }
        else if(this.alarm.equals("pomiar glukozy")) {
            return R.drawable.ic_baseline_local_drink_24;
        }
        return 0;
    }

    public void deleteReminder(Context context) {
        Intent intent = new Intent(context, Receiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, this.rId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);

        AppDatabase.getInstance(context).reminderDao().delete(this);
    }
}
