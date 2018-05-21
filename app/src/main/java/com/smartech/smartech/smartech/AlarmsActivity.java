package com.smartech.smartech.smartech;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.smartech.smartech.smartech.Database.DatabaseHandler;
import com.smartech.smartech.smartech.Receivers.AlarmReceiver;

import java.util.Calendar;

public class AlarmsActivity extends AppCompatActivity {
    final static int RQS_1 = 1;
    AlertDialog alarmDialog;
    SeekBar range_difficulty,range_times;
    TextView txt_difficulty,txt_times;
    int difficulty = 1,times = 1;
    DatabaseHandler dbHadler;
    SQLiteDatabase sqLiteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarms);
        /*-----------*/
        dbHadler = new DatabaseHandler(getApplicationContext());
        sqLiteDatabase = dbHadler.getWritableDatabase();
        /*-----------*/

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_alarm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.add_alarm:
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        AlarmsActivity.this);
                LayoutInflater inflater = (LayoutInflater) getApplicationContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.dialog_new_alarm, null);
                alertDialogBuilder.setView(view);
                alertDialogBuilder.setTitle("New Alarm");
                range_difficulty = view.findViewById(R.id.range_difficulty);
                range_times = view.findViewById(R.id.range_times);
                txt_difficulty = view.findViewById(R.id.txt_difficulty);
                txt_times = view.findViewById(R.id.txt_times);
                range_difficulty.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if(progress < 1) progress = 1;
                        txt_difficulty.setText(progress + "/" + seekBar.getMax());
                        difficulty = progress;
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                range_times.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if(progress < 1) progress = 1;
                        txt_times.setText(progress + "/" + seekBar.getMax());
                        times = progress;
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alarmDialog.cancel();
                    }
                });
                alertDialogBuilder.setPositiveButton("Choose Time", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        showDatePicker();
                    }
                });
                alarmDialog = alertDialogBuilder.create();
                alarmDialog.show();

        }
        return true;

    }
public void showDatePicker(){
    Calendar mcurrentDate = Calendar.getInstance();
    DatePickerDialog  mDatePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            showTimePicker(dayOfMonth,monthOfYear,year);
        }

    }, mcurrentDate.get(Calendar.YEAR), mcurrentDate.get(Calendar.MONTH), mcurrentDate.get(Calendar.DAY_OF_MONTH));
    mDatePicker.show();
}
    private void showTimePicker(final int day, final int month, final int year){
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(AlarmsActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                Calendar calSet = Calendar.getInstance();
                calSet.set(Calendar.DATE,day);  //1-31
                calSet.set(Calendar.MONTH,month);  //first month is 0!!! January is zero!!!
                calSet.set(Calendar.YEAR,year);//year...

                calSet.set(Calendar.HOUR_OF_DAY, selectedHour);
                calSet.set(Calendar.MINUTE, selectedMinute);
                calSet.set(Calendar.SECOND, 0);
                calSet.set(Calendar.MILLISECOND, 0);

                setAlarm(calSet);
            }
        }, hour, minute, false);//Yes 12 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    private void setAlarm(Calendar targetCal) {
        long id = dbHadler.addAlarm(""+targetCal.getTimeInMillis(),difficulty,times,"");
        Toast.makeText(getApplicationContext(),"\n\n***\n" + "Alarm is set "
                + targetCal.getTime() + "\n" + "***\n",Toast.LENGTH_LONG).show();

        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        intent.putExtra("id",id);
        Toast.makeText(getApplicationContext(),"ID : "+id,Toast.LENGTH_LONG).show();
        intent.putExtra("time", targetCal.getTimeInMillis());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getBaseContext(), RQS_1, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(),
                pendingIntent);

    }

}
