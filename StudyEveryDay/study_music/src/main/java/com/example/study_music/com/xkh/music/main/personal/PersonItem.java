package com.example.study_music.com.xkh.music.main.personal;

public class PersonItem {
    private int resId;
    private String title;
    private boolean hasMore;

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public PersonItem(int resId, String title, boolean hasMore) {
        this.resId = resId;
        this.title = title;
        this.hasMore = hasMore;
    }

    public PersonItem(int resId, String title) {
        this.resId = resId;
        this.title = title;
    }
}
