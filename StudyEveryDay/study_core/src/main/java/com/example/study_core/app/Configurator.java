package com.example.study_core.app;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;

import com.example.study_core.util.storage.StudySharedPreference;
import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.WeakHashMap;

import okhttp3.Interceptor;

public final class Configurator {

    private static final WeakHashMap<Object, Object> STUDY_CONFERS = new WeakHashMap<>();
    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();
    private static final Handler HANDLER = new Handler();
    private static final ArrayList<IconFontDescriptor> ICON = new ArrayList<>();

    private Configurator() {
        STUDY_CONFERS.put(ConfigKeys.CONFIG_READY, false);
        STUDY_CONFERS.put(ConfigKeys.HANDLER, HANDLER);
    }

    static Configurator getInstance() {
        return ConfiguratorHolder.INSTANCE;
    }

    private void initIcons() {
        if (ICON.size() > 0) {
            Iconify.IconifyInitializer iconifyInitializer = Iconify.with(ICON.get(0));
            for (int i = 1; i < ICON.size(); i++) {
                iconifyInitializer.with(ICON.get(i));
            }
        }
    }

    private static class ConfiguratorHolder {
        private static final Configurator INSTANCE = new Configurator();
    }

    final WeakHashMap<Object, Object> getStudyConfers() {
        return STUDY_CONFERS;
    }

    public final void configDone() {
        initIcons();
        Logger.addLogAdapter(new AndroidLogAdapter());
        STUDY_CONFERS.put(ConfigKeys.CONFIG_READY, true);
    }

    public final Configurator withIcon(IconFontDescriptor icon) {
        ICON.add(icon);
        return this;
    }

    public final Configurator withApiHost(String apiHost) {
        if (!TextUtils.isEmpty(apiHost)) {
            STUDY_CONFERS.put(ConfigKeys.API_HOST, apiHost);
        }
        return this;
    }

    public final Configurator withActivity(Activity activity) {
        STUDY_CONFERS.put(ConfigKeys.ACTIVITY, activity);
        return this;
    }

    public final Configurator withApplicationContext(Context applicationContext) {
        STUDY_CONFERS.put(ConfigKeys.APPLICATION_CONTEXT, applicationContext);
        return this;
    }

    public final Configurator withInterceptors(Interceptor interceptor) {
        INTERCEPTORS.add(interceptor);
        STUDY_CONFERS.put(ConfigKeys.INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    private void checkConfigIsDone() {
        boolean done = (boolean) STUDY_CONFERS.get(ConfigKeys.CONFIG_READY);
        if (!done) {
            throw new RuntimeException("config is not done");
        }
    }

    @SuppressWarnings("unchecked")
    final <T> T getConfiguration(Object object) {
        checkConfigIsDone();
        final Object obj = STUDY_CONFERS.get(object);
        if (obj == null) {
            return null;
        } else return (T) obj;
    }

}
