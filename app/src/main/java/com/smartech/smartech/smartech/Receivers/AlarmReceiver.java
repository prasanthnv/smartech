package com.smartech.smartech.smartech.Receivers;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.smartech.smartech.smartech.AlarmRingActivity;
import com.smartech.smartech.smartech.Database.DatabaseHandler;


public class AlarmReceiver extends BroadcastReceiver {
    DatabaseHandler dbHandler;
    SQLiteDatabase sqLiteDatabase;
    AlertDialog alarmDialog;
    int times = 1, difficulty = 1;
    @Override
    public void onReceive(Context context, Intent alarm_intent) {
        // TODO Auto-generated method stub
        /*-----------*/
        dbHandler = new DatabaseHandler(context);
        sqLiteDatabase = dbHandler.getWritableDatabase();
        /*-----------*/
        String alarm_id = alarm_intent.getStringExtra("id");
        Intent intent = new Intent(context, AlarmRingActivity.class);
        Log.e("FROM RECIVER => ",""+alarm_id);
        intent.putExtra("id",alarm_id);
        context.startActivity(intent);


    }




}