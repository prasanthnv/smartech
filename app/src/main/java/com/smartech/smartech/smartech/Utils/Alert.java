package com.smartech.smartech.smartech.Utils;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


/**
 * Created by prasanth on 10/3/17.
 */

public class Alert {
    public static void showShortToast(Context context, String message){
        Toast.makeText(context,message, Toast.LENGTH_SHORT).show();
    }
    public static void showLongToast(Context context, String message){
        Toast.makeText(context,message, Toast.LENGTH_LONG).show();
    }

    public static void showModeDilaog(Context context, int icon, String text, int duration){
        Intent dialogIntent = new Intent(context, Alert.class);
        dialogIntent.putExtra("text",text);
        dialogIntent.putExtra("duration",duration);
        dialogIntent.putExtra("icon",icon);
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(dialogIntent);
    }

}
