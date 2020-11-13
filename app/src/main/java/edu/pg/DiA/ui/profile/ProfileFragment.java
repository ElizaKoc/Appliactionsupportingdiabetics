package edu.pg.DiA.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.Calendar;

import edu.pg.DiA.R;
import edu.pg.DiA.interfaces.DrawerLocker;
import edu.pg.DiA.models.User;

public class ProfileFragment extends Fragment{

    private ProfileViewModel profileViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        updateData(root);
        return root;
    }

    private void updateData(View root) {

        ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();
        ((DrawerLocker)getActivity()).setDrawerLocked(false);

        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);

         TextView name = root.findViewById(R.id.profile_name);
         TextView surname = root.findViewById(R.id.profile_surname);
         TextView sex = root.findViewById(R.id.profile_sex);
         TextView age = root.findViewById(R.id.profile_age);
         TextView height = root.findViewById(R.id.profile_height);
         TextView weight = root.findViewById(R.id.profile_weight);

        profileViewModel.user.observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(@Nullable User u) {

                int current_year = Calendar.getInstance().get(Calendar.YEAR);
                int birth_year = u.birth_year;
                int age_int = current_year - birth_year;

                name.setText(u.firstName);
                surname.setText(u.lastName);
                sex.setText(u.sex);
                age.setText(String.valueOf(age_int));
                height.setText(String.valueOf(u.height_cm));
            }
        });

        profileViewModel.weight.observe(getViewLifecycleOwner(), new Observer<Float>() {
            @Override
            public void onChanged(@Nullable Float w) {
                if(w != null) {
                    weight.setText(String.valueOf(w));

                    root.findViewById(R.id.profile_weight_layout).setBackgroundResource(R.drawable.customborder);
                    root.findViewById(R.id.profile_weight_label).setVisibility(View.VISIBLE);
                    root.findViewById(R.id.profile_weight).setVisibility(View.VISIBLE);
                    root.findViewById(R.id.profile_weight_unit).setVisibility(View.VISIBLE);
                }else {
                    root.findViewById(R.id.profile_weight_layout).setBackgroundResource(0);
                    root.findViewById(R.id.profile_weight_label).setVisibility(View.GONE);
                    root.findViewById(R.id.profile_weight).setVisibility(View.GONE);
                    root.findViewById(R.id.profile_weight_unit).setVisibility(View.GONE);
                }
            }
        });

        profileViewModel.getTitle().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer i) {
                ab.setTitle(i);
            }
        });
    }
}
