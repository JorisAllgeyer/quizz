package fr.doranco.myquizz.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

import java.io.IOException;

public class MusicService extends Service {
    private static final String TAG = MusicService.class.getSimpleName();
    private static final String RAW_SONG = "living_room";

    private MediaPlayer mediaPlayer;

    public MusicService() { }

    @Override
    public void onCreate() {
        super.onCreate();

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("__________________ onStartCommand SERVICE");

        try {
            MediaPlayerUtils.selectSong(MusicService.this, mediaPlayer, RAW_SONG);

        } catch (IOException e) {

            e.printStackTrace();
            Toast.makeText(MusicService.this,
                    "Erreur lors de la lecture de la chanson", Toast.LENGTH_LONG).show();
        }

        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        System.out.println("__________________ onDestroy SERVICE");
        super.onDestroy();
        mediaPlayer.stop();
    }

    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("__________________ onDestroy SERVICE");
        return null;
    }
}
