package com.example.study_core.delegate.buttom;

public final class BottomTabBean {

    private final String title;
    private final String icon;

    public BottomTabBean(String icon, String title) {
        this.title = title;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public String getIcon() {
        return icon;
    }
}
