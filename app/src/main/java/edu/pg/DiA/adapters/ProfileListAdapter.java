package edu.pg.DiA.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.pg.DiA.R;
import edu.pg.DiA.SecondActivity;
import edu.pg.DiA.holders.ProfileListViewHolder;
import edu.pg.DiA.models.User;

public class ProfileListAdapter extends RecyclerView.Adapter<ProfileListViewHolder>{

    private Context context;
    public List<User> changeProfiles = null;

    // Provide a suitable constructor (depends on the kind of dataset)
    public ProfileListAdapter(Context context) {
        this.context = context;
    }

    public void setProfiles(List<User> changeProfiles) {
        this.changeProfiles = changeProfiles;
        notifyDataSetChanged();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ProfileListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_profile, parent, false);

        ProfileListViewHolder profileListViewHolder = new ProfileListViewHolder(v);
        return profileListViewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ProfileListViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //holder.userId.setText(changeProfiles.get(position).uId);
        holder.userFirstName.setText(changeProfiles.get(position).firstName);
        holder.userLastName.setText(changeProfiles.get(position).lastName);
        holder.profileListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SecondActivity.class);
                intent.putExtra("firstName", String.valueOf(changeProfiles.get(position).firstName));
                context.startActivity(intent);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if(changeProfiles != null) {
            return changeProfiles.size();
        }
        return 0;
    }
}
