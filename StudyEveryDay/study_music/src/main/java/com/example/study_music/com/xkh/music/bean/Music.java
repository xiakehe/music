package com.example.study_music.com.xkh.music.bean;

import android.text.TextUtils;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "music")
public class Music implements Serializable{
    private static final long serialVersionUID = 536871008;

    @Id(autoincrement = true)
    @Property(nameInDb = "id")
    private Long id;

    @NotNull
    @Property(nameInDb = "type")
    private int type; // 歌曲类型:本地/网络
    @Property(nameInDb = "songId")
    private long songId; // [本地]歌曲ID
    @Property(nameInDb = "title")
    private String title; // 音乐标题
    @Property(nameInDb = "artist")
    private String artist; // 艺术家
    @Property(nameInDb = "album")
    private String album; // 专辑
    @Property(nameInDb = "albumId")
    private long albumId; // [本地]专辑ID
    @Property(nameInDb = "coverPath")
    private String coverPath; // [在线]专辑封面路径
    @NotNull
    @Property(nameInDb = "duration")
    private long duration; // 持续时间
    @NotNull
    @Property(nameInDb = "path")
    private String path; // 播放地址
    @Property(nameInDb = "fileName")
    private String fileName; // [本地]文件名
    @Property(nameInDb = "fileSize")
    private long fileSize; // [本地]文件大小
    @Generated(hash = 332465567)
    public Music(Long id, int type, long songId, String title, String artist,
            String album, long albumId, String coverPath, long duration,
            @NotNull String path, String fileName, long fileSize) {
        this.id = id;
        this.type = type;
        this.songId = songId;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.albumId = albumId;
        this.coverPath = coverPath;
        this.duration = duration;
        this.path = path;
        this.fileName = fileName;
        this.fileSize = fileSize;
    }
    @Generated(hash = 1263212761)
    public Music() {
    }
    public interface Type {
        int LOCAL = 0;
        int ONLINE = 1;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getType() {
        return this.type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public long getSongId() {
        return this.songId;
    }
    public void setSongId(long songId) {
        this.songId = songId;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getArtist() {
        return this.artist;
    }
    public void setArtist(String artist) {
        this.artist = artist;
    }
    public String getAlbum() {
        return this.album;
    }
    public void setAlbum(String album) {
        this.album = album;
    }
    public long getAlbumId() {
        return this.albumId;
    }
    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }
    public String getCoverPath() {
        return this.coverPath;
    }
    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }
    public long getDuration() {
        return this.duration;
    }
    public void setDuration(long duration) {
        this.duration = duration;
    }
    public String getPath() {
        return this.path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public String getFileName() {
        return this.fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public long getFileSize() {
        return this.fileSize;
    }
    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    @Override
    public String toString() {
        return "Music{" +
                "id=" + id +
                ", type=" + type +
                ", songId=" + songId +
                ", title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", album='" + album + '\'' +
                ", albumId=" + albumId +
                ", coverPath='" + coverPath + '\'' +
                ", duration=" + duration +
                ", path='" + path + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileSize=" + fileSize +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Music)) {
            return false;
        }
        Music music = (Music) o;
        return music.songId > 0 && music.songId == this.songId || TextUtils.equals(music.title, this.title) && TextUtils.equals(music.artist, this.artist) && TextUtils.equals(music.album, this.album) && music.duration == this.duration;
    }
}
