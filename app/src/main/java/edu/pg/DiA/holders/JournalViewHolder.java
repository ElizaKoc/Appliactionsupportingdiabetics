package edu.pg.DiA.holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import edu.pg.DiA.R;

public class JournalViewHolder extends RecyclerView.ViewHolder{

    public TextView noteTitle, date, buttonViewOption;
    public ConstraintLayout noteListItem;

    public JournalViewHolder(@NonNull View v) {
        super(v);
        noteTitle = v.findViewById(R.id.note_title_txt);
        date = v.findViewById(R.id.note_date_txt);
        buttonViewOption = v.findViewById(R.id.note_list_options);
        noteListItem = v.findViewById(R.id.note_list_item);
    }
}
