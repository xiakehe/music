package com.example.study_music.com.xkh.music.player;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.util.Log;

import com.example.study_music.R;
import com.example.study_music.com.xkh.music.main.FullScreenPlayerActivity;
import com.example.study_music.com.xkh.music.player.model.ILoadResult;
import com.example.study_music.com.xkh.music.player.model.MusicProvider;
import com.example.study_music.com.xkh.music.player.model.MusicProviderSource;
import com.example.study_music.com.xkh.music.util.Contact;
import com.example.study_music.com.xkh.music.view.MusicKeyguardActivity;

import java.lang.ref.WeakReference;
import java.util.List;

public class MusicService extends MediaBrowserServiceCompat implements PlaybackManager.PlaybackServiceCallback {
    private static final String TAG = "MusicService";
    public static final String MEDIA_ID_ROOT = "__ROOT__";
    public static final String MEDIA_LIST = "__LIST__";

    public static final String ALBUM_INFO = "music";


    // Extra on MediaSession that contains the Cast device name currently connected to
    //public static final String EXTRA_CONNECTED_CAST = "com.example.android.uamp.CAST_NAME";
    // The action of the incoming Intent indicating that it contains a command
    // to be executed (see {@link #onStartCommand})
    public static final String ACTION_CMD = "com.example.study_music.ACTION_CMD";
    // The key in the extras of the incoming Intent indicating the command that
    // should be executed (see {@link #onStartCommand})
    public static final String CMD_NAME = "CMD_NAME";
    // A value of a CMD_NAME key in the extras of the incoming Intent that
    // indicates that the music playback should be paused (see {@link #onStartCommand})
    public static final String CMD_PAUSE = "CMD_PAUSE";
    public static final String CMD_UPDATE = "CMD_UPDATE";
    // A value of a CMD_NAME key that indicates that the music playback should switch
    // to local playback from cast playback.
    //public static final String CMD_STOP_CASTING = "CMD_STOP_CASTING";
    // Delay stopSelf by using a handler.
    private static final int STOP_DELAY = 30000;
    private MusicProvider mMusicProvider = null;
    private PlaybackManager mPlaybackManager;
    private boolean isNeedRegScreenBroadcast = true;


    private MediaSessionCompat mSession;
    private MediaNotificationManager mMediaNotificationManager;
    private final DelayedStopHandler mDelayedStopHandler = new DelayedStopHandler(this);
    private BroadcastReceiver screenBroadcastReceiver = null;


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");

        mMusicProvider = new MusicProvider();

        //QueueManager提供四个回调接口
        QueueManager queueManager = new QueueManager(mMusicProvider, getResources(),
                new QueueManager.MetadataUpdateListener() {
                    @Override
                    public void onMetadataChanged(MediaMetadataCompat metadata) {
                        mSession.setMetadata(metadata);
                    }

                    @Override
                    public void onMetadataRetrieveError() {
                        mPlaybackManager.updatePlaybackState(
                                getString(R.string.error_no_metadata));
                    }

                    @Override
                    public void onCurrentQueueIndexUpdated(int queueIndex) {
                        mPlaybackManager.handlePlayRequest();
                    }

                    @Override
                    public void onQueueUpdated(String title,
                                               List<MediaSessionCompat.QueueItem> newQueue) {
                        mSession.setQueue(newQueue);
                        mSession.setQueueTitle(title);
                    }
                });

        //Service初始化时传入PlaybackManager中的是LocalPlayback
        LocalPlayback playback = new LocalPlayback(this, mMusicProvider);
        mPlaybackManager = new PlaybackManager(this, getResources(), mMusicProvider, queueManager,
                playback);

        //开始一个新的MediaSession
        mSession = new MediaSessionCompat(this, "MusicService");
        setSessionToken(mSession.getSessionToken());
        mSession.setCallback(mPlaybackManager.getMediaSessionCallback());
        mSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        Context context = getApplicationContext();
        Intent intent = new Intent(context, FullScreenPlayerActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, 99 /*request code*/,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mSession.setSessionActivity(pi);

        mPlaybackManager.updatePlaybackState(null);

        try {
            mMediaNotificationManager = new MediaNotificationManager(this);
        } catch (RemoteException e) {
            throw new IllegalStateException("Could not create a MediaNotificationManager", e);
        }

    }

    @Override
    public int onStartCommand(Intent startIntent, int flags, int startId) {
        if (startIntent != null) {
            String action = startIntent.getAction();
            String command = startIntent.getStringExtra(CMD_NAME);
            if (ACTION_CMD.equals(action)) {
                if (CMD_PAUSE.equals(command)) {
                    mPlaybackManager.handlePauseRequest();
                } else if (CMD_UPDATE.equals(command)) {
                    mPlaybackManager.updatePlaybackState(null);
                }
            } else {
                // Try to handle the intent as a media button event wrapped by MediaButtonReceiver
                MediaButtonReceiver.handleIntent(mSession, startIntent);
            }
        }
        // Reset the delay handler to enqueue a message to stop the service if
        // nothing is playing.
        mDelayedStopHandler.removeCallbacksAndMessages(null);
        mDelayedStopHandler.sendEmptyMessageDelayed(0, STOP_DELAY);
        return START_STICKY;
    }

