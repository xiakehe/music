package com.example.study_music.com.xkh.music.util;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.v4.util.LruCache;
import android.text.TextUtils;
import android.util.Log;

import com.example.study_core.util.ScreenUtils;
import com.example.study_music.R;
import com.example.study_music.com.xkh.music.bean.Music;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 专辑封面图片加载器
 * Created by wcy on 2015/11/27.
 */
public class CoverLoader {
    public static final int THUMBNAIL_MAX_LENGTH = 500;
    private static final String KEY_NULL = "null";

    private Context context;
    private Map<Type, LruCache<String, Bitmap>> cacheMap;
    private int roundLength = ScreenUtils.getScreenWidth() / 2;

    private enum Type {
        THUMB,
        ROUND,
        BLUR
    }

    public static CoverLoader get() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        @SuppressLint("StaticFieldLeak")
        private static final CoverLoader instance = new CoverLoader();
    }

    private CoverLoader() {
    }

    public void init(Context context) {
        this.context = context.getApplicationContext();

        // 获取当前进程的可用内存（单位KB）
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        // 缓存大小为当前进程可用内存的1/8
        int cacheSize = maxMemory / 8;
        LruCache<String, Bitmap> thumbCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getAllocationByteCount() / 1024;
            }
        };
        LruCache<String, Bitmap> roundCache = new LruCache<>(10);
        LruCache<String, Bitmap> blurCache = new LruCache<>(10);

        cacheMap = new HashMap<>(3);
        cacheMap.put(Type.THUMB, thumbCache);
        cacheMap.put(Type.ROUND, roundCache);
        cacheMap.put(Type.BLUR, blurCache);
    }

    public void setRoundLength(int roundLength) {
        if (this.roundLength != roundLength) {
            this.roundLength = roundLength;
            cacheMap.get(Type.ROUND).evictAll();
        }
    }

    public Bitmap loadThumb(Music music) {
        return loadCover(music, Type.THUMB);
    }

    public Bitmap loadRound(Music music) {
        return loadCover(music, Type.ROUND);
    }

    public Bitmap loadBlur(Music music) {
        return loadCover(music, Type.BLUR);
    }

    private Bitmap loadCover(Music music, Type type) {
        Bitmap bitmap;
        String key = getKey(music);
        LruCache<String, Bitmap> cache = cacheMap.get(type);
        if (TextUtils.isEmpty(key)) {
            bitmap = cache.get(KEY_NULL);
            if (bitmap != null) {
                return bitmap;
            }

            bitmap = getDefaultCover(type);
            cache.put(KEY_NULL, bitmap);
            return bitmap;
        }

        bitmap = cache.get(key);
        if (bitmap != null) {
            return bitmap;
        }

        bitmap = loadCoverByType(context, music, type);
        if (bitmap != null) {
            cache.put(key, bitmap);
            return bitmap;
        }

        return loadCover(null, type);
    }

    private String getKey(Music music) {
        if (music == null) {
            return null;
        }

        if (music.getType() == Music.Type.LOCAL && music.getAlbumId() > 0) {
            return String.valueOf(music.getAlbumId());
        } else if (music.getType() == Music.Type.ONLINE && !TextUtils.isEmpty(music.getCoverPath())) {
            return music.getCoverPath();
        } else {
            return null;
        }
    }

    private Bitmap getDefaultCover(Type type) {
        switch (type) {
            case ROUND:
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.play_page_default_bg);
                bitmap = ImageUtils.resizeImage(bitmap, roundLength, roundLength);
                return bitmap;
            case BLUR:
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.play_page_default_cover);
            default:
                return BitmapFactory.decodeResource(context.getResources(), R.drawable.default_cover);
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    private Bitmap loadCoverByType(Context context, Music music, Type type) {
        Bitmap bitmap;
        if (music.getType() == Music.Type.LOCAL) {
            bitmap = loadCoverFromMediaStore(music.getAlbumId());
        } else {
            bitmap = loadCoverFromFile(music.getCoverPath());
        }

        if (bitmap == null) {
            return null;
        }
        switch (type) {
            case ROUND:
                bitmap = ImageUtils.resizeImage(bitmap, roundLength, roundLength);
                return ImageUtils.createCircleImage(bitmap);
            case BLUR:
                // return ImageUtils.rsBlur(context, bitmap, radius, 0.5f);
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    return ImageUtils.rsBlur(context, bitmap, 15.0f, 0.5f);
                } else {
                    return ImageUtils.blur(bitmap);
                }

            default:
                return bitmap;
        }
    }

    /**
     * 从媒体库加载封面<br>
     * 本地音乐
     */
    private Bitmap loadCoverFromMediaStore(long albumId) {
        ContentResolver resolver = context.getContentResolver();
        Uri uri = MusicUtils.getMediaStoreAlbumCoverUri(albumId);
        InputStream is;
        try {
            is = resolver.openInputStream(uri);
        } catch (FileNotFoundException ignored) {
            return null;
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        return BitmapFactory.decodeStream(is, null, options);
    }

    /**
     * 从下载的图片加载封面<br>
     * 网络音乐
     */
    private Bitmap loadCoverFromFile(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        return BitmapFactory.decodeFile(path, options);
    }
}
