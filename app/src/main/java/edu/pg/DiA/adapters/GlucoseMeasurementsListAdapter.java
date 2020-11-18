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
import edu.pg.DiA.holders.GlucoseMeasurementsListViewHolder;
import edu.pg.DiA.interfaces.EventListener;
import edu.pg.DiA.models.GlucoseMeasurement;

public class GlucoseMeasurementsListAdapter extends RecyclerView.Adapter<GlucoseMeasurementsListViewHolder>{

    private Context context;
    private List<GlucoseMeasurement> changeGlucoseMeasurements = null;
    private EventListener listener;

    // Provide a suitable constructor (depends on the kind of dataset)
    public GlucoseMeasurementsListAdapter(Context context, EventListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void setMeasurements(List<GlucoseMeasurement> changeMeasurements) {
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

        holder.buttonViewOption.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context, v);
                popup.inflate(R.menu.glucose_measurement_menu_item);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        int id = item.getItemId();
                        if (id == R.id.glucose_measurement_delete) {
                            AppDatabase.getInstance(context).glucoseMeasurementDao().delete(changeGlucoseMeasurements.get(position));
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
