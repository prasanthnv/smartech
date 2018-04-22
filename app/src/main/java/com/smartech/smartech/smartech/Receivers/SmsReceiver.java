package com.smartech.smartech.smartech.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.smartech.smartech.smartech.Core.Profile;
import com.smartech.smartech.smartech.Core.ProfileChanger;
import com.smartech.smartech.smartech.Database.DatabaseHandler;
import com.smartech.smartech.smartech.R;
import com.smartech.smartech.smartech.Utils.Alert;

import org.json.JSONObject;

/**
 * Created by prasanthvenugopal on 11/04/18.
 */

public class SmsReceiver extends BroadcastReceiver{
    final SmsManager sms = SmsManager.getDefault();
    DatabaseHandler dbHandler;
    SQLiteDatabase db;
    @Override
    public void onReceive(Context context, Intent intent) {
        dbHandler = new DatabaseHandler(context);
        db = dbHandler.getReadableDatabase();
        // Get the object of SmsManager
// Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();

        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();

                    Log.i("SmsReceiver", "senderNum: "+ senderNum + "; message: " + message);
                    Cursor rs = dbHandler.getSmsAction(db,message);
                if(rs.moveToFirst()){

                    String profileData = rs.getString(rs.getColumnIndex("data"));
                    Log.e("here ==> ", profileData);
                    Profile profile = new Profile(new JSONObject(profileData));
                    ProfileChanger.applyProfile(context,profile);
                }
                    // Show alert
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, "senderNum: "+ senderNum + ", message: " + message, duration);
                    toast.show();


                    switch (message){
                        case "pc_ring":
                            Alert.showModeDilaog(context, R.drawable.ic_volume_on,"Ring Mode Acticated",2000);
                            ProfileChanger.setRingMode("RING");
                            break;
                        case "pc_silent":
                            Alert.showModeDilaog(context,R.drawable.ic_volume_off,"Silent Mode Acticated",2000);
                            ProfileChanger.setRingMode("SILENT");

                            break;
                        case "pc_vibrate":
                            Alert.showModeDilaog(context,R.drawable.ic_vibration,"Vibrate Mode Acticated",2000);
                            ProfileChanger.setRingMode("VIBRATE");
                            break;
                        case "pc_tourch_on":
                            Alert.showModeDilaog(context,android.R.drawable.ic_menu_camera,"Tourch On Mode Acticated",2000);
                            ProfileChanger.turnTourchOn(context);
                            break;
                        case "pc_tourch_off":
                            ProfileChanger.turnTourchOff();
                            break;
                        case "pc_play":
                            Alert.showModeDilaog(context,android.R.drawable.ic_menu_view,"Hey Am Here !",2000);
                            ProfileChanger.playSound();
                            break;
                        case "pc_stop":
                            Alert.showModeDilaog(context,android.R.drawable.ic_menu_view,"Stoped Playing !",2000);
                            ProfileChanger.stopSound();
                            break;
                        case "pc_where_are_you":
                            Alert.showModeDilaog(context,android.R.drawable.ic_popup_reminder,"Finding Location...",2000);
//                            ProfileChanger.getMyLocation();
                            break;


                    }

                } // end for loop
            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);

        }
    }
}
