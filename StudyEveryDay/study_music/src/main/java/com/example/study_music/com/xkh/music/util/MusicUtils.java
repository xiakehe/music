package com.example.study_music.com.xkh.music.util;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.MediaStore;

import com.example.study_core.util.ParseUtils;
import com.example.study_music.com.xkh.music.bean.AlbumInfo;
import com.example.study_music.com.xkh.music.bean.ArtistInfo;
import com.example.study_music.com.xkh.music.bean.FolderInfo;
import com.example.study_music.com.xkh.music.bean.Music;
import com.github.promeg.pinyinhelper.Pinyin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MusicUtils {

    private static final String SELECTION = MediaStore.Audio.AudioColumns.SIZE + " >= ? AND " + MediaStore.Audio.AudioColumns.DURATION + " >= ?";

    private static String[] PRO_MUSIC = new String[]{
            BaseColumns._ID, MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.ALBUM, MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.AudioColumns.IS_MUSIC,
            MediaStore.Audio.Media.DISPLAY_NAME, MediaStore.Audio.Media.DURATION, MediaStore.Audio.Media.SIZE};
    private static String[] PRO_ALBUM = new String[]{MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART,
            MediaStore.Audio.Albums.ALBUM, MediaStore.Audio.Albums.NUMBER_OF_SONGS, MediaStore.Audio.Albums.ARTIST};
    private static String[] PRO_ARTIST = new String[]{
            MediaStore.Audio.Artists.ARTIST,
            MediaStore.Audio.Artists.NUMBER_OF_TRACKS,
            MediaStore.Audio.Artists._ID};
    private static String[] PRO_FORDER = new String[]{MediaStore.Files.FileColumns.DATA};

    public static ArrayList<Music> scanMusic(Context context) {
        ArrayList<Music> musicArrayList = new ArrayList<>();
        long music_size = ParseUtils.parseLong(Preferences.getFilterSize()) * 1024;
        long music_time = ParseUtils.parseLong(Preferences.getFilterTime()) * 1000;

        Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                PRO_MUSIC,
                SELECTION, new String[]{
                        String.valueOf(music_size),
                        String.valueOf(music_time)
                }, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        if (cursor == null) {
            return musicArrayList;
        }
        while (cursor.moveToNext()) {
            // 是否为音乐，魅族手机上始终为0
            int isMusic = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.IS_MUSIC));
            if (isMusic == 0) {
                continue;
            }

            long id = cursor.getLong(cursor.getColumnIndex(BaseColumns._ID));
            String title = cursor.getString((cursor.getColumnIndex(MediaStore.Audio.AudioColumns.TITLE)));
            String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST));
            String album = cursor.getString((cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM)));
            long albumId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM_ID));
            long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATA));
            String fileName = cursor.getString((cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DISPLAY_NAME)));
            long fileSize = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));

            Music music = new Music();
            music.setSongId(id);
            music.setType(Music.Type.LOCAL);
            music.setTitle(title);
            music.setArtist(artist);
            music.setAlbum(album);
            music.setAlbumId(albumId);
            music.setDuration(duration);
            music.setPath(path);
            music.setFileName(fileName);
            music.setFileSize(fileSize);
            musicArrayList.add(music);
        }
        cursor.close();
        return musicArrayList;
    }

    public static Uri getMediaStoreAlbumCoverUri(long albumId) {
        Uri artworkUri = Uri.parse("content://media/external/audio/albumart");
        return ContentUris.withAppendedId(artworkUri, albumId);
    }

    /**
     * 获取歌手信息
     *
     * @param context context
     * @return artist list
     */
    public static List<ArtistInfo> queryArtist(Context context) {

        Uri uri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
        ContentResolver cr = context.getContentResolver();

        Cursor cursor = cr.query(uri, PRO_ARTIST,
                null, null, MediaStore.Audio.Artists.DEFAULT_SORT_ORDER);
        List<ArtistInfo> list = new ArrayList<>();
        while (cursor != null && cursor.moveToNext()) {
            ArtistInfo info = new ArtistInfo();
            info.setArtist_name(cursor.getString(cursor
                    .getColumnIndex(MediaStore.Audio.Artists.ARTIST)));
            info.setNumber_of_tracks(cursor.getInt(cursor
                    .getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_TRACKS)));
            info.setArtist_id(cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Artists._ID)));
            info.setArtist_sort(Pinyin.toPinyin(info.getArtist_name().charAt(0)).substring(0, 1).toUpperCase());
            list.add(info);
        }
        if (cursor != null) {
            cursor.close();
        }
        return list;
    }

    /**
     * 获取包含音频文件的文件夹信息
     *
     * @param context context
     * @return list
     */
    public static List<FolderInfo> queryFolder(Context context) {

        Uri uri = MediaStore.Files.getContentUri("external");
        ContentResolver cr = context.getContentResolver();
        String mSelection = MediaStore.Files.FileColumns.MEDIA_TYPE
                + " = " + MediaStore.Files.FileColumns.MEDIA_TYPE_AUDIO + " and " + "("
                + MediaStore.Files.FileColumns.DATA + " like'%.mp3' or " + MediaStore.Audio.Media.DATA
                + " like'%.wma')" +
                ") group by ( " + MediaStore.Files.FileColumns.PARENT;
        // 查询语句：检索出.mp3为后缀名，时长大于1分钟，文件大小大于1MB的媒体文件
        Cursor cursor = cr.query(uri, PRO_FORDER, mSelection, null,
                null);

        List<FolderInfo> list = new ArrayList<>();
        while (cursor != null && cursor.moveToNext()) {
            FolderInfo info = new FolderInfo();
            String filePath = cursor.getString(cursor
                    .getColumnIndex(MediaStore.Files.FileColumns.DATA));
            info.setFolder_path(filePath.substring(0, filePath.lastIndexOf(File.separator)));
            info.setFolder_name(info.getFolder_path().substring(info.getFolder_path()
                    .lastIndexOf(File.separator) + 1));
            info.setFolder_sort(Pinyin.toPinyin(info.getFolder_name().charAt(0)).substring(0, 1).toUpperCase());
            list.add(info);
        }
        if (cursor != null) {
            cursor.close();
        }

        return list;

    }

    /**
     * 获取专辑信息
     *
     * @param context context
     * @return list
     */
    public static List<AlbumInfo> queryAlbums(Context context) {

        ContentResolver cr = context.getContentResolver();

        // Media.ALBUM_KEY 按专辑名称排序
        // List<AlbumInfo> list = getAlbumList(

        Cursor cursor = cr.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, PRO_ALBUM,
                null, null, MediaStore.Audio.Albums.DEFAULT_SORT_ORDER);

        List<AlbumInfo> list = new ArrayList<>();
        while (cursor != null && cursor.moveToNext()) {
            AlbumInfo info = new AlbumInfo();
            info.setAlbum_name(cursor.getString(cursor
                    .getColumnIndex(MediaStore.Audio.Albums.ALBUM)));
            info.setAlbum_id(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Albums._ID)));
            info.setNumber_of_songs(cursor.getInt(cursor
                    .getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS)));
            info.setAlbum_art(getMediaStoreAlbumCoverUri(info.getAlbum_id()).toString());
            info.setAlbum_artist(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST)));
            info.setAlbum_sort(Pinyin.toPinyin(info.getAlbum_name().charAt(0)).substring(0, 1).toUpperCase());
            list.add(info);
        }
        if (cursor != null) {
            cursor.close();

        }
        return list;

    }

}
