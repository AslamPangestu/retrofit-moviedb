package com.mvryan.katalogfilm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.mvryan.katalogfilm.fragment.FavouriteFragment;
import com.mvryan.katalogfilm.fragment.HomeFragment;
import com.mvryan.katalogfilm.fragment.NavDrawerFragment;
import com.mvryan.katalogfilm.fragment.SearchFragment;
import com.mvryan.katalogfilm.fragment.SettingFragment;
import com.mvryan.katalogfilm.utils.CacheManager;
import com.mvryan.katalogfilm.utils.listener.FragmentDrawerListener;

public class MainActivity extends AppCompatActivity implements FragmentDrawerListener{

    private Toolbar toolbar;
    private NavDrawerFragment drawerFragment;

    public static final int NOTIFICAITION_ID = 1;
    private NotificationCompat.Builder notification;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(2);

        drawerFragment = (NavDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_nav_drawer);
        drawerFragment.setUp(R.id.fragment_nav_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
        drawerFragment.setFragmentDrawerListener(this);

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            displayView(CacheManager.grab("position", 0));
        } else {
            if (CacheManager.isExist("position")) {
                displayView(CacheManager.grab("position", 0));
            } else {
                displayView(0);
            }
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(NOTIFICAITION_ID, notification.build());
        }
    };

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                title = getString(R.string.app_name);
                break;
            case 1:
                fragment = new SearchFragment();
                title = getString(R.string.nav_item_search);
                break;
            case 2:
                fragment = new SettingFragment();
                title = getString(R.string.nav_item_setting);
                break;
            case 3:
                fragment = new FavouriteFragment();
                title = getString(R.string.nav_item_favourite);
                break;
            default:
                break;
        }

        CacheManager.save("position", position);

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
