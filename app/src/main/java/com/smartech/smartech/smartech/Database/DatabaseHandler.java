package com.smartech.smartech.smartech.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.smartech.smartech.smartech.Core.Profile;

/**
 * Created by prasanthvenugopal on 11/04/18.
 */

public class DatabaseHandler extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "smartech.db";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        // query to create profiles table
        String CREATE_PROFILES_TABLE = "CREATE TABLE profiles(id INTEGER PRIMARY KEY,name TEXT, type TEXT,trigger TEXT,data TEXT)";

        // query to create contacts table
        String CREATE_CONTACTS_TABLE = "CREATE TABLE contacts(id INTEGER PRIMARY KEY,name TEXT, phone TEXT)";
        String CREATE_REMINDER_TABLE = "CREATE TABLE reminders(id INTEGER PRIMARY KEY,title TEXT,data TEXT, trigger TEXT)";
        String CREATE_ALARM_TABLE = "CREATE TABLE alarms(id INTEGER PRIMARY KEY,time TEXT,difficulty TEXT,times TEXT, message TEXT)";

        // creating tables
        db.execSQL(CREATE_PROFILES_TABLE);
        db.execSQL(CREATE_CONTACTS_TABLE);
        db.execSQL(CREATE_REMINDER_TABLE);
        db.execSQL(CREATE_ALARM_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS profiles");
        db.execSQL("DROP TABLE IF EXISTS contacts");

        // Create tables again
        onCreate(db);
    }


  public long addProfile(Profile profile){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", profile.getName());       // Name
        values.put("type", profile.getType());       // type
        values.put("trigger", profile.getTrigger()); // trigger
        values.put("data", profile.getProfileInfoJson()); // trigger
        // Inserting Row
        long id =  db.insert("profiles", null, values);
//        profile.setId(id);
      //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
        return id;
    }


    public Cursor getProfiles(SQLiteDatabase db){
        String query = "Select * from profiles";
        String[] params = null;
        Cursor rs = db.rawQuery(query,params);
        return rs;
    }

    public Cursor getProfilesWithLocation(SQLiteDatabase db){
        String query = "Select * from profiles where type='location'";
        String[] params = null;
        Cursor rs = db.rawQuery(query,params);
        return rs;
    }

    public void deleteContacts(SQLiteDatabase db,String id){
        db.execSQL("delete from contacts where id='"+id+"'");
    }
    public void deleteProfile(SQLiteDatabase db,String id){
        db.execSQL("delete from profiles where id='"+id+"'");
    }


    public Cursor getWifiAction(SQLiteDatabase db,String ssid){
        String query = "Select * from profiles where type='wifi' AND trigger = '"+ssid+"'";
        String[] params = null;
        Cursor rs = db.rawQuery(query,params);
        return rs;
    }
    public Cursor getSmsAction(SQLiteDatabase db,String message){
        String query = "Select * from profiles where type='sms' AND trigger = '"+message+"'";
        String[] params = null;
        Cursor rs = db.rawQuery(query,params);
        return rs;
    }


    public long addReminder(String title,String data,String triiger ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", title);       // Name
        values.put("data", data);       // Name
        values.put("trigger", triiger); // trigger
        // Inserting Row
        long id =  db.insert("reminders", null, values);
//        profile.setId(id);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
        return id;
    }


    public Cursor getReminder(SQLiteDatabase db){
        String query = "Select * from reminders";
        String[] params = null;
        Cursor rs = db.rawQuery(query,params);
        return rs;
    }

    public void deleteReminder(SQLiteDatabase db,String id){
        db.execSQL("delete from reminders where id='"+id+"'");
    }

    public long addAlarm(String time,int difficulty,int times,String message ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("difficulty", difficulty);
        values.put("time", time);
        values.put("times", times);
        values.put("message", message);
        // Inserting Row
        long id =  db.insert("alarms", null, values);
        db.close(); // Closing database connection
        return id;
    }


    public Cursor getAlarms(SQLiteDatabase db){
        String query = "Select * from alarms";
        String[] params = null;
        Cursor rs = db.rawQuery(query,params);
        return rs;
    }

    public Cursor getAlarmByID(SQLiteDatabase db,String id){
        String query = "Select * from alarms  where id = '"+id+"'";
        String[] params = null;
        Cursor rs = db.rawQuery(query,params);
        return rs;
    }

    public void deleteAlarm(SQLiteDatabase db,String id){
        db.execSQL("delete from reminders where id='"+id+"'");
    }
}
