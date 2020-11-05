package edu.pg.DiA.holders;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import edu.pg.DiA.R;

public class MedicineListViewHolder extends RecyclerView.ViewHolder{

    public TextView medicineName, unit, buttonViewOption;
    public ConstraintLayout medicineListItem;

    public MedicineListViewHolder(View v) {
        super(v);
        medicineName = v.findViewById(R.id.medicine_name_txt);
        unit = v.findViewById(R.id.medicine_unit_txt);
        buttonViewOption = v.findViewById(R.id.medicine_list_options);
        medicineListItem = v.findViewById(R.id.medicine_list_item);
    }
}
