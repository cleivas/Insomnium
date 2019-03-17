package se.leivas.insomnium;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;

public class MediaPlayerService extends Service {
    private MediaPlayer player;
    private int id = 0;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        id = intent.getIntExtra("id", 0);
        Log.i("Where am I", "onStartCommand in MediaPlayerService");

        if(id==1) player = MediaPlayer.create(this, R.raw.no_10);
        else if(id==2) player = MediaPlayer.create(this, R.raw.yes_10);

        if(player != null) {
            player.start();
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                    stopSelf();
                }
            });
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Where am I", "onDestroy in MediaPlayerService");

        if(player != null) {
            player.release();
        }

    }


}
