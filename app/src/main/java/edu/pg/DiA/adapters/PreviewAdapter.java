package edu.pg.DiA.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.pg.DiA.R;
import edu.pg.DiA.database.AppDatabase;
import edu.pg.DiA.holders.PreviewHolder;
import edu.pg.DiA.models.Reminder;

public class PreviewAdapter extends RecyclerView.Adapter<PreviewHolder>{

    private Context context;
    private List<Reminder> changeReminders = null;
    private String selectedDate;

    // Provide a suitable constructor (depends on the kind of dataset)
    public PreviewAdapter(Context context) {
        this.context = context;
    }

    public void setReminders(List<Reminder> changeReminders, String selectedDate) {
        this.changeReminders = changeReminders;
        this.selectedDate = selectedDate;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PreviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_schedule_reminder, parent, false);

        PreviewHolder previewHolder = new PreviewHolder(v);
        return previewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PreviewHolder holder, int position) {


        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        String type = changeReminders.get(position).alarm;
        if(type.equals("lekarstwo")) {
            int medicineId = AppDatabase.getInstance(context).medicineReminderDao().getMedicineId(changeReminders.get(position).rId);
            String medicineName = "lek: " + AppDatabase.getInstance(context).medicineDao().getName(medicineId);
            holder.alarmType.setText(medicineName);
        }
        else {
            holder.alarmType.setText(changeReminders.get(position).alarm);
        }

        String strDate;

        if(changeReminders.get(position).date != null) {
            strDate = "";
        } else {
            strDate = "cyklicznie";
        }
        holder.isCyclical.setText(strDate);
        holder.time.setText(changeReminders.get(position).time);
    }

    @Override
    public int getItemCount() {
        if(changeReminders != null) {
            return changeReminders.size();
        }
        return 0;
    }
}
