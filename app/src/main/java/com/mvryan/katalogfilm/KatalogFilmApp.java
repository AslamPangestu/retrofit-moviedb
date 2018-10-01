package com.mvryan.katalogfilm;

import android.app.Application;
import android.content.Context;

/**
 * Created by mvryan on 01/10/18.
 */

public class KatalogFilmApp extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext() {
        return context;
    }
}
