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
import edu.pg.DiA.holders.GlucoseMeasurementReminderListViewHolder;
import edu.pg.DiA.models.GlucoseMeasurement;
import edu.pg.DiA.models.Reminder;

public class GlucoseMeasurementReminderListAdapter extends RecyclerView.Adapter<GlucoseMeasurementReminderListViewHolder>{

    private Context context;
    private List<Reminder> changeMeasurementReminders = null;

    // Provide a suitable constructor (depends on the kind of dataset)
    public GlucoseMeasurementReminderListAdapter(Context context) {
        this.context = context;
    }

    public void setMeasurementReminders(List<Reminder> changeMeasurementReminders) {
        this.changeMeasurementReminders = changeMeasurementReminders;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GlucoseMeasurementReminderListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_glucose_measurement_reminder, parent, false);

        GlucoseMeasurementReminderListViewHolder glucoseMeasurementReminderListViewHolder = new GlucoseMeasurementReminderListViewHolder(v);
        return glucoseMeasurementReminderListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GlucoseMeasurementReminderListViewHolder holder, int position) {

        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        String strDate;

        if(changeMeasurementReminders.get(position).date != null) {
            strDate = new SimpleDateFormat("yyyy-MM-dd").format(changeMeasurementReminders.get(position).date);
        } else {
            strDate = changeMeasurementReminders.get(position).weekday;
        }
        holder.date.setText(strDate);
        holder.time.setText(changeMeasurementReminders.get(position).time);
    }

    @Override
    public int getItemCount() {
        if(changeMeasurementReminders != null) {
            return changeMeasurementReminders.size();
        }
        return 0;
    }
}
