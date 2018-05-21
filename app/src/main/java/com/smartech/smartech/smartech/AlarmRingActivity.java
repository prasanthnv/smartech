package com.smartech.smartech.smartech;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.smartech.smartech.smartech.Database.DatabaseHandler;
import com.smartech.smartech.smartech.Receivers.RingtonePlayingService;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class AlarmRingActivity extends AppCompatActivity {
    DatabaseHandler dbHandler;
    SQLiteDatabase sqLiteDatabase;
    int times = 1, difficulty = 1;
    TextView txt_number_one,txt_number_two;
    Button btn_okay;
    EditText txt_answer;
    int current_answer = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_ring);
        /*-----------*/
        dbHandler = new DatabaseHandler(getApplicationContext());
        sqLiteDatabase = dbHandler.getWritableDatabase();
        /*-----------*/
        txt_number_one = findViewById(R.id.txt_first_number);
        txt_number_two = findViewById(R.id.txt_second_number);
        txt_answer  = findViewById(R.id.txt_answer);
btn_okay = findViewById(R.id.btn_okay);
        btn_okay.setOnClickListener(btnClickListener);
        Intent alarm_intent = getIntent();
        String alarm_id = alarm_intent.getStringExtra("id");

        Cursor alarmCursor = dbHandler.getAlarmByID(sqLiteDatabase,alarm_id);
        if (alarmCursor.moveToFirst()) {
            do {
                String times = alarmCursor.getString(alarmCursor.getColumnIndex("times"));
                String difficulty = alarmCursor.getString(alarmCursor.getColumnIndex("difficulty"));
                times = times;
                difficulty = difficulty;
                Log.e("Times",times);
                Log.e("difficulty",difficulty);
                startSound();
                askQuestions();

            } while (alarmCursor.moveToNext());
        }


    }

    public void askQuestions(){

           if(times != 0){
               int number_one = getRandomNumber();
               int number_two = getRandomNumber();
               current_answer = number_one + number_two;
               txt_number_one.setText(""+number_one);
               txt_number_two.setText(""+number_two);
             txt_answer.setText(""+current_answer);
           }else{
               Toast.makeText(this, "Alarm finished !", Toast.LENGTH_SHORT).show();
               stopSound();
               finish();
           }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }


    public int getRandomNumber(){
        Random r = new Random();
        int Low =(int) Math.pow(10, difficulty-1);
        int High =((int) Math.pow(10, difficulty) - 1);
        Log.e("low",Low+"");
        Log.e("high",High+"");
       return r.nextInt(High-Low) + Low;
    }

    private void startSound(){
        // stating alarm
        Intent startIntent = new Intent(getApplicationContext(), RingtonePlayingService.class);
        startIntent.putExtra("type","alarm");
        startService(startIntent);
    }

    private void stopSound(){
        // stating alarm
        Intent startIntent = new Intent(getApplicationContext(), RingtonePlayingService.class);
        stopService(startIntent);
    }

    View.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int answer  = 0;
            String user_input = txt_answer.getText().toString();
            if(!user_input.equals("")){
                answer = Integer.parseInt(user_input);
            }

            Toast.makeText(AlarmRingActivity.this, answer+"", Toast.LENGTH_SHORT).show();
            if(answer == current_answer){
                times--;
            }
            txt_answer.setText("");
            askQuestions();
        }
    };
}
