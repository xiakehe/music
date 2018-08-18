package com.example.study_music.com.xkh.music.util;


import android.support.v4.media.MediaDescriptionCompat;
import android.text.TextUtils;
import android.util.Log;
import android.util.LongSparseArray;

import com.example.study_music.com.xkh.music.player.model.MutableMediaDescriptionMetadata;

import java.util.ArrayList;
import java.util.List;


public class PlayQueueUtils {

    private PlayQueueUtils() {
    }

    private static class PlayQueueUtilsHolder {
        private static PlayQueueUtils HOLDER = new PlayQueueUtils();
    }

    public static PlayQueueUtils getInstance() {
        return PlayQueueUtilsHolder.HOLDER;
    }

    private MediaDescriptionCompat curPlayData = null;
    private MediaDescriptionCompat lastPlayData = null;

    private LongSparseArray<MediaDescriptionCompat> sparseArray = new LongSparseArray<>();
    private List<MutableMediaDescriptionMetadata> data = new ArrayList<>();
    private boolean hasInit = false;
    private CharSequence title;


    public void initData(List<MutableMediaDescriptionMetadata> list) {

        if (hasInit) {
            return;
        }
        for (MutableMediaDescriptionMetadata mu : list) {
            sparseArray.put(mu.getQueueId(), mu.getMediaDescriptionCompat());
        }
        if (list.size() > 0) {
            data.addAll(list);
        }
        hasInit = true;
    }

    public void clearData() {
        data.clear();
        sparseArray.clear();
        hasInit = false;
    }

    public boolean isHasInit(CharSequence title) {
        if (!TextUtils.equals(this.title, title)) {
            clearData();
        }
        this.title = title;
        return hasInit;
    }

    public List<MutableMediaDescriptionMetadata> getData() {
        return data;
    }

    private MediaDescriptionCompat getDataByQueueId(long id) {
        return sparseArray.get(id);
    }

    public boolean isCurPlayData(MediaDescriptionCompat data) {

        return curPlayData != null && (curPlayData == data || TextUtils.equals(curPlayData.getMediaId(), data.getMediaId()));

    }

    public void updateDataByQueueId(long id) {
        updateData(getDataByQueueId(id));
    }

    public MediaDescriptionCompat getListDataByPosition(int position) {
        return data.get(position).getMediaDescriptionCompat();
    }

    public MediaDescriptionCompat updateData(MediaDescriptionCompat newData) {
        if (curPlayData == null) {
            lastPlayData = curPlayData = newData;
        } else {
            lastPlayData = curPlayData;
            curPlayData = newData;
        }
        return curPlayData;


    }

    public MediaDescriptionCompat getCurPlayData() {
        return curPlayData;
    }


    public MediaDescriptionCompat getLastPlayData() {
        return lastPlayData;
    }


    private void updateData(long id) {
        MediaDescriptionCompat newData = getDataByQueueId(id);
        updateData(newData);
    }

    public int getCurDataPosition() {
        if (curPlayData != null) {
            return getDataPosition(new MutableMediaDescriptionMetadata(lastPlayData));
        }
        return -1;
    }

    public int getLastDataPosition() {
        if (lastPlayData != null) {
            return getDataPosition(new MutableMediaDescriptionMetadata(lastPlayData));
        }
        return -1;
    }

    public int getDataPosition(MutableMediaDescriptionMetadata item) {

        return data != null && !data.isEmpty() ? data.indexOf(item) : -1;
    }

    public int getDataPosition(int position) {
        if (position != -1) {
            return getDataPosition(data.get(position));
        }
        return -1;
    }

}
