package com.smartech.smartech.smartech.Core;



import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by prasanthvenugopal on 13/04/18.
 */

public class Profile implements Serializable{
    long id;
    String name = "profile" + new Random().nextInt(6) + 5;
    String settings;
    String type;
    String trigger;
    String sound;

    boolean wifi,bluetooth;

    int brigtness = -1;


    public final static String TYPE_WIFI = "wifi";
    public final static String TYPE_LOCATION = "location";
    public final static String TYPE_ALARM = "alarm";
    public final static String SOUND_VIBRATE = "vibrate";
    public final static String SOUND_SILENT = "silent";
    public final static String SOUND_RING = "ring";
    public final static String SOUND_LOUD = "loud";
    public final static String SOUND_MEDIUM = "medium";

    public Profile(String name,  String type, String trigger, String sound, int brigtness) {
        this.name = name;
        this.type = type;
        this.trigger = trigger;
        this.sound = sound;
        this.brigtness = brigtness;
    }

    public Profile(String name, String type){
       this.name = name;
       this.type = type;
    }

    public Profile(JSONObject profileObj){
        // for generating profile from Existing profile
        try {
            this.setName(profileObj.getString("name"));
            JSONObject settingsObj = new JSONObject(profileObj.getString("settings"));
            if(settingsObj.has("wifi")){
                this.setWifi(settingsObj.getBoolean("wifi"));
            }
            if(settingsObj.has("brightness")){
                this.setBrigtness(settingsObj.getInt("brightness"));
            }
            if(settingsObj.has("sound")){
                this.setSound(settingsObj.getString("sound"));
            }
            if(settingsObj.has("bluetooth")){
                this.setBluetooth(settingsObj.getBoolean("bluetooth"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void setName(String name) {
        this.name = name;
    }

    public void setSettings(String settings) {
        this.settings = settings;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTrigger(String trigger) {
        this.trigger = trigger;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public void setBrigtness(int brigtness) {
        this.brigtness = brigtness;
    }

    public String getName() {
        return name;
    }

    public boolean isWifi() {
        return wifi;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setWifi(boolean wifi) {
        this.wifi = wifi;
    }

    public JSONObject getSettings() {
        JSONObject settingsObj = new JSONObject();
        try {
            if(this.getBrigtness() != -1){
                settingsObj.put("brightness",this.getBrigtness());
            }
            if(this.getSound() != null){
                settingsObj.put("sound",this.getSound());
            }
            if(this.isWifi()){
                settingsObj.put("wifi",this.isWifi());
            }
            if(this.isBluetooth()){
                settingsObj.put("bluetooth",this.isBluetooth());
            }

            settings = settingsObj.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return settingsObj;
    }


    public boolean isBluetooth() {
        return bluetooth;
    }

    public void setBluetooth(boolean bluetooth) {
        this.bluetooth = bluetooth;
    }

    public String getType() {
        return type;
    }

    public String getTrigger() {
        return trigger;
    }

    public String getSound() {
        return sound;
    }

    public int getBrigtness() {
        return brigtness;
    }


    public String getProfileInfoJson(){
        // returns profile info jso
        String info = "{}";
      JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",this.id + "");
            jsonObject.put("name",this.name + "");
            jsonObject.put("settings",this.getSettings());
            jsonObject.put("type",this.getType());
            jsonObject.put("trigger",this.getTrigger());
            info = jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return info;
    }

    public int getBrightnessPercentage(){
        if(this.hasBrightness()){
            return (getBrigtness() * 100) / 255 ;
        }
        return 0;
    }


public boolean hasBrightness(){
        if(this.getBrigtness() != -1){
            return true;
        }else{
            return false;
        }
}



}
