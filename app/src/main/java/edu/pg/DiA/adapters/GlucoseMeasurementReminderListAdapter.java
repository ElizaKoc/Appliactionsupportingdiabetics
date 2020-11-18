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
import edu.pg.DiA.interfaces.EventListener;
import edu.pg.DiA.models.GlucoseMeasurement;
import edu.pg.DiA.models.MedicineReminderWithMedicineAndReminder;
import edu.pg.DiA.models.Reminder;
import edu.pg.DiA.ui.reminder.EditReminderFragment;

public class GlucoseMeasurementReminderListAdapter extends RecyclerView.Adapter<GlucoseMeasurementReminderListViewHolder>{

    private Context context;
    private List<Reminder> changeMeasurementReminders = null;
    private Reminder reminder;
    private EventListener listener;

    // Provide a suitable constructor (depends on the kind of dataset)
    public GlucoseMeasurementReminderListAdapter(Context context, EventListener listener) {
        this.context = context;
        this.listener = listener;
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

        holder.buttonViewOption.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context, v);
                popup.inflate(R.menu.glucose_measurement_reminder_menu_item);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        int id = item.getItemId();
                        if(id == R.id.glucose_ms_reminder_edit) {
                            reminder = changeMeasurementReminders.get(position);
                            listener.onEvent(new EditReminderFragment());
                            return true;
                        }
                        else if(id == R.id.glucose_ms_reminder_delete) {
                            changeMeasurementReminders.get(position).deleteReminder(context);
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
    }

    @Override
    public int getItemCount() {
        if(changeMeasurementReminders != null) {
            return changeMeasurementReminders.size();
        }
        return 0;
    }

    public Reminder getReminder() {
        return reminder;
    }
}
