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

import static edu.pg.DiA.DiA.CHANNEL_1_ID;

public class Receiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        int reminderId = intent.getIntExtra("reminder_id", 0);
        int iconReminder = intent.getIntExtra("icon", 0);
        String alarmType = intent.getStringExtra("alarm_type");
        String contentTitle = intent.getStringExtra("content_title");
        String contentText = intent.getStringExtra("content_text");

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
