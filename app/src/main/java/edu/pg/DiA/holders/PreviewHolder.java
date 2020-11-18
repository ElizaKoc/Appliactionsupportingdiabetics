package edu.pg.DiA.holders;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import edu.pg.DiA.R;

public class PreviewHolder extends RecyclerView.ViewHolder{

    public TextView alarmType, time, isCyclical, buttonViewOption;

    public PreviewHolder(View v) {
        super(v);
        alarmType = v.findViewById(R.id.schedule_reminder_label);
        isCyclical = v.findViewById(R.id.schedule_is_cyclical_reminder_txt);
        time = v.findViewById(R.id.schedule_time_reminder_txt);
        buttonViewOption = v.findViewById(R.id.schedule_reminder_options);
    }
}
