package com.smartech.smartech.smartech;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.smartech.smartech.smartech.Adapters.ProfilesListAdapter;
import com.smartech.smartech.smartech.Adapters.ReminderListAdapter;
import com.smartech.smartech.smartech.Database.DatabaseHandler;
import com.smartech.smartech.smartech.SharedPreferenceStore.SharedPreferenceStore;

import java.util.ArrayList;

public class ReminderActivity extends AppCompatActivity {
FloatingActionButton fab_new;
ListView list_reminders;

    DatabaseHandler dbHandler;
    SharedPreferenceStore spStore;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        list_reminders = findViewById(R.id.list_reminders);
        dbHandler = new DatabaseHandler(getApplicationContext());
        db = dbHandler.getReadableDatabase();
         fab_new = (FloatingActionButton) findViewById(R.id.fab_new);
        fab_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(getApplicationContext(),NewReminderActivity.class));
            }
        });
        updateReminderList();
    }

    public void updateReminderList(){
        final ArrayList titles = new ArrayList();
        final ArrayList ids = new ArrayList();
        final ArrayList notes = new ArrayList();
        Cursor profilesCursor = dbHandler.getReminder(db);
        while(profilesCursor.moveToNext()){
            String id = profilesCursor.getString(0);
            String title = profilesCursor.getString(1);
            String note = profilesCursor.getString(2);
            ids.add(id);
            titles.add(title);
            notes.add(note);
        }

        ReminderListAdapter profilesListAdapter = new ReminderListAdapter(ReminderActivity.this,titles,notes);
        list_reminders.setAdapter(profilesListAdapter);
        list_reminders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, final long id) {
                new AlertDialog.Builder(ReminderActivity.this)
                        .setTitle("Delete")
                        .setMessage("Are you sure to delete")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                dbHandler.deleteReminder(db,ids.get(position).toString());
                                updateReminderList();
                            }})
                        .setNegativeButton(android.R.string.no, null).show();



            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateReminderList();
    }
}
