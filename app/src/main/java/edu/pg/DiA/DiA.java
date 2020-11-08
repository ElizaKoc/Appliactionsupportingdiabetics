package edu.pg.DiA;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.os.Build;

import com.jakewharton.threetenabp.AndroidThreeTen;

import edu.pg.DiA.database.AppDatabase;

public class DiA extends Application {

    public static final String CHANNEL_1_ID = "reminder";

    @Override
    public void onCreate() {
        super.onCreate();

        AndroidThreeTen.init(this);
        createNotificationChannels();
    }

    private void createNotificationChannels() {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel reminder = new NotificationChannel(
                    CHANNEL_1_ID,
                    "reminder",
                    NotificationManager.IMPORTANCE_HIGH
            );
            reminder.setDescription("This is Channel Reminder");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(reminder);
        }
    }
}
