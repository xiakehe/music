package com.example.study_music.com.xkh.music.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class AlbumInfo implements Parcelable {

    //专辑名称
    private String album_name;
    //专辑在数据库中的id
    private int album_id = -1;
    //专辑的歌曲数目
    private int number_of_songs = 0;
    //专辑封面图片路径
    private String album_art;
    private String album_artist;
    private String album_sort;

    private AlbumInfo(Parcel in) {
        album_name = in.readString();
        album_id = in.readInt();
        number_of_songs = in.readInt();
        album_art = in.readString();
        album_artist = in.readString();
        album_sort = in.readString();
    }

    public AlbumInfo() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(album_name);
        dest.writeInt(album_id);
        dest.writeInt(number_of_songs);
        dest.writeString(album_art);
        dest.writeString(album_artist);
        dest.writeString(album_sort);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AlbumInfo> CREATOR = new Creator<AlbumInfo>() {
        @Override
        public AlbumInfo createFromParcel(Parcel in) {
            return new AlbumInfo(in);
        }

        @Override
        public AlbumInfo[] newArray(int size) {
            return new AlbumInfo[size];
        }
    };

    public String getAlbum_name() {
        return album_name;
    }

    public void setAlbum_name(String album_name) {
        this.album_name = album_name;
    }

    public int getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(int album_id) {
        this.album_id = album_id;
    }

    public int getNumber_of_songs() {
        return number_of_songs;
    }

    public void setNumber_of_songs(int number_of_songs) {
        this.number_of_songs = number_of_songs;
    }

    public String getAlbum_art() {
        return album_art;
    }

    public void setAlbum_art(String album_art) {
        this.album_art = album_art;
    }

    public String getAlbum_artist() {
        return album_artist;
    }

    public void setAlbum_artist(String album_artist) {
        this.album_artist = album_artist;
    }

    public String getAlbum_sort() {
        return album_sort;
    }

    public void setAlbum_sort(String album_sort) {
        this.album_sort = album_sort;
    }
}
