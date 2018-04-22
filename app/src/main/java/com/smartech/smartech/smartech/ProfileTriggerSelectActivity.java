package com.smartech.smartech.smartech;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.http.SslError;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.smartech.smartech.smartech.Core.Profile;
import com.smartech.smartech.smartech.Database.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

public class ProfileTriggerSelectActivity extends AppCompatActivity {
Profile profile;
    ScrollView view_location,view_wifi,view_sms;
double latitude,longitude;
    ProgressDialog mProgessDialog;
Button btn_location,btn_save;
EditText txt_sms_trigger;
    LocationManager mLocationManager;
    WebView webview_map;
    DatabaseHandler dbHandler;
    Spinner spinner_wifi;
    String ssid="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_trigger_select);
        profile = (Profile) getIntent().getSerializableExtra("profile");
        Toast.makeText(this, profile.getProfileInfoJson(), Toast.LENGTH_SHORT).show();
        viewInit();
        mProgessDialog = new ProgressDialog(ProfileTriggerSelectActivity.this);
        dbHandler = new DatabaseHandler(getApplicationContext());
        /*--------location-----*/
        mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        btn_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgessDialog.setMessage("Fetching Location...");
                mProgessDialog.show();
                if (ActivityCompat.checkSelfPermission(ProfileTriggerSelectActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ProfileTriggerSelectActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                        profile.setTrigger(latitude+","+longitude);
                        showMap();
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


        switch (profile.getType()){
            case "location":
                view_location.setVisibility(View.VISIBLE);
                break;
            case "wifi":
                view_wifi.setVisibility(View.VISIBLE);
                wifiFetch();
                break;
            case "sms":
            view_sms.setVisibility(View.VISIBLE);
            break;
        }

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                latitude = place.getLatLng().latitude;

                longitude = place.getLatLng().longitude;
            profile.setTrigger(latitude+","+longitude);
                showMap();

            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("Error", "An error occurred: " + status);
            }
        });


        txt_sms_trigger.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                profile.setTrigger(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        /*--------/wifi------*/

        btn_save.setOnClickListener(saveTriggerListener);
    }
public void wifiFetch(){
         /*--------wifi------*/
    WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    List wifiList = wifiManager.getConfiguredNetworks();
    final ArrayList wifis = new ArrayList<String>();
    List<WifiConfiguration> configs = wifiManager.getConfiguredNetworks();
    for (WifiConfiguration config : configs) {
        String SSID = config.SSID;
        if (SSID.startsWith("\"") && SSID.endsWith("\"")) {
            // to remove aditional quotes in SSID
            SSID = SSID.substring(1, SSID.length() - 1);
        }
        wifis.add(SSID);
    }
    Log.e("WIFI", wifis.toString());
    // setting wifi names into android spinner
    ArrayAdapter wifiArrayAdapter = new ArrayAdapter<String>(ProfileTriggerSelectActivity.this,
            R.layout.spinner_item, wifis);
    spinner_wifi.setAdapter(wifiArrayAdapter);
    spinner_wifi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            ssid =  wifis.get(position).toString();
            profile.setTrigger(ssid);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    });
}

    public void viewInit(){
        view_location = findViewById(R.id.view_location);
        view_wifi = findViewById(R.id.view_wifi);
        view_sms = findViewById(R.id.view_sms);
        btn_location = findViewById(R.id.btn_location);
        webview_map = findViewById(R.id.webview_map);
        btn_save = findViewById(R.id.btn_save);
        spinner_wifi = findViewById(R.id.spinner_wifi);
        txt_sms_trigger = findViewById(R.id.txt_sms_trigger);
    }


    public void showMap(){

        webview_map.setVisibility(View.VISIBLE);
        webview_map.getSettings().setJavaScriptEnabled(true);
        webview_map.setWebViewClient(new WebViewClient());
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenWidth = displaymetrics.widthPixels;
        int screenHeight = displaymetrics.heightPixels;
        webview_map.loadUrl("https://maps.googleapis.com/maps/api/staticmap?zoom=14&size="+(screenWidth + 50)+"x200&maptype=roadmap&markers=color:red%7Clabel:C%7C"+latitude+","+longitude+"&center="+latitude+","+longitude+"");

    }

   View.OnClickListener saveTriggerListener = new View.OnClickListener() {
       @Override
       public void onClick(View v) {
        dbHandler.addProfile(profile);
           Toast.makeText(ProfileTriggerSelectActivity.this, "Profile saved and waiting for "+profile.getType()+" to acivate.", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
        finish();
       }
   };
}
