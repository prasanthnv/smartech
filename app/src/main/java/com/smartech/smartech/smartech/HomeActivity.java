package com.smartech.smartech.smartech;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.smartech.smartech.smartech.Core.Profile;
import com.smartech.smartech.smartech.Core.ProfileChanger;
import com.smartech.smartech.smartech.SharedPreferenceStore.SharedPreferenceStore;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class HomeActivity extends AppCompatActivity {
SharedPreferenceStore spStore;
TextView txt_profile_name;
    Profile active_profile;
    Button btn_info_sound,btn_info_brightness,btn_info_bluetooth,btn_info_wifi;
    ImageView image_background;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_home);
        spStore = new SharedPreferenceStore(getApplicationContext());
        txt_profile_name = findViewById(R.id.txt_profile_name);
        btn_info_sound = findViewById(R.id.btn_info_sound);
        btn_info_brightness = findViewById(R.id.btn_info_brightness);
        btn_info_bluetooth = findViewById(R.id.btn_info_bluetooth);
        btn_info_wifi = findViewById(R.id.btn_info_wifi);
        image_background = findViewById(R.id.image_background);

        showInfo();
    }

    @Override
    protected void onResume() {
        super.onResume();

        showInfo();
    }

    public void showInfo(){
        ProfileChanger profileChanger = new ProfileChanger(getApplicationContext());
        if(!spStore.getProfile().equals("")){
            try {
                active_profile = new Profile(new JSONObject(spStore.getProfile()));
                txt_profile_name.setText(active_profile.getName());

            } catch (JSONException e) {

            }
        }else{
            active_profile =  profileChanger.buildCurrentProfile();
        }
        btn_info_brightness.setText(active_profile.getBrightnessPercentage()+"");
        btn_info_wifi.setText(active_profile.isWifi() ? "ON" : "OFF");
        btn_info_bluetooth.setText(active_profile.isBluetooth() ? "ON" : "OFF");
        btn_info_sound.setText(active_profile.getSound());


        int hour = new GregorianCalendar().get(Calendar.HOUR_OF_DAY);

        if(hour > 4 && hour <= 10){
            image_background.setImageResource(R.drawable.morning);
        }else if(hour > 10 && hour <= 15){
            image_background.setImageResource(R.drawable.noon);
        }else if(hour > 15 && hour <= 19){
            image_background.setImageResource(R.drawable.afternoon);
        }else{
            image_background.setImageResource(R.drawable.mode_night);
        }
    }
    public void navigation(View v) {
        switch (v.getId()) {
            case R.id.btn_new_profile:
                Toast.makeText(getApplicationContext(), "New Profile", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), NewProfileActivity.class));
                break;

            case R.id.btn_alarm:
     startActivity(new Intent(getApplicationContext(), AlarmsActivity.class));
               break;
            case R.id.btn_profiles:
                startActivity(new Intent(getApplicationContext(), ProfilesActivity.class));
                break;
                case R.id.btn_reminder:
                startActivity(new Intent(getApplicationContext(), ReminderActivity.class));
                break;
        }
    }

    public void settings(View v){
        startActivity(new Intent(getApplicationContext(),SettingsActivity.class));
    }
}
