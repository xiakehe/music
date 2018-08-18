package com.example.study_music.com.xkh.music.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ArtistInfo implements Parcelable {

    private String artist_name;
    private int number_of_tracks;
    private long artist_id;
    private String artist_sort;

    public ArtistInfo() {
    }

    public ArtistInfo(Parcel in) {
        artist_name = in.readString();
        number_of_tracks = in.readInt();
        artist_id = in.readLong();
        artist_sort = in.readString();
    }

    public String getArtist_name() {
        return artist_name;
    }

    public void setArtist_name(String artist_name) {
        this.artist_name = artist_name;
    }

    public int getNumber_of_tracks() {
        return number_of_tracks;
    }

    public void setNumber_of_tracks(int number_of_tracks) {
        this.number_of_tracks = number_of_tracks;
    }

    public long getArtist_id() {
        return artist_id;
    }

    public void setArtist_id(long artist_id) {
        this.artist_id = artist_id;
    }

    public String getArtist_sort() {
        return artist_sort;
    }

    public void setArtist_sort(String artist_sort) {
        this.artist_sort = artist_sort;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(artist_name);
        dest.writeInt(number_of_tracks);
        dest.writeLong(artist_id);
        dest.writeString(artist_sort);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ArtistInfo> CREATOR = new Creator<ArtistInfo>() {
        @Override
        public ArtistInfo createFromParcel(Parcel in) {
            return new ArtistInfo(in);
        }

        @Override
        public ArtistInfo[] newArray(int size) {
            return new ArtistInfo[size];
        }
    };
}
