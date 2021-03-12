package fr.doranco.myquizz.service;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.IOException;

public class MediaPlayerUtils {

    public static final void selectSong(Context context, MediaPlayer mediaPlayer, String resourceSongName) throws IOException {

        String packageName = context.getPackageName();
        int idSong = context.getResources().getIdentifier(resourceSongName, "raw", packageName);
        Uri uri = Uri.parse("android.resource://" + context.getPackageName() + "/" + idSong);

        // Set MediaPlayer
        mediaPlayer.setDataSource(context, uri);
        mediaPlayer.prepareAsync();
    }
}
