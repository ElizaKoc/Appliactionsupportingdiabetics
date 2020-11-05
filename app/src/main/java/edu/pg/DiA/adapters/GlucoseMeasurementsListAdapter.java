package edu.pg.DiA.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import edu.pg.DiA.R;
import edu.pg.DiA.database.AppDatabase;
import edu.pg.DiA.holders.GlucoseMeasurementsListViewHolder;
import edu.pg.DiA.holders.MedicineListViewHolder;
import edu.pg.DiA.interfaces.EventListener;
import edu.pg.DiA.models.Glucose_measurement;
import edu.pg.DiA.models.Medicine;

public class GlucoseMeasurementsListAdapter extends RecyclerView.Adapter<GlucoseMeasurementsListViewHolder>{

    private Context context;
    private List<Glucose_measurement> changeGlucoseMeasurements = null;
    private EventListener listener;

    // Provide a suitable constructor (depends on the kind of dataset)
    public GlucoseMeasurementsListAdapter(Context context, EventListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void setMeasurements(List<Glucose_measurement> changeMeasurements) {
        this.changeGlucoseMeasurements = changeMeasurements;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GlucoseMeasurementsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_glucose_measurement, parent, false);

        GlucoseMeasurementsListViewHolder glucoseMeasurementsListViewHolder = new GlucoseMeasurementsListViewHolder(v);
        return glucoseMeasurementsListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GlucoseMeasurementsListViewHolder holder, int position) {

        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.measurement.setText(String.valueOf(changeGlucoseMeasurements.get(position).dose));

        String strDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(changeGlucoseMeasurements.get(position).date);
        holder.date.setText(strDate);

        holder.glucoseMeasurementListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //listener.onEvent();
            }
        });
    }

    @Override
    public int getItemCount() {
        if(changeGlucoseMeasurements != null) {
            return changeGlucoseMeasurements.size();
        }
        return 0;
    }
}
