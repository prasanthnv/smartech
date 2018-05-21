package com.smartech.smartech.smartech.Receivers;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.smartech.smartech.smartech.AlarmRingActivity;
import com.smartech.smartech.smartech.Database.DatabaseHandler;


import java.util.concurrent.ThreadLocalRandom;

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
        intent.putExtra("id",alarm_id);
        context.startActivity(intent);
        Log.e( "Alarm received! " , alarm_id);


    }


    public void askQuestions(){
        int randomNum = ThreadLocalRandom.current().nextInt(0, difficulty);
    }

}