package edu.pg.DiA.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.pg.DiA.MainActivity;
import edu.pg.DiA.R;
import edu.pg.DiA.database.AppDatabase;
import edu.pg.DiA.holders.ProfileListViewHolder;
import edu.pg.DiA.models.MedicineReminderWithMedicineAndReminder;
import edu.pg.DiA.models.Reminder;
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

        SharedPreferences sharedPref = ((Activity)context).getSharedPreferences(((Activity)context).getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.userFirstName.setText(changeProfiles.get(position).firstName);
        holder.userLastName.setText(changeProfiles.get(position).lastName);

        holder.buttonViewOption.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context, v);
                popup.inflate(R.menu.profile_list_menu_item);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        int id = item.getItemId();
                        if (id == R.id.profile_edit) {
                            return true;
                        }
                        else if (id == R.id.profile_delete) {

                            AppDatabase db = AppDatabase.getInstance(context);

                            List<Reminder> reminders = db.reminderDao().getAllUserReminders(changeProfiles.get(position).uId);
                            for(Reminder reminder : reminders) {
                                reminder.deleteReminder(context);
                            }

                            if(changeProfiles.get(position).uId == User.getCurrentUser().uId) {
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putInt((context.getString(R.string.preference_file_key)), 0);
                                editor.apply();
                            }

                            db.userDao().delete(changeProfiles.get(position));
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

        holder.profileListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt(((Activity)context).getString(R.string.preference_file_key), changeProfiles.get(position).uId);
                editor.apply();

                User user = AppDatabase.getInstance(context).userDao().getUser(changeProfiles.get(position).uId);
                User.setUser(user);

                Intent intent = new Intent(context, MainActivity.class);
               // intent.putExtra("firstName", String.valueOf(changeProfiles.get(position).firstName));
                context.startActivity(intent);
                ((Activity)context).finish();
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
