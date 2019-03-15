package se.leivas.insomnium;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.format.Time;
import android.util.Log;

public class MainService extends Service {
    private int audioID = 1;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Log.i("Where am I", "onStartCommand in MainService");

        /*this is where you
        X                   1. start intro mp3
        2. start location service
        X                   3. start checking for when mp3 has finished playing
        4. start next when timer is over
         */
        Intent mediaPlayerIntent = new Intent(this, MediaPlayerService.class);
        mediaPlayerIntent.putExtra("id", audioID);
        startService(mediaPlayerIntent);

        Thread timer = new Thread(new TimerThread() );
        timer.start();



        /*
        while(isServiceRunning(MediaPlayerService.class)){
            Log.i("service running", "Yes, it's running");
        }
        Log.i("service running", "YNO IT STOPPED!!!");
        */
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Where am I", "onDestroy in MainService");
        Intent mediaPlayerIntent = new Intent(this, MediaPlayerService.class);
        stopService(mediaPlayerIntent);
    }

    final class TimerThread implements Runnable{

        @Override
        public void run() {
            synchronized(this) {
                while(true) {
                    try {
                        wait(1000);
                        if(isServiceRunning(MediaPlayerService.class)){
                            Log.i("MediaPlayerService run?", "YES");
                        }else {
                            Log.i("MediaPlayerService run?", "NO");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


}
