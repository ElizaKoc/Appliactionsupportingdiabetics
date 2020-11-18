package edu.pg.DiA.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

import edu.pg.DiA.R;
import edu.pg.DiA.database.AppDatabase;
import edu.pg.DiA.holders.WeightMeasurementsListViewHolder;
import edu.pg.DiA.interfaces.EventListener;
import edu.pg.DiA.models.BodyWeightMeasurement;

public class WeightMeasurementsListAdapter extends RecyclerView.Adapter<WeightMeasurementsListViewHolder>{

    private Context context;
    private List<BodyWeightMeasurement> changeWeightMeasurements = null;
    private EventListener listener;

    // Provide a suitable constructor (depends on the kind of dataset)
    public WeightMeasurementsListAdapter(Context context, EventListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void setMeasurements(List<BodyWeightMeasurement> changeMeasurements) {
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

        holder.buttonViewOption.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context, v);
                popup.inflate(R.menu.weight_measurement_menu_item);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        int id = item.getItemId();
                        if (id == R.id.weight_measurement_delete) {
                            AppDatabase.getInstance(context).bodyWeightMeasurementDao().delete(changeWeightMeasurements.get(position));
                            return true;
                        }
                        else {
                            return false;
                        }
                    }
                });
                popup.show();
            }
        });

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
