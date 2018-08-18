package com.example.study_music.com.xkh.music.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.study_core.app.Study;
import com.example.study_core.util.storage.StudySharedPreference;

public class Preferences {

    private static final String PLAY_POSITION = "play_position";
    private static final String PLAY_MODE = "play_mode";
    private static final String SPLASH_URL = "splash_url";
    private static final String NIGHT_MODE = "night_mode";
    private static final String FILTER_SIZE = "filter_size";
    private static final String FILTER_TIME = "filter_time";
    private static final Context sContext = Study.getApplicationContext();

    public static String getFilterSize() {
        return StudySharedPreference.getString(FILTER_SIZE, "0");
    }

    public static void saveFilterSize(String value) {
        StudySharedPreference.setSting(FILTER_SIZE, value);
    }

    public static String getFilterTime() {
        return StudySharedPreference.getString(FILTER_TIME, "0");
    }

    public static void saveFilterTime(String value) {
        StudySharedPreference.setSting(FILTER_TIME, value);
    }

    public static int getPlayMode() {
        return getInt(PLAY_MODE, 0);
    }
    private static int getInt(String key, int defValue) {
        return getPreferences().getInt(key, defValue);
    }
    private static SharedPreferences getPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(sContext);
    }
    public static void savePlayMode(int mode) {
        saveInt(PLAY_MODE, mode);
    }

    private static void saveInt(String key, int value) {
        getPreferences().edit().putInt(key, value).apply();
    }
}
