package com.smartech.smartech.smartech.Core;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.util.Log;
import android.widget.Toast;

import com.smartech.smartech.smartech.SharedPreferenceStore.SharedPreferenceStore;

/**
 * Created by prasanthvenugopal on 11/04/18.
 */

public class ProfileChanger {
    Context context;
    AudioManager audioManager;
    SharedPreferenceStore spStore;
    public ProfileChanger(Context context) {
        spStore = new SharedPreferenceStore(context);
        this.context = context;
        this.audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

public void setBrightness(int brightness){
    android.provider.Settings.System.putInt(context.getContentResolver(),
            android.provider.Settings.System.SCREEN_BRIGHTNESS, brightness);
}
    public void applyProfile(Profile profile){
        spStore.setProfileName(profile.getName());
        Log.e("Profile => ", profile.getName());
        Log.e("Profile => ", profile.getSound());
        switch (profile.getSound()){
            case Profile.SOUND_RING:
                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                break;
                case Profile.SOUND_VIBRATE:
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                break;
            case Profile.SOUND_SILENT:
                audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                break;
            default:
                Toast.makeText(context,"No sound selected",Toast.LENGTH_LONG).show();
                   break;
        }

        if(profile.hasBrightness()){
            this.setBrightness(profile.getBrigtness());
        }


    }




}
