package fr.doranco.myquizz.service;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;

public class MediaPlayerUtils {

    private static final String TAG = MediaPlayerUtils.class.getSimpleName();

    public static final void selectSong(Context context, MediaPlayer mediaPlayer, String resourceSongName) throws IOException {

        String packageName = context.getPackageName();

        int idSong = context.getResources().getIdentifier(resourceSongName, "raw", packageName);

        Log.i(TAG, "Resource song name: " + resourceSongName + ", id: " + idSong);

        Uri uri = Uri.parse("android.resource://" + context.getPackageName() + "/" + idSong);
        Log.i(TAG, "Song Uri: " + uri);

        mediaPlayer.setDataSource(context, uri);
        mediaPlayer.prepareAsync();
    }
}
