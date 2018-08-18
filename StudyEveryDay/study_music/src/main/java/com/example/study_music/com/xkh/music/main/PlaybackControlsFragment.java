package com.example.study_music.com.xkh.music.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.study_core.delegate.PermissionDelegate;
import com.example.study_music.R;
import com.example.study_music.R2;
import com.example.study_music.com.xkh.music.main.adapter.MusicQueueAdapter;
import com.example.study_music.com.xkh.music.player.model.MutableMediaDescriptionMetadata;
import com.example.study_music.com.xkh.music.util.PlayQueueUtils;
import com.example.study_ui.GlideUtil;
import com.example.study_ui.bottomsheet.BottomSheetDialogListView;
import com.example.study_ui.bottomsheet.SpringBackBottomSheetDialog;
import com.example.study_ui.recycler.BaseDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PlaybackControlsFragment extends PermissionDelegate {

    private static final String TAG = "PlaybackControls";
    private MusicQueueAdapter adapter = null;
    private SpringBackBottomSheetDialog c = null;
    private final PlayQueueUtils mPlayQueueUtils = PlayQueueUtils.getInstance();

    @BindView(R2.id.title)
    TextView mMusicName;

    @BindView(R2.id.artist)
    TextView mArtistName;

    @BindView(R2.id.play_pause)
    ImageButton mPlayOrPause;

    @BindView(R2.id.album_art)
    ImageView mAlbum;

    @BindView(R2.id.play_list)
    ImageButton mPlayQueue;


    @OnClick(R2.id.play_list)
    void onClickPlayQueue() {

        List<MediaSessionCompat.QueueItem> items = getMediaSessionCompatQueueList();
        List<MutableMediaDescriptionMetadata> list = new ArrayList<>();
        for (MediaSessionCompat.QueueItem item : items) {
            MutableMediaDescriptionMetadata entry = new MutableMediaDescriptionMetadata(item.getDescription(), item.getQueueId());
            list.add(entry);
        }
        mPlayQueueUtils.initData(list);
        showCustomSheet();
    }

    private List<MediaSessionCompat.QueueItem> getMediaSessionCompatQueueList() {
        MediaControllerCompat controller = MediaControllerCompat.getMediaController(getActivity());
        return controller.getQueue();
    }

    private void showCustomSheet() {

        c = new SpringBackBottomSheetDialog(mContext);
        @SuppressLint("InflateParams") View v = LayoutInflater.from(mContext).inflate(R.layout.custom_sheet_child_layout, null, false);
        BottomSheetDialogListView listView = v.findViewById(R.id.music_queue_list);
        ImageView deleteAll = v.findViewById(R.id.iv_music_queue_delete_all);
        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMediaSessionCompatQueueList().removeAll(new ArrayList<MediaSessionCompat.QueueItem>());
                mPlayQueueUtils.clearData();
                if (adapter != null) {
                    adapter.replaceData(new ArrayList<MutableMediaDescriptionMetadata>());
                }
                closeCustomSheet();
            }
        });

        initListView(listView);
        c.setContentView(v);
        listView.bindBottomSheetDialog(v);
        c.addSpringBackDisLimit(-1);
        c.show();

    }

    private void closeCustomSheet() {
        if (c != null && c.isShowing()) {
            c.cancel();
            adapter = null;
            c = null;
        }
    }

    private void initListView(BottomSheetDialogListView listView) {

        if (getPlayingData() != null) {
            mPlayQueueUtils.updateData(getPlayingData().getDescription());
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        adapter = new MusicQueueAdapter(R.layout.adapter_main_play_queue_item, mPlayQueueUtils.getData());
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                getMediaSessionCompatQueueList().remove(mPlayQueueUtils.getDataPosition(position));
                adapter.remove(position);
                adapter.notifyItemChanged(position);
            }
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                MediaControllerCompat controller = MediaControllerCompat.getMediaController(getActivity());
                controller.getTransportControls().playFromMediaId(mPlayQueueUtils.updateData(mPlayQueueUtils.getListDataByPosition(position)).getMediaId(), null);
                adapter.notifyItemChanged(mPlayQueueUtils.getLastDataPosition());
                adapter.notifyItemChanged(position);
            }
        });
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(adapter);
        listView.addItemDecoration(BaseDecoration.create(getResources().getColor(R.color.line_write_bg_color), 1));


        adapter.notifyDataSetChanged();

    }


    @OnClick(R2.id.rl_bottom_control)
    void onClickRoot() {
        Intent intentFull = new Intent(getActivity(), FullScreenPlayerActivity.class);
        startActivity(intentFull);
    }

    @OnClick(R2.id.play_next)
    void onClickPlayNext() {
        playNextMedia();
    }

    @OnClick(R2.id.play_pause)
    void onClickPlayOrPause() {
        MediaControllerCompat controller = MediaControllerCompat.getMediaController(getActivity());
        PlaybackStateCompat stateObj = controller.getPlaybackState();
        final int state = stateObj == null ?
                PlaybackStateCompat.STATE_NONE : stateObj.getState();
        Log.d(TAG, "Button pressed, in state " + state);
        if (state == PlaybackStateCompat.STATE_PAUSED ||
                state == PlaybackStateCompat.STATE_STOPPED ||
                state == PlaybackStateCompat.STATE_NONE) {
            playMedia();
        } else if (state == PlaybackStateCompat.STATE_PLAYING ||
                state == PlaybackStateCompat.STATE_BUFFERING ||
                state == PlaybackStateCompat.STATE_CONNECTING) {
            pauseMedia();
        }


    }

    private MediaMetadataCompat getPlayingData() {
        MediaControllerCompat controllerCompat = MediaControllerCompat.getMediaController(getActivity());
        if (controllerCompat != null) {

            return controllerCompat.getMetadata();
        }

        return null;
    }
