package com.smartech.smartech.smartech.SharedPreferenceStore;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by prasanthvenugopal on 13/04/18.
 */

public class SharedPreferenceStore {
    SharedPreferences preferences;
    public SharedPreferenceStore(Context context){
        this.preferences = context.getSharedPreferences("smartech_preferences",Context.MODE_PRIVATE);
    }



    public void setProfileName(String name){
        SharedPreferences.Editor editor = this.preferences.edit();
        editor.putString("PROFILE_NAME",name);
        editor.commit();
    }

    public String getProfileName(){
        return this.preferences.getString("PROFILE_NAME","");
    }
    public String getProfileID(){
        return this.preferences.getString("PROFILE_ID","");
    }

}
