package com.smartech.smartech.smartech;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ReminderAlertActivity extends AppCompatActivity {
TextView txt_note,txt_title;
Intent incomingIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_alert);

        txt_title =  findViewById(R.id.txt_title);
        txt_note =  findViewById(R.id.txt_note);

        incomingIntent = getIntent();

        txt_title.setText(incomingIntent.getStringExtra("title"));
        txt_note.setText(incomingIntent.getStringExtra("note"));


    }
}
