package com.smartech.smartech.smartech.Adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.smartech.smartech.smartech.R;

import java.util.ArrayList;

/**
 * Created by prasanth on 19/3/17.
 */

public class ReminderListAdapter extends ArrayAdapter {
    ArrayList titles;
    ArrayList notes;
    Activity activity;

    public ReminderListAdapter(Activity activity, ArrayList titles,ArrayList notes) {
        super(activity, R.layout.list_remider_row, titles);
        this.activity = activity;
this.titles = titles;
this.notes = notes;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View rowView = layoutInflater.inflate(R.layout.list_remider_row, null);

        TextView txt_title= (TextView) rowView.findViewById(R.id.txt_title);
        TextView txt_note= (TextView) rowView.findViewById(R.id.txt_note);

        String title = titles.get(position).toString();
        String note = notes.get(position).toString();

        txt_title.setText(title);
        txt_note.setText(note);

        return rowView;

    }


}
