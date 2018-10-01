package com.mvryan.katalogfilm.utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.mvryan.katalogfilm.KatalogFilmApp;

public class CacheManager {

    private static final String TAG = "CacheManager";

    private static SharedPreferences getPref() {
        return PreferenceManager.getDefaultSharedPreferences(KatalogFilmApp.getContext());
    }

    public static Boolean isExist(String key) {
        return getPref().contains(key);
    }

    public static void save(String key, String value) {
        Log.d(TAG, "saveCache: " + value);
        getPref().edit().putString(key, value).apply();
    }

    public static void save(String key, int value) {
        Log.d(TAG, "saveCache: " + value);
        getPref().edit().putInt(key, value).apply();
    }

    public static String grab(String key) {
        return getPref().getString(key, null);
    }

    public static Integer grab(String key, int defaultValue) {
        return getPref().getInt(key, defaultValue);
    }
}