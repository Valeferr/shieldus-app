package com.example.shieldus.persistence;

import android.content.Context;
import android.content.SharedPreferences;

public class ProgressManager {
    private static final String PREFS_NAME = "progress_prefs";

    public static void saveProgress(Context context, String moduleId, int progress) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putInt(moduleId, progress).apply();
    }

    public static int getProgress(Context context, String moduleId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(moduleId, 0);
    }
}