package edu.pg.DiA.holders;

import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import edu.pg.DiA.R;

public class GlucoseMeasurementsListViewHolder extends RecyclerView.ViewHolder{

    public TextView measurement, date, buttonViewOption;
    public ConstraintLayout glucoseMeasurementListItem;

    public GlucoseMeasurementsListViewHolder(View v) {
        super(v);
        measurement = v.findViewById(R.id.glucose_measurement_txt);
        date = v.findViewById(R.id.glucose_measurement_date_txt);
        buttonViewOption = v.findViewById(R.id.glucose_ms_list_options);
        glucoseMeasurementListItem = v.findViewById(R.id.glucose_measurement_list_item);
    }
}
