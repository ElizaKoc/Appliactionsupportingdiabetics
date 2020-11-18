package edu.pg.DiA.holders;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import edu.pg.DiA.R;

public class ProfileListViewHolder extends RecyclerView.ViewHolder{

    public TextView userFirstName, userLastName, buttonViewOption;
    public ConstraintLayout profileListItem;

    public ProfileListViewHolder(View v) {
        super(v);
        userFirstName = v.findViewById(R.id.user_first_name_txt);
        userLastName = v.findViewById(R.id.user_last_name_txt);
        buttonViewOption = v.findViewById(R.id.profile_list_options);
        profileListItem = v.findViewById(R.id.profile_list_item);
    }
}
