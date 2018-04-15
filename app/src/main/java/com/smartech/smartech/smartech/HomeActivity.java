package com.smartech.smartech.smartech;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.smartech.smartech.smartech.Core.Profile;
import com.smartech.smartech.smartech.Core.ProfileChanger;
import com.smartech.smartech.smartech.Database.DatabaseHandler;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivity(new Intent(getApplicationContext(),NewProfileActivity.class));
            }
        });
    }
}
