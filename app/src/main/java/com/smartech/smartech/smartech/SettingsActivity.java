package com.smartech.smartech.smartech;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.smartech.smartech.smartech.SharedPreferenceStore.SharedPreferenceStore;

import org.json.JSONArray;
import org.json.JSONException;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {
Switch toggle_battery_low;
SeekBar range_battery_low;
EditText txt_phone;
ListView list_phones;
TextView txt_battery_level_show;
    SharedPreferenceStore spStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        spStore = new SharedPreferenceStore(getApplicationContext());
        toggle_battery_low = findViewById(R.id.toggle_battery_low);
        list_phones = findViewById(R.id.list_phones);
        txt_battery_level_show = findViewById(R.id.txt_battery_level_show);
        range_battery_low = findViewById(R.id.range_battery_low);
        txt_phone = findViewById(R.id.txt_phone);
        init();
        showIgnoreList();

        toggle_battery_low.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                spStore.setBatteryAlarmEnabled(b);
            }
        });
        range_battery_low.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = progress + 1;
                txt_battery_level_show.setText(progress+"%");
               spStore.setBatteryLowLevel(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    public void addContact(View v){
        String number = txt_phone.getText().toString();
        try {
            JSONArray ignored_numbers = new JSONArray(spStore.getIgnoredList());
            boolean found = false;
            for (int i=0;i<ignored_numbers.length();i++){
               if(ignored_numbers.get(i).equals(number)){
                   found = true;
               }
            }
            if(!found){
                ignored_numbers.put(number);
                spStore.setIgnoreList(ignored_numbers.toString());
            }
            txt_phone.setText("");
            showIgnoreList();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void showIgnoreList(){
        ArrayList numbers = new ArrayList();
        try {
            final JSONArray ignored_numbers = new JSONArray(spStore.getIgnoredList());
            for (int i=0;i<ignored_numbers.length();i++){
                numbers.add(ignored_numbers.get(i));
            }
            ArrayAdapter adapter = new ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, numbers);
            list_phones.setAdapter(adapter);
            list_phones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, final long id) {
                    new AlertDialog.Builder(SettingsActivity.this)
                            .setTitle("Delete")
                            .setMessage("Are you sure to delete")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {
                                    ignored_numbers.remove(position);
                                    spStore.setIgnoreList(ignored_numbers.toString());
                                    showIgnoreList();
                                }
                            })
                            .setNegativeButton(android.R.string.no, null).show();


                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void init(){
        toggle_battery_low.setChecked(spStore.isBatteryAlarmEnabled());
        range_battery_low.setProgress(spStore.getBatteryLowLevel());
        txt_battery_level_show.setText(spStore.getBatteryLowLevel()+"%");
    }
}

