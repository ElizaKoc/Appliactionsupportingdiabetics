package edu.pg.DiA.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

import edu.pg.DiA.R;
import edu.pg.DiA.holders.GlucoseMeasurementsListViewHolder;
import edu.pg.DiA.holders.WeightMeasurementsListViewHolder;
import edu.pg.DiA.interfaces.EventListener;
import edu.pg.DiA.models.Body_weight_measurement;
import edu.pg.DiA.models.Glucose_measurement;

public class WeightMeasurementsListAdapter extends RecyclerView.Adapter<WeightMeasurementsListViewHolder>{

    private Context context;
    private List<Body_weight_measurement> changeWeightMeasurements = null;
    private EventListener listener;

    // Provide a suitable constructor (depends on the kind of dataset)
    public WeightMeasurementsListAdapter(Context context, EventListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void setMeasurements(List<Body_weight_measurement> changeMeasurements) {
        this.changeWeightMeasurements = changeMeasurements;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WeightMeasurementsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_weight_measurement, parent, false);

        WeightMeasurementsListViewHolder weightMeasurementsListViewHolder = new WeightMeasurementsListViewHolder(v);
        return weightMeasurementsListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WeightMeasurementsListViewHolder holder, int position) {

        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.measurement.setText(String.valueOf(changeWeightMeasurements.get(position).weightKg));

        String strDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(changeWeightMeasurements.get(position).date);
        holder.date.setText(strDate);

        holder.weightMeasurementListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //listener.onEvent();
            }
        });

    }

    @Override
    public int getItemCount() {
        if(changeWeightMeasurements != null) {
            return changeWeightMeasurements.size();
        }
        return 0;
    }
}
