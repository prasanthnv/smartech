package com.smartech.smartech.smartech;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.smartech.smartech.smartech.Database.DatabaseHandler;

public class NewReminderActivity extends AppCompatActivity {
    LocationManager mLocationManager;
    DatabaseHandler dbHandler;
    double latitude,longitude;
    ProgressDialog mProgessDialog;
    Button btn_location,btn_save;
    EditText txt_note,txt_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reminder);
        dbHandler = new DatabaseHandler(getApplicationContext());
        btn_location = findViewById(R.id.btn_location);
        btn_save = findViewById(R.id.btn_save);
        mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        mProgessDialog = new ProgressDialog(NewReminderActivity.this);
txt_note = findViewById(R.id.txt_note);
        txt_title = findViewById(R.id.txt_title);
        btn_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgessDialog.setMessage("Fetching Location...");
                mProgessDialog.show();
                if (ActivityCompat.checkSelfPermission(NewReminderActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(NewReminderActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 5000, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        mProgessDialog.hide();
                        latitude = location.getLongitude();
                        longitude = location.getLongitude();
                        Log.e("Profile => ", latitude + "," + longitude);

                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                });
            }
        });

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                latitude = place.getLatLng().latitude;
                longitude = place.getLatLng().longitude;
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("Error", "An error occurred: " + status);
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String note = txt_note.getText().toString();
                String title = txt_title.getText().toString();
                dbHandler.addReminder(title,note,latitude+","+longitude);
                Toast.makeText(NewReminderActivity.this, "Reminder Saved", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }



}