    @Override
    public void onCustomAction(@NonNull String action, Bundle extras, @NonNull Result<Bundle> result) {
        super.onCustomAction(action, extras, result);

    }

    private void regScreenOffReceiver() {
        if (screenBroadcastReceiver == null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
            screenBroadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    if (TextUtils.equals(intent.getAction(), Intent.ACTION_SCREEN_OFF)) {
                        Intent startScreenActivity = new Intent(getApplicationContext(), MusicKeyguardActivity.class);
                        startScreenActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                        getApplicationContext().startActivity(startScreenActivity);
                    }
                }
            };
            registerReceiver(screenBroadcastReceiver, intentFilter);
        } else {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
            registerReceiver(screenBroadcastReceiver, intentFilter);
        }

    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        // Service is being killed, so make sure we release our resources
        mPlaybackManager.handleStopRequest(null);
        mMediaNotificationManager.stopNotification();
        mDelayedStopHandler.removeCallbacksAndMessages(null);
        if (screenBroadcastReceiver != null) {
            unregisterReceiver(screenBroadcastReceiver);
            screenBroadcastReceiver = null;
        }
        mSession.release();
    }

    @Override
    public BrowserRoot onGetRoot(@NonNull String clientPackageName, int clientUid, Bundle rootHints) {

        return new BrowserRoot(MEDIA_ID_ROOT, null);

    }

    @Override
    public void onLoadChildren(@NonNull String parentId, @NonNull Result<List<MediaBrowserCompat.MediaItem>> result) {

    }

    @Override
    public void onLoadChildren(@NonNull String parentId, @NonNull final Result<List<MediaBrowserCompat.MediaItem>> result, @NonNull Bundle options) {

        switch (parentId) {
            case MEDIA_LIST:
                String type = options.getString(Contact.PARAM_TYPE);
                String count = options.getString(Contact.PARAM_OFFSET);
                String size = options.getString(Contact.PARAM_SIZE);
                result.detach();
                mMusicProvider.requestMusicByType(type, count, size, new ILoadResult<List<MediaBrowserCompat.MediaItem>>() {
                    @Override
                    public void onLoadResult(List<MediaBrowserCompat.MediaItem> list) {
                        result.sendResult(list);
                    }

                    @Override
                    public void onLoadSuccess() {
                    }

                    @Override
                    public void onLoadError(String e) {
                        result.sendError(null);
                    }
                });
                break;
            case ALBUM_INFO:
                String album_Id = options.getString(Contact.PARAM_ALBUM_ID);
                break;


        }


    }

    @Override
    public void onPlaybackStart() {

        mSession.setActive(true);
        if (isNeedRegScreenBroadcast) {
            regScreenOffReceiver();
        }
        mDelayedStopHandler.removeCallbacksAndMessages(null);

        // The service needs to continue running even after the bound client (usually a
        // MediaController) disconnects, otherwise the music playback will stop.
        // Calling startService(Intent) will keep the service running until it is explicitly killed.
        //即使绑定的客户端（通常是指MediaController）断开连接了，Service也需要继续运行，否则音乐将会停止播放。
        //调用startService(Intent)将保持Service持续运行直到明确要将服务杀掉为止
        startService(new Intent(getApplicationContext(), MusicService.class));

    }

    @Override
    public void onLoadResource() {
        mSession.sendSessionEvent(MusicProviderSource.CUSTOM_LOAD_METADATA_TRACK_SOURCE_EVENT, null);
    }

    @Override
    public void onNotificationRequired() {
        mMediaNotificationManager.startNotification();

    }

    @Override
    public void onPlaybackStop() {

        mSession.setActive(false);
        // Reset the delayed stop handler, so after STOP_DELAY it will be executed again,
        // potentially stopping the service.
        //重置延迟停止的Handler，所以收到 STOP_DELAY 消息后将再次执行
        //有可能会停止Service
        mDelayedStopHandler.removeCallbacksAndMessages(null);
        mDelayedStopHandler.sendEmptyMessageDelayed(0, STOP_DELAY);
        stopForeground(true);
        if (screenBroadcastReceiver != null) {
            unregisterReceiver(screenBroadcastReceiver);
            screenBroadcastReceiver = null;
        }

    }


    @Override
    public void onPlaybackStateUpdated(PlaybackStateCompat newState) {
        mSession.setPlaybackState(newState);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        stopSelf();
    }


    /**
     * A simple handler that stops the service if playback is not active (playing)
     * 当playback不在活跃状态时停止服务
     */
    private static class DelayedStopHandler extends Handler {
        private final WeakReference<MusicService> mWeakReference;

        private DelayedStopHandler(MusicService service) {
            mWeakReference = new WeakReference<>(service);
        }

        @Override
        public void handleMessage(Message msg) {
            MusicService service = mWeakReference.get();
            if (service != null && service.mPlaybackManager.getPlayback() != null) {
                if (service.mPlaybackManager.getPlayback().isPlaying()) {
                    Log.d(TAG, "Ignoring delayed stop since the media player is in use.");
                    return;
                }
                Log.d(TAG, "Stopping service with delay handler.");
                service.stopSelf();
            }
        }
    }
}
