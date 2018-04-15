package com.smartech.smartech.smartech;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.smartech.smartech.smartech.Core.Profile;

public class ProfileTriggerSelectActivity extends AppCompatActivity {
Profile profile;
LinearLayout view_location,view_wifi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_trigger_select);
        profile = (Profile) getIntent().getSerializableExtra("profile");
        Toast.makeText(this, profile.getProfileInfoJson(), Toast.LENGTH_SHORT).show();

        viewInit();

        switch (profile.getType()){
            case "location":
                view_location.setVisibility(View.VISIBLE);
                break;
            case "wifi":
                view_wifi.setVisibility(View.VISIBLE);
                break;
        }

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setOnPlaceSele
        ctedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i("Auto", "Place: " + place.getName());
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("Error", "An error occurred: " + status);
            }
        });

    }


    public void viewInit(){
        view_location = findViewById(R.id.view_location);
        view_wifi = findViewById(R.id.view_wifi);

    }
}
