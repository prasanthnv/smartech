package com.smartech.smartech.smartech.SharedPreferenceStore;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by prasanthvenugopal on 13/04/18.
 */

public class    SharedPreferenceStore {
    SharedPreferences preferences;
    public SharedPreferenceStore(Context context){
        this.preferences = context.getSharedPreferences("smartech_preferences",Context.MODE_PRIVATE);
    }



    public void setProfile(String profile){
        SharedPreferences.Editor editor = this.preferences.edit();
        editor.putString("ACTIVE_PROFILE",profile);
        editor.commit();
    }

    public void setBatteryAlarmEnabled(boolean value){
        SharedPreferences.Editor editor = this.preferences.edit();
        editor.putBoolean("BATTERY_ALARM",value);
        editor.commit();
    }

    public boolean isBatteryAlarmEnabled(){
        return this.preferences.getBoolean("BATTERY_ALARM",false);
    }

    public String getProfile(){
        return this.preferences.getString("ACTIVE_PROFILE","{name:''}");
    }


    public void setIgnoreList(String data){
        SharedPreferences.Editor editor = this.preferences.edit();
        editor.putString("IGNORE_LIST",data);
        editor.commit();
    }
    public String getIgnoredList(){
        return this.preferences.getString("IGNORE_LIST","[]");
    }

    public void setBatteryLowLevel(int data){
        SharedPreferences.Editor editor = this.preferences.edit();
        editor.putInt("BATTERY_LOW",data);
        editor.commit();
    }
    public int getBatteryLowLevel(){
        return this.preferences.getInt("BATTERY_LOW",5);
    }


    public void setLastCall(String data){
        SharedPreferences.Editor editor = this.preferences.edit();
        editor.putString("LASTCALL",data);
        editor.commit();
    }
    public String  getLastCall(){
        return this.preferences.getString("LASTCALL","{}");
    }

    public void setPrevRingState(String data){
        SharedPreferences.Editor editor = this.preferences.edit();
        editor.putString("PREV_RING_STATE",data);
        editor.commit();
    }
    public String  getPrevRingState(){
        return this.preferences.getString("PREV_RING_STATE","{}");
    }

    public void setBackupNumber(String data){
        SharedPreferences.Editor editor = this.preferences.edit();
        editor.putString("BACKUP_NUMBER",data);
        editor.commit();
    }

    public String  getBackupNumber(){
        return this.preferences.getString("BACKUP_NUMBER","");
    }

}

