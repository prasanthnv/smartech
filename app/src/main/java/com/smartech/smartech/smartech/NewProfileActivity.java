package com.smartech.smartech.smartech;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.smartech.smartech.smartech.Core.Profile;

public class NewProfileActivity extends AppCompatActivity {
RadioGroup rgroup_type,rgroup_sound;
EditText txt_name;
SeekBar range_volume,range_brightness;
Button btn_continue;
ToggleButton toggle_bluetooth,toggle_wifi;
LinearLayout view_custom_sound;
String name = "",sound = "",type = "";
boolean bluetooth = false, wifi=true;
int brightness = 0,volume = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("New Profile");
        setContentView(R.layout.activity_new_profile);

        // view init
        txt_name = findViewById(R.id.txt_name);
        rgroup_type = findViewById(R.id.rgroup_type);
        range_volume = findViewById(R.id.range_volume);
        range_brightness = findViewById(R.id.range_brightness);
        range_brightness = findViewById(R.id.range_brightness);
        toggle_bluetooth = findViewById(R.id.toggle_bluetooth);
        toggle_wifi = findViewById(R.id.toggle_wifi);
        btn_continue = findViewById(R.id.btn_continue);
        view_custom_sound = findViewById(R.id.view_custom_sound);
        rgroup_sound = findViewById(R.id.rgroup_sound);

        // radio group actions
        rgroup_type.setOnCheckedChangeListener(typeSelectedListener);
        rgroup_sound.setOnCheckedChangeListener(soundSelectListener);

        // seekbar actions
       range_volume.setOnSeekBarChangeListener(volumeChangeListener);
       range_brightness.setOnSeekBarChangeListener(brightnessChangeListener);

        // bluetooth,Wifi toggle
        toggle_bluetooth.setOnCheckedChangeListener(bluetoothToggleListener);
        toggle_wifi.setOnCheckedChangeListener(wifiToggleListener);

        // continue button action
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // validating and saving Profile
                name = txt_name.getText().toString();
                Log.e("Name",name);
                Log.e("Type",type);
                Log.e("Sound",sound);
                Log.e("Brightness",brightness+"");
                Log.e("Volume",volume+"");
                Log.e("bluetooth",bluetooth+"");
                if(!name.equals("") && !type.equals("") && !sound.equals("")){
                    Profile profile = new Profile(name,type);
                    profile.setBluetooth(bluetooth);
                    profile.setSound(sound);
                    profile.setBrigtness(brightness);
                    profile.setWifi(wifi);
                    Log.w("profile",profile.getProfileInfoJson());
                    Intent triggerSelectIntent = new Intent(getApplicationContext(),ProfileTriggerSelectActivity.class);
                    triggerSelectIntent.putExtra("profile",profile);
                    startActivity(triggerSelectIntent);

                }else{
                    Toast.makeText(getApplicationContext(), "Fill All Fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    RadioGroup.OnCheckedChangeListener typeSelectedListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            RadioButton selectedRadioButton =findViewById(checkedId);
            type = selectedRadioButton.getTag().toString();
        }
    };
    RadioGroup.OnCheckedChangeListener soundSelectListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            RadioButton selectedRadioButton =findViewById(checkedId);
            sound = selectedRadioButton.getTag().toString();
           if(sound.equals("custom")){
               view_custom_sound.setVisibility(View.VISIBLE);
           }else{
               view_custom_sound.setVisibility(View.GONE);
           }

            Toast.makeText(getApplicationContext(),"selected " + selectedRadioButton.getTag(),Toast.LENGTH_LONG).show();
        }
    };

    SeekBar.OnSeekBarChangeListener volumeChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            volume = progress;
            sound = ""+progress;
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
    SeekBar.OnSeekBarChangeListener brightnessChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            brightness = progress;
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };



    CompoundButton.OnCheckedChangeListener bluetoothToggleListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            bluetooth = isChecked;
        }
    };
    CompoundButton.OnCheckedChangeListener wifiToggleListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            wifi = isChecked;
        }
    };


}
