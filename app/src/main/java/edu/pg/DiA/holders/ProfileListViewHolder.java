package edu.pg.DiA.holders;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import edu.pg.DiA.R;

public class ProfileListViewHolder extends RecyclerView.ViewHolder{

    public TextView userId, userFirstName, userLastName;
    public ConstraintLayout profileListItem;

    public ProfileListViewHolder(View v) {
        super(v);
        //textView = v.findViewById(R.id.user);
        //userId = v.findViewById(R.id.user_id_txt);
        userFirstName = v.findViewById(R.id.user_first_name_txt);
        userLastName = v.findViewById(R.id.user_last_name_txt);
        profileListItem = v.findViewById(R.id.profile_list_item);
    }
}
