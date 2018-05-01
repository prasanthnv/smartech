package com.smartech.smartech.smartech.Core;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import com.smartech.smartech.smartech.R;
import com.smartech.smartech.smartech.SharedPreferenceStore.SharedPreferenceStore;
import com.smartech.smartech.smartech.Utils.Alert;

/**
 * Created by prasanthvenugopal on 11/04/18.
 */

public class ProfileChanger {
    static Context context;
    static AudioManager audioManager;
    static SharedPreferenceStore spStore;
    static MediaPlayer mediaPlayer;
    static Camera camera = null;
    static Camera.Parameters camera_parameters;
    WifiManager wifiManager;
    public ProfileChanger(Context context) {
        spStore = new SharedPreferenceStore(context);
        this.context = context;
        this.audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

public void setBrightness(int brightness){
    android.provider.Settings.System.putInt(context.getContentResolver(),
            android.provider.Settings.System.SCREEN_BRIGHTNESS, brightness);
}
    public static void applyProfile(Context context,Profile profile){
      audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
       spStore = new SharedPreferenceStore(context);
        spStore.setProfile(profile.getProfileInfoJson());
        Log.e("Profile => ", profile.getName());
        Log.e("Profile => ", profile.getSound());
        switch (profile.getSound()){
            case Profile.SOUND_RING:
                Alert.showModeDilaog(context, R.drawable.ic_volume_on,"Ring Mode Acticated",2000);

                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                break;
                case Profile.SOUND_VIBRATE:
                    Alert.showModeDilaog(context,R.drawable.ic_vibration,"Vibrate Mode Acticated",2000);

                    audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                break;
            case Profile.SOUND_SILENT:
                Alert.showModeDilaog(context,R.drawable.ic_volume_off,"Silent Mode Acticated",2000);

                audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                break;
            default:
                Toast.makeText(context,"No sound selected",Toast.LENGTH_LONG).show();
                   break;
        }




    }

    public static void setRingMode(String mode) {
        switch (mode) {
            case "VIBRATE":
                audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                break;
            case "SILENT":
                audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                break;
            case "RING":
                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                break;
        }
    }


    public static void turnTourchOn(Context context) {
        PackageManager pm = context.getPackageManager();
        if (pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
            camera = Camera.open();
            camera_parameters = camera.getParameters();
            camera_parameters.setFlashMode(camera_parameters.FLASH_MODE_TORCH);
            camera.setParameters(camera_parameters);
            camera.startPreview();
        }
    }

    public static void turnTourchOff() {
        camera.stopPreview();
        camera.release();
        camera = null;
    }

    public static void playSound() {
        mediaPlayer.setVolume(50,50);
        mediaPlayer.start();
    }

    public static void stopSound() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }


}
