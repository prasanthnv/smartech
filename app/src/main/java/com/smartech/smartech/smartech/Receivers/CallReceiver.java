package com.smartech.smartech.smartech.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.smartech.smartech.smartech.Core.Profile;
import com.smartech.smartech.smartech.Core.ProfileChanger;
import com.smartech.smartech.smartech.SharedPreferenceStore.SharedPreferenceStore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class CallReceiver extends BroadcastReceiver {
    SharedPreferenceStore spStore;
    @Override
    public void onReceive(final Context context, Intent intent) {
        spStore = new SharedPreferenceStore(context);
        final ProfileChanger profileChanger = new ProfileChanger(context);
        TelephonyManager telephony = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        try {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);

            if(state.equals(TelephonyManager.EXTRA_STATE_RINGING)){
                Toast.makeText(context,"Ringing State Number is - " + incomingNumber, Toast.LENGTH_SHORT).show();
                try {
                    JSONArray ignored_numbers = new JSONArray(spStore.getIgnoredList());
                    boolean found = false;
                    for (int i=0;i<ignored_numbers.length();i++){
                        if(ignored_numbers.get(i).equals(incomingNumber)){
                            found = true;
                        }
                    }
                    if(found){
//                    Toast.makeText(context, "Activating....", Toast.LENGTH_SHORT).show();
//                    spStore.setPrevRingState(profileChanger.getRingMode());
//                    ProfileChanger.setRingMode("RING");
                        Intent startIntent = new Intent(context, RingtonePlayingService.class);
                        startIntent.putExtra("type", "ring");
                        context.startService(startIntent);
//
//                    Intent startIntent = new Intent(getApplicationContext(), RingtonePlayingService.class);
//                    stopService(startIntent);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                Intent startIntent = new Intent(context, RingtonePlayingService.class);
context.stopService(startIntent);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }




}