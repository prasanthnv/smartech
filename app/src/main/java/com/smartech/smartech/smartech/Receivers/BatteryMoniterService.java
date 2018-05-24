package com.smartech.smartech.smartech.Receivers;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.smartech.smartech.smartech.Receivers.RingtonePlayingService;
import com.smartech.smartech.smartech.SharedPreferenceStore.SharedPreferenceStore;

public class BatteryMoniterService  extends Service {
    SharedPreferenceStore spStore;
    private BroadcastReceiver mBatteryStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            spStore = new SharedPreferenceStore(context);
            if (intent.getAction().equals(Intent.ACTION_POWER_CONNECTED)) {
                Toast.makeText(context, "The device is charging", Toast.LENGTH_SHORT).show();

            } else {
                intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED);
                Toast.makeText(context, "The device is not charging", Toast.LENGTH_SHORT).show();
            }
        }
    };


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        PowerConnectionReceiver receiver = new PowerConnectionReceiver();

        IntentFilter ifilter = new IntentFilter();
        ifilter.addAction(Intent.ACTION_POWER_CONNECTED);
        ifilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        ifilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(receiver, ifilter);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);

        return START_NOT_STICKY;
    }

//    @Override
//    public void onDestroy() {
//          mBatteryStateReceiver=null;
//    }


    public class PowerConnectionReceiver extends BroadcastReceiver {
        public PowerConnectionReceiver() {

        }

        @Override
        public void onReceive(Context context, Intent intent) {
            spStore = new SharedPreferenceStore(context);
            if (spStore.isBatteryAlarmEnabled()) {
                int level = intent.getIntExtra("level", 0);
                String battery = String.valueOf(level) + "%";
                Log.i("battery => ", battery);

                int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
                boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                        status == BatteryManager.BATTERY_STATUS_FULL;
                Log.w("Power status", isCharging + "");

                if (isCharging && level == 100) {
                    // battery @ 100%
                    Intent startIntent = new Intent(context, RingtonePlayingService.class);
                    context.startService(startIntent);
                } else {
                    Intent stopIntent = new Intent(context, RingtonePlayingService.class);
                    context.stopService(stopIntent);
                }

                if (!isCharging && level == spStore.getBatteryLowLevel()) {
                    // battery @ 4%
                    Intent startIntent = new Intent(context, RingtonePlayingService.class);
                    startIntent.putExtra("type", "alarm");
                    context.startService(startIntent);
                }


                if (intent.getAction().equals(Intent.ACTION_POWER_CONNECTED)) {
                    Log.w("Power", "CONNECTED");
                }
                if (intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED)) {
                    Log.w("Power", "DISCONNECTED");

                }
            }
        }


    }



}


