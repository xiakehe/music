package com.example.study_core.util;

import java.lang.reflect.ParameterizedType;


public class TUtil {
    @SuppressWarnings("unchecked")
    public static <T> T getT(Object o, int i) {
        try {
            return ((Class<T>) ((ParameterizedType) o.getClass()
                    .getGenericSuperclass()).getActualTypeArguments()[i])
                    .newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassCastException ignored) {
        }
        return null;
    }

    public static Class<?> forName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
