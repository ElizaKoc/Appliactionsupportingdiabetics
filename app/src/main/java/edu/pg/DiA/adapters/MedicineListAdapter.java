package edu.pg.DiA.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.pg.DiA.R;
import edu.pg.DiA.database.AppDatabase;
import edu.pg.DiA.holders.MedicineListViewHolder;
import edu.pg.DiA.interfaces.EventListener;
import edu.pg.DiA.models.Medicine;
import edu.pg.DiA.models.MedicineReminderWithMedicineAndReminder;
import edu.pg.DiA.ui.medicines.MedicineFragment;
import edu.pg.DiA.ui.reminder.AddNewReminderFragment;

public class MedicineListAdapter extends RecyclerView.Adapter<MedicineListViewHolder>{

    private Context context;
    private List<Medicine> changeMedicines = null;
    private EventListener listener;
    private Medicine medicine;

    // Provide a suitable constructor (depends on the kind of dataset)
    public MedicineListAdapter(Context context, EventListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void setMedicines(List<Medicine> changeMedicines) {
        this.changeMedicines = changeMedicines;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MedicineListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_medicine, parent, false);

        MedicineListViewHolder medicineListViewHolder = new MedicineListViewHolder(v);
        return medicineListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineListViewHolder holder, int position) {

        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.medicineName.setText(changeMedicines.get(position).name);
        String unitName = AppDatabase.getInstance(context).unitDao().getName(changeMedicines.get(position).unitId);
        holder.unit.setText(unitName);

        holder.buttonViewOption.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context, v);
                popup.inflate(R.menu.medicine_menu_item);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        medicine = changeMedicines.get(position);

                        int id = item.getItemId();
                        if (id == R.id.medicine_add_reminder) {
                            listener.onEvent(new AddNewReminderFragment());
                            return true;
                        }
                        else if(id == R.id.medicine_edit) {

                            return true;
                        }
                        else if(id == R.id.medicine_delete) {
                            List<MedicineReminderWithMedicineAndReminder> medicineReminders = AppDatabase.getInstance(context).medicineReminderDao().getRemindersList(changeMedicines.get(position).mId);
                            for(MedicineReminderWithMedicineAndReminder reminder : medicineReminders){
                                reminder.reminder.deleteReminder(context);
                            }
                            AppDatabase.getInstance(context).medicineDao().delete(changeMedicines.get(position));
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

        holder.medicineListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                medicine = changeMedicines.get(position);
                listener.onEvent(new MedicineFragment());
            }
        });

    }

    @Override
    public int getItemCount() {
        if(changeMedicines != null) {
            return changeMedicines.size();
        }
        return 0;
    }

    public Medicine getMedicine() {
        return medicine;
    }
}
