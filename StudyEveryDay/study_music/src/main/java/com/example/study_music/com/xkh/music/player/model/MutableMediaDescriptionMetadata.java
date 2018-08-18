package com.example.study_music.com.xkh.music.player.model;

import android.support.v4.media.MediaDescriptionCompat;
import android.text.TextUtils;

public class MutableMediaDescriptionMetadata {

    private MediaDescriptionCompat mediaDescriptionCompat;
    private long queueId;

    public MutableMediaDescriptionMetadata(MediaDescriptionCompat mediaDescriptionCompat, long queueId) {
        this.mediaDescriptionCompat = mediaDescriptionCompat;
        this.queueId = queueId;
    }

    public MutableMediaDescriptionMetadata(MediaDescriptionCompat mediaDescriptionCompat) {
        this.mediaDescriptionCompat = mediaDescriptionCompat;
    }

    public long getQueueId() {
        return queueId;
    }

    public MediaDescriptionCompat getMediaDescriptionCompat() {
        return mediaDescriptionCompat;
    }

    @Override
    public String toString() {
        return "MutableMediaDescriptionMetadata{" +
                "mediaDescriptionCompat=" + mediaDescriptionCompat +
                ", queueId=" + queueId +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof MutableMediaDescriptionMetadata) {
            MutableMediaDescriptionMetadata o = (MutableMediaDescriptionMetadata) obj;
            return TextUtils.equals(o.getMediaDescriptionCompat().getMediaId(), this.mediaDescriptionCompat.getMediaId());
        }

        return false;
    }
}
