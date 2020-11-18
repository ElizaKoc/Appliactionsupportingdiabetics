package edu.pg.DiA.holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import edu.pg.DiA.R;

public class GlucoseMeasurementReminderListViewHolder extends RecyclerView.ViewHolder{

    public TextView date, time, buttonViewOption;

    public GlucoseMeasurementReminderListViewHolder(View v) {
        super(v);
        date = v.findViewById(R.id.glucose_measurement_date_reminder_txt);
        time = v.findViewById(R.id.glucose_measurement_time_reminder_txt);
        buttonViewOption = v.findViewById(R.id.glucose_ms_reminder_options);
    }
}
