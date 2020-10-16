package edu.pg.DiA.holders;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import edu.pg.DiA.R;

public class ProfileListViewHolder extends RecyclerView.ViewHolder{

    public TextView textView;
    public ProfileListViewHolder(View v) {
        super(v);
        textView = v.findViewById(R.id.user);
    }
}
