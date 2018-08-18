package com.example.study_music.com.xkh.music.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MusicDownloadInfo implements Serializable {
    @SerializedName("bitrate")
    private Bitrate bitrate;

    public Bitrate getBitrate() {
        return bitrate;
    }

    public static class Bitrate implements Serializable {
        @SerializedName("file_duration")
        private int file_duration;
        @SerializedName("file_link")
        private String file_link;

        public int getFile_duration() {
            return file_duration;
        }


        public String getFile_link() {
            return file_link;
        }

        @Override
        public String toString() {
            return "Bitrate{" +
                    "file_duration=" + file_duration +
                    ", file_link='" + file_link + '\'' +
                    '}';
        }
    }



    @Override
    public String toString() {
        return "MusicDownloadInfo{" +
                "bitrate=" + bitrate +
                '}';
    }
}
