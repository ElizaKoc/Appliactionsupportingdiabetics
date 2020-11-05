package edu.pg.DiA.holders;

import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import edu.pg.DiA.R;

public class WeightMeasurementsListViewHolder extends RecyclerView.ViewHolder{

    public TextView measurement, date;
    public ConstraintLayout weightMeasurementListItem;

    public WeightMeasurementsListViewHolder(View v) {
        super(v);
        measurement = v.findViewById(R.id.weight_measurement_txt);
        date = v.findViewById(R.id.weight_measurement_date_txt);
        weightMeasurementListItem = v.findViewById(R.id.weight_measurement_list_item);
    }
}
