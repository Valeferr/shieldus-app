package com.example.shieldus.persistence;

import android.content.Context;
import android.content.SharedPreferences;

public class ProgressManager {
    private static final String PREFS_NAME = "progress_prefs";
    private static final String PREFIX_CURRENT = "current_";

    public static void saveQuizState(Context context, String moduleId, int progress, int currentQuestion, int score) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit()
                .putInt(moduleId, progress)
                .putInt(PREFIX_CURRENT + moduleId + "_question", currentQuestion)
                .apply();
    }

    public static int getQuizState(Context context, String moduleId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(PREFIX_CURRENT + moduleId + "_question", 0);
    }

    public static void saveProgress(Context context, String moduleId, int progress) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        int currentProgress = prefs.getInt(moduleId, 0);
        if (progress > currentProgress) {
            prefs.edit().putInt(moduleId, progress).apply();
        }
    }

    public static int getProgress(Context context, String moduleId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        return prefs.getInt(moduleId, 0);
    }
}