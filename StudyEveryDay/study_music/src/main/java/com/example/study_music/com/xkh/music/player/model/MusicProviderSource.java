package com.example.study_music.com.xkh.music.player.model;


import android.support.v4.media.MediaMetadataCompat;

import com.example.study_music.com.xkh.music.main.index.bean.Album;

import java.util.List;

public interface MusicProviderSource {
    String CUSTOM_METADATA_TRACK_SOURCE = "__SOURCE__";
    String CUSTOM_LOAD_METADATA_TRACK_SOURCE_EVENT = "load_music_event";


    void requestMusicByType(String type, final String offset, String size, final ILoadResult<List<MediaMetadataCompat>> listListener);

    void requestAlbumMusicList(String albumId, final ILoadResult<Album> albumILoadResult);



}
