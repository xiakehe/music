package com.example.study_core.util.storage;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.study_core.app.Study;

public final class StudySharedPreference {
    private static final SharedPreferences PREFERENCES = PreferenceManager.getDefaultSharedPreferences(Study.getApplicationContext());

    private static SharedPreferences getPreferences() {
        return PREFERENCES;
    }

    public static void setAppFlag(String key, boolean value) {
        getPreferences().edit().putBoolean(key, value).apply();
    }

    public static boolean getAppFlag(String key) {
        return getPreferences().getBoolean(key, false);
    }

    public static void setSting(String key, String value) {
        getPreferences().edit().putString(key, value).apply();
    }

    public static String getString(String key,String def) {
        return getPreferences().getString(key, def);
    }





}
