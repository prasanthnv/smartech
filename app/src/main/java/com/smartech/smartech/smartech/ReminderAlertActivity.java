package com.smartech.smartech.smartech;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.smartech.smartech.smartech.Database.DatabaseHandler;

public class ReminderAlertActivity extends AppCompatActivity {
TextView txt_note,txt_title;
Intent incomingIntent;
Button btn_okay;
    DatabaseHandler dbHadler;
    SQLiteDatabase sqLiteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_alert);
        /*-----------*/
        dbHadler = new DatabaseHandler(getApplicationContext());
        sqLiteDatabase = dbHadler.getWritableDatabase();
        /*-----------*/
        txt_title =  findViewById(R.id.txt_title);
        txt_note =  findViewById(R.id.txt_note);
        btn_okay = findViewById(R.id.btn_okay);

        incomingIntent = getIntent();

        txt_title.setText(incomingIntent.getStringExtra("title"));
        txt_note.setText(incomingIntent.getStringExtra("note"));
        btn_okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHadler.deleteReminder(sqLiteDatabase,incomingIntent.getStringExtra("id"));
                finish();
            }
        });

    }
}
