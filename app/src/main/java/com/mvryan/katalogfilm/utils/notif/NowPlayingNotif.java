package com.mvryan.katalogfilm.utils.notif;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.mvryan.katalogfilm.BuildConfig;
import com.mvryan.katalogfilm.FilmDetailActivity;
import com.mvryan.katalogfilm.R;
import com.mvryan.katalogfilm.model.Film;
import com.mvryan.katalogfilm.model.Result;
import com.mvryan.katalogfilm.network.Networks;
import com.mvryan.katalogfilm.network.Routes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.provider.AlarmClock.EXTRA_MESSAGE;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.SECOND;

/**
 * Created by mvryan on 03/10/18.
 */

public class NowPlayingNotif extends BroadcastReceiver {

    private final int NOW_PLAYING = 101;
    private List<Film> films = new ArrayList<>();
    Film film;
    String lang;

    @Override
    public void onReceive(Context context, Intent intent) {

        film = new Film();
        Calendar calendar= Calendar.getInstance();
        Date today = calendar.getTime();

        String outputPattern = "EEE, MMM d, yyyy";
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        String todayAsString = outputFormat.format(today);

        if (Locale.getDefault().getLanguage().equals("en")){
            lang = BuildConfig.LANG_DBMOVIE_US;
        }else if (Locale.getDefault().getLanguage().equals("in")){
            lang = BuildConfig.LANG_DBMOVIE_ID;
        }

        getNowPlaying(context,todayAsString,film);

        for(Film film: films){
            film = new Film();
            showNotif(context,film);
        }
    }

    public void offReminder(Context context){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NowPlayingNotif.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOW_PLAYING, intent, 0);
        alarmManager.cancel(pendingIntent);

        Toast.makeText(context, R.string.off_remainder_now_playing, Toast.LENGTH_SHORT).show();
    }

    public void onReminder(Context context){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NowPlayingNotif.class);
        intent.putExtra(EXTRA_MESSAGE, R.string.content_text_nowplaying);

        Calendar calendar = Calendar.getInstance();

        calendar.set(HOUR_OF_DAY,21);
        calendar.set(MINUTE,50);
        calendar.set(SECOND,0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,NOW_PLAYING, intent,0);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
        Toast.makeText(context,R.string.on_remainder_now_playing,Toast.LENGTH_SHORT).show();
    }

    private void showNotif(Context context, Film film){

        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder;
        NotificationChannel mChannel = null;

        String channelId = "nowplaying_channel";
        CharSequence channelName = "dicoding channel";
        int importance = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            importance = NotificationManager.IMPORTANCE_HIGH;
        }

        Intent notificationIntent;
        notificationIntent = new Intent(context, FilmDetailActivity.class);
        notificationIntent.putExtra(FilmDetailActivity.EXTRA,film);

        String message = film.getTitle()
                + context.getResources().getString(R.string.content_text_nowplaying);

        builder = new NotificationCompat.Builder(context,channelId)
                .setSmallIcon(R.mipmap.ic_notif)
                .setContentTitle(context.getResources().getString(R.string.content_title_nowplaying))
                .setContentText(message)
                .setSubText(context.getResources().getString(R.string.subtext))
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent));
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(channelId, channelName, importance);
            notificationManagerCompat.createNotificationChannel(mChannel);
        }

        builder.build().flags|= Notification.FLAG_AUTO_CANCEL;
        PendingIntent pendingIntent = PendingIntent.getActivity(context,film.getId(),notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        notificationManagerCompat.notify(film.getId(), builder.build());
    }

    private void getNowPlaying(final Context context, final String today, final Film film){
        Routes routes = Networks.filmRequest().create(Routes.class);
        Call<Result> resultCall = routes.getNowPlaying(BuildConfig.API_DBMOVIE, lang);
        resultCall.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(@NonNull Call<Result> call, @NonNull Response<Result> response) {
                if (response.isSuccessful()) {
                    films = response.body().getResults();
                    if (today.equals(film.getRelease_date())){
                        films.add(film);
                    }
                } else {
                    Toast.makeText(context, R.string.error, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Toast.makeText(context, R.string.not_found, Toast.LENGTH_LONG).show();
            }
        });
    }
}
