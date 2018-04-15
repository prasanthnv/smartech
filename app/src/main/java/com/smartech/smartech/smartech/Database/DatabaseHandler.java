package com.smartech.smartech.smartech.Database;

import android.content.ContentValues;
import android.content.Context;
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
        String CREATE_PROFILES_TABLE = "CREATE TABLE profiles( INTEGER PRIMARY KEY,name TEXT, type TEXT,trigger TEXT,data TEXT)";

        // query to create contacts table
        String CREATE_CONTACTS_TABLE = "CREATE TABLE contacts( INTEGER PRIMARY KEY,name TEXT, phone TEXT)";

        // creating tables
        db.execSQL(CREATE_PROFILES_TABLE);
        db.execSQL(CREATE_CONTACTS_TABLE);
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

}
