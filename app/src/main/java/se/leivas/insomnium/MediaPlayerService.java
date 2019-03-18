package se.leivas.insomnium;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
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

        switch(id) {
            case 0:
                player = MediaPlayer.create(this, R.raw.intro);
                break;
            case 1:
                player = MediaPlayer.create(this, R.raw.whisper_yes_1);
                break;
            case 2:
                chooseAudio(0, "yes");
                break;
            case 3:
                player = MediaPlayer.create(this, R.raw.whisper_yes_2);
                break;
            case 4:
                chooseAudio(1, "yes");
                break;
            case 5:
                chooseAudio(0, "yes");
                break;
            case 6:
                player = MediaPlayer.create(this, R.raw.whisper_no_1);
                break;
            case 7:
                chooseAudio(0, "no");
                break;
            case 8:
                player = MediaPlayer.create(this, R.raw.whisper_no_2);
                break;
            case 9:
                chooseAudio(1, "no");
                break;
            case 10:
                player = MediaPlayer.create(this, R.raw.whisper_no_3);
                break;
            case 11:
                chooseAudio(2, "no");
                break;
            case 12:
                player = MediaPlayer.create(this, R.raw.no_10);
                break;
            case 13:
                chooseAudio(2, "no");
                break;
            case 14:
                chooseAudio(1, "no");
                break;
            case 15:
                chooseAudio(0, "no");
                break;
            case 16:
                player = MediaPlayer.create(this, R.raw.whisper_yes_3);
                break;
            case 17:
                chooseAudio(0, "yes");
                break;
            case 18:
                chooseAudio(1, "yes");
                break;
            case 19:
                chooseAudio(2, "yes");
                break;
            case 20:
                player = MediaPlayer.create(this, R.raw.yes_10);
                break;
        }


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

    private void chooseAudio(int level, String type){
        int random = (int )(Math.random() * 3 + 1)+(level*3);
        Log.i("fileName", Integer.toString(level));
        Log.i("fileName", Integer.toString(random));
        String fileName = type + "_" + String.format("%02d", random);
        Log.i("fileName", fileName);
        int resID = getResources().getIdentifier(fileName , "raw", this.getPackageName());
        player = MediaPlayer.create(this, resID);
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Where am I", "onDestroy in MediaPlayerService");

        if(player != null) {
            player.release();
        }

    }


}
