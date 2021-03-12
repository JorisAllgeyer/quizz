package fr.doranco.myquizz.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.IBinder;

import java.io.IOException;

public class MusicService extends Service {
    // Song
    private static final String RAW_SONG = "living_room";
    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        // Init
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build());
    }

    public void doStart() {
        if (mediaPlayer.isPlaying()) return;
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        try {
            MediaPlayerUtils.selectSong(MusicService.this, mediaPlayer, RAW_SONG);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Wait for song preparation before start...
        mediaPlayer.setOnPreparedListener(mp -> {
            doStart();
        });

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
