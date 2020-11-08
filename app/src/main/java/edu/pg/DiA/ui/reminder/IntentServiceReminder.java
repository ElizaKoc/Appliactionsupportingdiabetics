package edu.pg.DiA.ui.reminder;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.JobIntentService;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Random;

import edu.pg.DiA.MainActivity;
import edu.pg.DiA.R;

import static edu.pg.DiA.DiA.CHANNEL_1_ID;

public class IntentServiceReminder extends JobIntentService {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onHandleWork(@NonNull Intent intent) {

        int reminderId = intent.getIntExtra("reminder_id", 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_baseline_local_pharmacy_24)
                .setContentTitle("PRZYPOMNIENIE")
                .setContentText("TEST")
                .setPriority(Notification.PRIORITY_HIGH)
                .setCategory(Notification.CATEGORY_REMINDER);

        Intent notifyIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, new Random().nextInt(4000000), notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //to be able to launch activity from the notification
        builder.setContentIntent(pendingIntent);
        Notification notificationCompat = builder.build();
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(reminderId, notificationCompat);
    }
}
