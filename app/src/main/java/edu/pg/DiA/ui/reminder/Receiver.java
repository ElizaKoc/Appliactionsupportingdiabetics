package edu.pg.DiA.ui.reminder;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Random;

import edu.pg.DiA.MainActivity;
import edu.pg.DiA.R;
import edu.pg.DiA.database.AppDatabase;
import edu.pg.DiA.database.dao.MedicineDao;
import edu.pg.DiA.database.dao.MedicineReminderDao;
import edu.pg.DiA.database.dao.ReminderDao;
import edu.pg.DiA.database.dao.UnitDao;
import edu.pg.DiA.models.MedicineReminder;
import edu.pg.DiA.models.Reminder;

import static edu.pg.DiA.DiA.CHANNEL_1_ID;

public class Receiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        int reminderId = intent.getIntExtra("reminder_id", 0);
        String alarmType = intent.getStringExtra("alarm_type");

        AppDatabase db = AppDatabase.getInstance(context);
        ReminderDao reminderDao = db.reminderDao();
        Reminder reminder = reminderDao.getReminderById(reminderId);

        int iconReminder = reminder.getNotificationIcon();
        String contentTitle = reminder.getNotificationTitle();
        String contentText = reminder.getNotificationContent(context);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_1_ID)
                .setSmallIcon(iconReminder)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setPriority(Notification.PRIORITY_HIGH)
                .setCategory(Notification.CATEGORY_REMINDER)
                .setOngoing(true)
                .setAutoCancel(true);

        Intent notifyIntent = new Intent(context, MainActivity.class);

        notifyIntent.putExtra("reminder_id", reminderId);
        notifyIntent.putExtra("alarm_type", alarmType);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, new Random().nextInt(4000000), notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //to be able to launch activity from the notification
        builder.setContentIntent(pendingIntent);
        Notification notificationCompat = builder.build();
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        managerCompat.notify(reminderId, notificationCompat);


        /*Intent intentNew = new Intent(context, IntentServiceReminder.class);
        intentNew.putExtra("reminder_id", reminderId);
        context.startService(intentNew);*/
    }
}
