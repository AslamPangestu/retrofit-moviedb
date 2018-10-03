package com.mvryan.katalogfilm.utils.notif;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.mvryan.katalogfilm.MainActivity;
import com.mvryan.katalogfilm.R;

import java.util.Calendar;

import static android.provider.AlarmClock.EXTRA_MESSAGE;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.SECOND;

/**
 * Created by mvryan on 03/10/18.
 */

public class DailyNotif extends BroadcastReceiver {

    private final int DAILY_REMINDER = 100;

    @Override
    public void onReceive(Context context, Intent intent) {
        showNotif(context,DAILY_REMINDER);
    }

    public void offReminder(Context context){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DailyNotif.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, DAILY_REMINDER, intent, 0);
        alarmManager.cancel(pendingIntent);

        Toast.makeText(context, R.string.off_remainder_daily, Toast.LENGTH_SHORT).show();
    }

    public void onReminder(Context context){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DailyNotif.class);
        intent.putExtra(EXTRA_MESSAGE, R.string.content_text_daily);

        Calendar calendar = Calendar.getInstance();

        calendar.set(HOUR_OF_DAY,21);
        calendar.set(MINUTE,50);
        calendar.set(SECOND,0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,DAILY_REMINDER, intent,0);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
        Toast.makeText(context,R.string.on_remainder_daily,Toast.LENGTH_SHORT).show();
    }

    private void showNotif(Context context, int id){

        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel mChannel = null;
        NotificationCompat.Builder builder;

        String channelId = "daily_channel";
        CharSequence channelName = "daily notif chanel";
        int importance = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            importance = NotificationManager.IMPORTANCE_HIGH;
        }

        Intent notificationIntent;
        notificationIntent = new Intent(context,MainActivity.class);
        notificationIntent.setAction(Intent.ACTION_MAIN);
        notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        builder = new NotificationCompat.Builder(context,channelId)
                .setSmallIcon(R.mipmap.ic_notif)
                .setContentTitle(context.getResources().getString(R.string.content_title_daily))
                .setContentText(context.getResources().getString(R.string.content_text_daily))
                .setSubText(context.getResources().getString(R.string.subtext))
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(channelId, channelName, importance);
            notificationManagerCompat.createNotificationChannel(mChannel);
        }

        builder.build().flags|= Notification.FLAG_AUTO_CANCEL;
        PendingIntent pendingIntent = PendingIntent.getActivity(context,1,notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        notificationManagerCompat.notify(id, builder.build());
    }
}
