package com.smartech.smartech.smartech;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.smartech.smartech.smartech.Adapters.ReminderListAdapter;
import com.smartech.smartech.smartech.Database.DatabaseHandler;
import com.smartech.smartech.smartech.Receivers.AlarmReceiver;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AlarmsActivity extends AppCompatActivity {
    final static int RQS_1 = 1;
    AlertDialog alarmDialog;
    SeekBar range_difficulty, range_times;
    TextView txt_difficulty, txt_times;
    int difficulty = 1, times = 1;
    DatabaseHandler dbHadler;
    SQLiteDatabase sqLiteDatabase;
    ListView list_alarms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarms);
        /*-----------*/
        dbHadler = new DatabaseHandler(getApplicationContext());
        sqLiteDatabase = dbHadler.getWritableDatabase();
        /*-----------*/
        list_alarms = findViewById(R.id.list_alarms);

        updateAlarmList();
    }

    public void updateAlarmList() {
        final ArrayList titles = new ArrayList();
        final ArrayList ids = new ArrayList();
        final ArrayList notes = new ArrayList();
        Cursor alarmCursor = dbHadler.getAlarms(sqLiteDatabase);
        int i = 1;
        while (alarmCursor.moveToNext()) {
            String id = alarmCursor.getString(alarmCursor.getColumnIndex("id"));
            String time = alarmCursor.getString(alarmCursor.getColumnIndex("time"));
            final Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(Long.parseLong(time));
            final String timeString =
                    new SimpleDateFormat("hh:mm a").format(cal.getTime());
            ids.add(id);
            titles.add("Alarm " + i);
            notes.add(timeString);
            i++;
        }
        alarmCursor.close();
        ReminderListAdapter profilesListAdapter = new ReminderListAdapter(AlarmsActivity.this, titles, notes);
        list_alarms.setAdapter(profilesListAdapter);
        list_alarms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, final long id) {
                new AlertDialog.Builder(AlarmsActivity.this)
                        .setTitle("Delete")
                        .setMessage("Are you sure to delete")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                dbHadler.deleteAlarm(sqLiteDatabase, ids.get(position).toString());
                                updateAlarmList();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();


            }
        });
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
                        progress = progress + 1;
                        if (progress > 5) progress = 5;
                        txt_difficulty.setText(progress + "/" + (seekBar.getMax() + 1) );
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
                        progress = progress + 1;
                        if (progress > 5) progress = 5;
                        txt_times.setText(progress + "/" + (seekBar.getMax() + 1) );
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

    public void showDatePicker() {
        Calendar mcurrentDate = Calendar.getInstance();
        DatePickerDialog mDatePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                showTimePicker(dayOfMonth, monthOfYear, year);
            }

        }, mcurrentDate.get(Calendar.YEAR), mcurrentDate.get(Calendar.MONTH), mcurrentDate.get(Calendar.DAY_OF_MONTH));
        mDatePicker.show();
    }

    private void showTimePicker(final int day, final int month, final int year) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(AlarmsActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                Calendar calSet = Calendar.getInstance();
                calSet.set(Calendar.DATE, day);  //1-31
                calSet.set(Calendar.MONTH, month);  //first month is 0!!! January is zero!!!
                calSet.set(Calendar.YEAR, year);//year...

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
        long id = dbHadler.addAlarm("" + targetCal.getTimeInMillis(), difficulty, times, "");
        final String timeString =
                new SimpleDateFormat("hh:mm a").format(targetCal.getTime());
        Toast.makeText(getApplicationContext(), "Alarm is set for"
                + timeString, Toast.LENGTH_LONG).show();

        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        intent.putExtra("id", "" + id);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getBaseContext(), RQS_1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(),
                pendingIntent);
       finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateAlarmList();
    }
}
