package com.example.study_music.com.xkh.music.player;

import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;

import com.example.study_music.com.xkh.music.player.model.MusicProvider;

import java.util.ArrayList;
import java.util.List;


public class QueueHelper {

    private static final String TAG = "QueueHelper";

    private static final int RANDOM_QUEUE_SIZE = 10;

    public static List<MediaSessionCompat.QueueItem> getPlayingQueue(String mediaId,
                                                                     MusicProvider musicProvider) {


        List<MediaMetadataCompat> tracks ;

        String type=musicProvider.getTypeByMusic(mediaId);

        tracks = musicProvider.getMusicsByType(type);

        if (tracks == null) {
            Log.e(TAG, "Unrecognized category type: " + mediaId);
            return null;
        }
        Log.d(TAG,tracks.toString());

        return convertToQueue(tracks);
    }


    public static int getMusicIndexOnQueue(Iterable<MediaSessionCompat.QueueItem> queue,
                                           String mediaId) {
        int index = 0;
        for (MediaSessionCompat.QueueItem item : queue) {
            if (mediaId.equals(item.getDescription().getMediaId())) {
                return index;
            }
            index++;
        }
        return -1;
    }

    public static int getMusicIndexOnQueue(Iterable<MediaSessionCompat.QueueItem> queue,
                                           long queueId) {
        int index = 0;
        for (MediaSessionCompat.QueueItem item : queue) {
            if (queueId == item.getQueueId()) {
                return index;
            }
            index++;
        }
        return -1;
    }

    /**
     * 索引是否是有效值（指向的音乐是否可以播放）
     *
     * @param index
     * @param queue
     * @return
     */
    public static boolean isIndexPlayable(int index, List<MediaSessionCompat.QueueItem> queue) {
        return (queue != null && index >= 0 && index < queue.size());
    }

    /**
     * Create a random queue with at most {@link #RANDOM_QUEUE_SIZE} elements.
     *
     * @param musicProvider the provider used for fetching music.
     * @return list containing {@link MediaSessionCompat.QueueItem}'s
     */
    public static List<MediaSessionCompat.QueueItem> getRandomQueue(MusicProvider musicProvider) {
        List<MediaMetadataCompat> result = new ArrayList<>(RANDOM_QUEUE_SIZE);
        Iterable<MediaMetadataCompat> shuffled = musicProvider.getShuffledMusic();
        for (MediaMetadataCompat metadata : shuffled) {
            if (result.size() == RANDOM_QUEUE_SIZE) {
                break;
            }
            result.add(metadata);
        }
        Log.d(TAG, "getRandomQueue: result.size=" + result.size());

        return convertToQueue(result);
    }

    private static List<MediaSessionCompat.QueueItem> convertToQueue(
            List<MediaMetadataCompat> tracks) {
        List<MediaSessionCompat.QueueItem> queue = new ArrayList<>();
        int count = 0;
        for (MediaMetadataCompat track : tracks) {


            MediaMetadataCompat trackCopy = new MediaMetadataCompat.Builder(track)
                    .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, track.getDescription().getMediaId())
                    .build();

            // We don't expect queues to change after created, so we use the item index as the
            // queueId. Any other number unique in the queue would work.
            MediaSessionCompat.QueueItem item = new MediaSessionCompat.QueueItem(
                    trackCopy.getDescription(), count++);
            queue.add(item);
        }
        Log.d(TAG,queue.toString());
        return queue;

    }
}
