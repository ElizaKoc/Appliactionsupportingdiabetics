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
import edu.pg.DiA.database.AppDatabase;
import edu.pg.DiA.holders.MedicineListViewHolder;
import edu.pg.DiA.holders.MedicineViewHolder;
import edu.pg.DiA.interfaces.EventListener;
import edu.pg.DiA.models.Medicine;
import edu.pg.DiA.models.MedicineReminderWithMedicineAndReminder;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineViewHolder>{

    private Context context;
    private List<MedicineReminderWithMedicineAndReminder> changeMedicineReminders = null;
    private EventListener listener;
    public Medicine medicine;

    // Provide a suitable constructor (depends on the kind of dataset)
    public MedicineAdapter(Context context, EventListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void setMedicineReminders(List<MedicineReminderWithMedicineAndReminder> changeMedicineReminders) {
        this.changeMedicineReminders = changeMedicineReminders;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MedicineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_medicine_reminder, parent, false);

        MedicineViewHolder medicineViewHolder = new MedicineViewHolder(v);
        return medicineViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineViewHolder holder, int position) {

        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.dose.setText(changeMedicineReminders.get(position).medicine_reminder.doseUnits);

        String strDate;

        if(changeMedicineReminders.get(position).reminder.date != null) {
            strDate = new SimpleDateFormat("yyyy-MM-dd").format(changeMedicineReminders.get(position).reminder.date);
        } else {
            strDate = changeMedicineReminders.get(position).reminder.weekday;
        }
        holder.date.setText(strDate);

        String unitName = AppDatabase.getInstance(context).unitDao().getName(changeMedicineReminders.get(position).medicine.unitId);
        holder.unit.setText(unitName);

        holder.time.setText(changeMedicineReminders.get(position).reminder.time);
        holder.medicineReminderListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        if(changeMedicineReminders != null) {
            return changeMedicineReminders.size();
        }
        return 0;
    }
}
