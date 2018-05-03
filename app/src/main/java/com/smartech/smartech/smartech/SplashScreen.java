package com.smartech.smartech.smartech;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.smartech.smartech.smartech.Receivers.BatteryMoniterService;
import com.smartech.smartech.smartech.Receivers.LocationService;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash_screen);
        startService(new Intent(this, LocationService.class));
        startService(new Intent(this, BatteryMoniterService.class));
        NotificationManager notificationManager =
                (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
//        if(!Settings.System.canWrite(getApplicationContext())){
//            startActivity(new Intent( android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS));
//            finish();
//        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
//                && !notificationManager.isNotificationPolicyAccessGranted()) {
//
//            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
//            alertDialogBuilder.setMessage("Permission required. Please allow required permissions ... and run the app Again.");
//            alertDialogBuilder.setTitle("Permission required !");
//            alertDialogBuilder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    Intent intent = new Intent(
//                            android.provider.Settings
//                                    .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
//                    startActivity(intent);
//                    finish();
//
//                }
//            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    finish();
//                }
//            });
//            alertDialogBuilder.create().show();
//
//        }else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                    Intent mainIntent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
            }, 3000);
//        }



        }
}
