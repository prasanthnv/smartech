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



    public void setProfile(String profile){
        SharedPreferences.Editor editor = this.preferences.edit();
        editor.putString("ACTIVE_PROFILE",profile);
        editor.commit();
    }

    public String getProfile(){
        return this.preferences.getString("ACTIVE_PROFILE","{}");
    }

}
