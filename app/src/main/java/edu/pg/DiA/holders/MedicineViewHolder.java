package edu.pg.DiA.holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import edu.pg.DiA.R;

public class MedicineViewHolder extends RecyclerView.ViewHolder{

    public TextView dose, date, time;
    public ConstraintLayout medicineReminderListItem;

    public MedicineViewHolder(@NonNull View v) {

        super(v);
        dose = v.findViewById(R.id.medicine_reminder_dose_txt);
        date = v.findViewById(R.id.medicine_date_reminder_txt);
        time = v.findViewById(R.id.medicine_time_reminder_txt);
        medicineReminderListItem = v.findViewById(R.id.medicine_reminder_list_item);
    }
}
