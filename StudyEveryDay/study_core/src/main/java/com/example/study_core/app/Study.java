package com.example.study_core.app;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

public final class Study {
    public static Configurator appInit(Context context) {
        Configurator.getInstance().getStudyConfers().put(ConfigKeys.APPLICATION_CONTEXT, context.getApplicationContext());
        return getConfigurator();
    }

    public static Configurator getConfigurator() {
        return Configurator.getInstance();
    }

    public static <T> T getConfiguration(Object object) {
        return getConfigurator().getConfiguration(object);
    }

    public static Context getApplicationContext() {
        return getConfiguration(ConfigKeys.APPLICATION_CONTEXT);
    }

    public static Handler getHandler() {
        return getConfiguration(ConfigKeys.HANDLER);
    }

    public static String getApiHost() {
        return getConfiguration(ConfigKeys.API_HOST);
    }

    public static void test(String test) {
        if (test != null && !test.isEmpty()) {
            Toast.makeText(getApplicationContext(), test, Toast.LENGTH_LONG).show();
        }
    }

    public static boolean isUserLogin() {
        return AccountManager.isSign();
    }

}
