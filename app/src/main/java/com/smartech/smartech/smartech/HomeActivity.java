package com.smartech.smartech.smartech;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.smartech.smartech.smartech.Core.Profile;
import com.smartech.smartech.smartech.SharedPreferenceStore.SharedPreferenceStore;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity {
SharedPreferenceStore spStore;
TextView txt_profile_name,txt_profile_sound;
    Profile active_profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_home);
spStore = new SharedPreferenceStore(getApplicationContext());
txt_profile_name = findViewById(R.id.txt_profile_name);
txt_profile_sound = findViewById(R.id.txt_profile_sound);
//        Button btn = findViewById(R.id.btn);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//              startActivity(new Intent(getApplicationContext(),NewProfileActivity.class));
//            }
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        showInfo();
    }

    public void showInfo(){
        if(!spStore.getProfile().equals("")){
            try {
                active_profile = new Profile(new JSONObject(spStore.getProfile()));
                txt_profile_name.setText(active_profile.getName());
                txt_profile_sound.setText(active_profile.getSound());
            } catch (JSONException e) {

            }
        }else{
            txt_profile_name.setText("Hi, Hello ");
            txt_profile_sound.setText("");
        }
    }
    public void navigation(View v) {
        switch (v.getId()) {
            case R.id.btn_new_profile:
                Toast.makeText(getApplicationContext(), "New Profile", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), NewProfileActivity.class));
                break;

            case R.id.btn_contacts:
//                startActivity(new Intent(getApplicationContext(), ContactsListActivity.class));
//                break;
            case R.id.btn_profiles:
                startActivity(new Intent(getApplicationContext(), ProfilesActivity.class));
                break;
        }
    }
}
