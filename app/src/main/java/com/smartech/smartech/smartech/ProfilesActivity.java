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
import com.smartech.smartech.smartech.Database.DatabaseHandler;
import com.smartech.smartech.smartech.SharedPreferenceStore.SharedPreferenceStore;

import java.util.ArrayList;

public class ProfilesActivity extends AppCompatActivity {
    DatabaseHandler dbHandler;
    SharedPreferenceStore spStore;
    SQLiteDatabase db;
    ListView list_profiles;
    FloatingActionButton float_btn_add_new;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiles);
        float_btn_add_new =  findViewById(R.id.float_new_button);
         /*---------------------*/
        spStore = new SharedPreferenceStore(getApplicationContext());
        dbHandler = new DatabaseHandler(getApplicationContext());
        db = dbHandler.getWritableDatabase();

        /*--------------------------*/
        list_profiles = (ListView) findViewById(R.id.list_profiles);
        float_btn_add_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),NewProfileActivity.class));
                finish();
            }
        });
        updateProfileList();
    }

    public void updateProfileList(){
        final ArrayList profilesNames = new ArrayList();
        final ArrayList profileIDs = new ArrayList();
        final ArrayList profileData = new ArrayList();
        Cursor profilesCursor = dbHandler.getProfiles(db);
        while(profilesCursor.moveToNext()){
            String id = profilesCursor.getString(0);
            String name = profilesCursor.getString(1);
            String data = profilesCursor.getString(4);
            profileIDs.add(id);
            profilesNames.add(name);
            profileData.add(data);
        }

        ProfilesListAdapter profilesListAdapter = new ProfilesListAdapter(ProfilesActivity.this,profilesNames);
        list_profiles.setAdapter(profilesListAdapter);
        list_profiles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),profilesNames.get(position).toString(),Toast.LENGTH_LONG).show();
            }
        });
        list_profiles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Toast.makeText(ProfilesActivity.this, profileData.get(position).toString(), Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(ProfilesActivity.this)
                        .setTitle("Delete")
                        .setMessage("Are you sure to delete")
                        // .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                dbHandler.deleteProfile(db,profileIDs.get(position).toString());
                                updateProfileList();
                            }})
                        .setNegativeButton(android.R.string.no, null).show();



            }
        });
    }
}