//
//    private int getPlayingMode() {
//        MediaControllerCompat controllerCompat = MediaControllerCompat.getMediaController(getActivity());
//        if (controllerCompat != null) {
//
//            return controllerCompat.getRepeatMode();
//        }
//        return 0;
//    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "fragment.onStart");
        MediaControllerCompat controller = MediaControllerCompat.getMediaController(getActivity());
        if (controller != null) {
            onConnected();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "fragment.onStop");
        MediaControllerCompat controller = MediaControllerCompat.getMediaController(getActivity());
        if (controller != null) {
            controller.unregisterCallback(mCallback);
        }
        closeCustomSheet();
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_playback_controls;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
    }

    private final MediaControllerCompat.Callback mCallback = new MediaControllerCompat.Callback() {
        @Override
        public void onPlaybackStateChanged(@NonNull PlaybackStateCompat state) {
            Log.d(TAG, "Received playback state change to state " + state.getState());
            PlaybackControlsFragment.this.onPlaybackStateChanged(state);
        }

        @Override
        public void onMetadataChanged(MediaMetadataCompat metadata) {
            if (metadata == null) {
                return;
            }
            Log.d(TAG, "Received metadata state change to mediaId=" +
                    metadata.getDescription().getMediaId() +
                    " song=" + metadata.getDescription().getTitle());
            PlaybackControlsFragment.this.onMetadataChanged(metadata);
        }
    };

    private void onPlaybackStateChanged(PlaybackStateCompat state) {

        Log.d(TAG, "onPlaybackStateChanged " + state);
        if (getActivity() == null) {
            Log.w(TAG, "onPlaybackStateChanged called when getActivity null," +
                    "this should not happen if the callback was properly unregistered. Ignoring.");
            return;
        }
        if (state == null) {
            return;
        }
        boolean enablePlay = false;
        switch (state.getState()) {
            case PlaybackStateCompat.STATE_PAUSED:
            case PlaybackStateCompat.STATE_STOPPED:
                enablePlay = true;
                break;
            case PlaybackStateCompat.STATE_ERROR:
                Log.e(TAG, "error playbackstate: " + state.getErrorMessage());
                Toast.makeText(getActivity(), state.getErrorMessage(), Toast.LENGTH_LONG).show();
                break;
        }

        if (enablePlay) {
            mPlayOrPause.setImageDrawable(
                    ContextCompat.getDrawable(getActivity(), R.drawable.ic_play));
        } else {
            mPlayOrPause.setImageDrawable(
                    ContextCompat.getDrawable(getActivity(), R.drawable.ic_pause));
        }
    }

    private void onMetadataChanged(MediaMetadataCompat metadata) {

        Log.d(TAG, "onMetadataChanged " + metadata);
        if (getActivity() == null) {
            Log.w(TAG, "onMetadataChanged called when getActivity null," +
                    "this should not happen if the callback was properly unregistered. Ignoring.");
            return;
        }
        if (metadata == null) {
            return;
        }
        String artist = metadata.getString(MediaMetadataCompat.METADATA_KEY_ARTIST);
        String album = metadata.getString(MediaMetadataCompat.METADATA_KEY_ALBUM);

        mMusicName.setText(metadata.getString(MediaMetadataCompat.METADATA_KEY_TITLE));
        mArtistName.setText(artist != null ? artist : "" + "-" + (album != null ? album : ""));
        Glide.with(this).load(metadata.getString(MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI))
                .apply(GlideUtil.REQUEST_OPTIONS)
                .into(mAlbum);
        MediaDescriptionCompat newMusic = metadata.getDescription();
        PlayQueueUtils.getInstance().updateData(newMusic);
        if (adapter != null) {
            adapter.notifyItemChanged(mPlayQueueUtils.getCurDataPosition());
            adapter.notifyItemChanged(mPlayQueueUtils.getLastDataPosition());

        }
    }

    private void playMedia() {
        MediaControllerCompat controller = MediaControllerCompat.getMediaController(getActivity());
        if (controller != null) {
            controller.getTransportControls().play();
        }
    }

    private void playNextMedia() {
        MediaControllerCompat controller = MediaControllerCompat.getMediaController(getActivity());
        if (controller != null) {
            controller.getTransportControls().skipToNext();
            controller.getTransportControls().play();
        }
    }

    private void pauseMedia() {
        MediaControllerCompat controller = MediaControllerCompat.getMediaController(getActivity());
        if (controller != null) {
            controller.getTransportControls().pause();
        }
    }

    public void onConnected() {
        MediaControllerCompat controller = MediaControllerCompat.getMediaController(getActivity());
        if (controller != null) {
            onMetadataChanged(controller.getMetadata());
            onPlaybackStateChanged(controller.getPlaybackState());
            controller.registerCallback(mCallback);
        }
    }
}
