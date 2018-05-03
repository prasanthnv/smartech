package com.smartech.smartech.smartech.Receivers;

import android.app.Service;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;

public class RingtonePlayingService extends Service
{
    private Ringtone ringtone;
    Uri notification;


    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        RingtoneManager ringtoneManager  =new RingtoneManager(getApplicationContext());
        if(intent.hasExtra("type")) {
            if (intent.getStringExtra("type").equals("ring")) {
                notification = ringtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            } else if (intent.getStringExtra("type").equals("alarm")) {
                notification = ringtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            } else  {
                notification = ringtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            }
        }else{
            notification = ringtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        }
        this.ringtone =RingtoneManager.getRingtone(getApplicationContext(), notification);
        ringtone.play();

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy()
    {
        ringtone.stop();
    }
}
