package com.example.study_music.com.xkh.music.player.model;


import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


public class MusicProvider {


    private static final String TAG = "MusicProvider";
    //根据type 存储音乐集合
    private ConcurrentMap<String, List<MediaMetadataCompat>> mMusicListByType;
    //根据id 存储音乐对象
    private ConcurrentMap<String, MutableMediaMetadata> mMusicListById;
    //保存收藏的音乐id
    private Set<String> mFavoriteTracks;

    private MusicProviderSource providerSource;


    public MusicProvider() {
        providerSource = new NetWorkMusicSourceImp();
        mMusicListByType = new ConcurrentHashMap<>();
        mMusicListById = new ConcurrentHashMap<>();
        mFavoriteTracks = Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>());
    }

    //获取所有type的迭代器
    public Iterable<String> getTypes() {
        return mMusicListByType.keySet();
    }

    public synchronized void updateMusicSource(String musicId, String key, String source) {
        MediaMetadataCompat metadata = getMusic(musicId);
        metadata = new MediaMetadataCompat.Builder(metadata)
                .putString(key, source)
                .build();

        MutableMediaMetadata mutableMetadata = mMusicListById.get(musicId);
        if (mutableMetadata == null) {
            throw new IllegalStateException("Unexpected error: Inconsistent data structures in " +
                    "MusicProvider");
        }

        mutableMetadata.metadata = metadata;
    }

    public synchronized void updateIntMusicSource(String musicId, String key, long source) {
        MediaMetadataCompat metadata = getMusic(musicId);
        metadata = new MediaMetadataCompat.Builder(metadata)
                .putLong(key, source)
                .build();

        MutableMediaMetadata mutableMetadata = mMusicListById.get(musicId);
        if (mutableMetadata == null) {
            throw new IllegalStateException("Unexpected error: Inconsistent data structures in " +
                    "MusicProvider");
        }

        mutableMetadata.metadata = metadata;
    }

    //获取随机音乐列表的迭代器
    public Iterable<MediaMetadataCompat> getShuffledMusic() {

        List<MediaMetadataCompat> shuffled = new ArrayList<>(mMusicListById.size());
        for (MutableMediaMetadata mutableMediaMetadata : mMusicListById.values()) {
            shuffled.add(mutableMediaMetadata.metadata);
        }
        Collections.shuffle(shuffled);
        return shuffled;
    }

    private synchronized void buildListsByGenre() {
        ConcurrentMap<String, List<MediaMetadataCompat>> newMusicListByGenre = new ConcurrentHashMap<>();

        for (MutableMediaMetadata m : mMusicListById.values()) {
            String genre = m.metadata.getString(MediaMetadataCompat.METADATA_KEY_GENRE);
            List<MediaMetadataCompat> list = newMusicListByGenre.get(genre);
            if (list == null) {
                list = new ArrayList<>();
                newMusicListByGenre.put(genre, list);
            }
            list.add(m.metadata);
        }
        mMusicListByType = newMusicListByGenre;
    }

    public String getTypeByMusic(String mediaId) {
        MutableMediaMetadata metadataCompat = mMusicListById.get(mediaId);
        if (metadataCompat == null) {
            return null;
        }
        return metadataCompat.metadata.getString(MediaMetadataCompat.METADATA_KEY_GENRE);
    }

    public List<MediaMetadataCompat> getMusicsByType(String type) {
        if (TextUtils.isEmpty(type)) {
            return Collections.emptyList();
        }
        if (!mMusicListByType.containsKey(type)) {
            return Collections.emptyList();
        }

        return mMusicListByType.get(type);
    }

    public MediaMetadataCompat getMusic(String mediaId) {
        return mMusicListById.containsKey(mediaId) ? mMusicListById.get(mediaId).metadata : null;
    }

    private List<MediaMetadataCompat> searchMusic(String metadataField, String query) {
        ArrayList<MediaMetadataCompat> result = new ArrayList<>();
        query = query.toLowerCase(Locale.US);
        for (MutableMediaMetadata track : mMusicListById.values()) {
            if (track.metadata.getString(metadataField).toLowerCase(Locale.US)
                    .contains(query)) {
                result.add(track.metadata);
            }
        }
        return result;
    }

    public void setFavorite(String musicId, boolean favorite) {
        if (favorite) {
            mFavoriteTracks.add(musicId);
        } else {
            mFavoriteTracks.remove(musicId);
        }
    }

    /**
     * 判断该音乐是否在"喜欢"列表中
     *
     * @param musicId id
     * @return like
     */
    public boolean isFavorite(String musicId) {
        return mFavoriteTracks.contains(musicId);
    }


    private void init(List<MediaMetadataCompat> list) {

        for (MediaMetadataCompat item : list) {
            String musicId = item.getDescription().getMediaId();
            mMusicListById.put(musicId, new MutableMediaMetadata(musicId, item));
        }
        buildListsByGenre();
    }

    public void requestMusicByType(String type, String offset, String size, final ILoadResult<List<MediaBrowserCompat.MediaItem>> result) {
        if (result == null) {
            return;
        }
        if (TextUtils.isEmpty(type) || TextUtils.isEmpty(offset) || TextUtils.isEmpty(size)) {
            result.onLoadError("params is null");
            return;
        }
        providerSource.requestMusicByType(type, offset, size, new ILoadResult<List<MediaMetadataCompat>>() {
            @Override
            public void onLoadResult(List<MediaMetadataCompat> mediaMetadataCompats) {

                init(mediaMetadataCompats);
                List<MediaBrowserCompat.MediaItem> items = new ArrayList<>();
                for (MediaMetadataCompat metadataCompat :
                        mediaMetadataCompats) {
                    MediaDescriptionCompat descriptionCompat = new MediaDescriptionCompat.Builder()
                            .setMediaId(metadataCompat.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID))
                            .setTitle(metadataCompat.getString(MediaMetadataCompat.METADATA_KEY_TITLE))
                            .setSubtitle(metadataCompat.getString(MediaMetadataCompat.METADATA_KEY_ARTIST))
                            .setDescription(metadataCompat.getString(MediaMetadataCompat.METADATA_KEY_ART_URI))
                            .build();
                    MediaBrowserCompat.MediaItem item = new MediaBrowserCompat.MediaItem(descriptionCompat, MediaBrowserCompat.MediaItem.FLAG_PLAYABLE);
                    items.add(item);
                }
                result.onLoadResult(items);

            }

            @Override
            public void onLoadSuccess() {
                result.onLoadSuccess();
            }

            @Override
            public void onLoadError(String e) {
                result.onLoadError(e);

            }
        });
    }


}
