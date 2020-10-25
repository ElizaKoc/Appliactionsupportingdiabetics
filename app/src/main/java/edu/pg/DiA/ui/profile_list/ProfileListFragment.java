package edu.pg.DiA.ui.profile_list;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import edu.pg.DiA.R;
import edu.pg.DiA.adapters.ProfileListAdapter;
import edu.pg.DiA.models.User;
import edu.pg.DiA.ui.add_new_profile.AddNewProfileFragment;

public class ProfileListFragment extends Fragment {

    private ProfileListAdapter profileListAdapter;
    public List<User> profiles;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile_list, container, false);
        initView(root);
        updateData();
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {
        ProfileListViewModel profileListViewModel = ViewModelProviders.of(this).get(ProfileListViewModel.class);
        profileListViewModel.profiles.observe(this, changeProfiles -> {
                profiles = changeProfiles;
                profileListAdapter.setProfiles(profiles);}
        );
    }

    private void updateData() {

        ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();

        ProfileListViewModel profileListViewModel = ViewModelProviders.of(this).get(ProfileListViewModel.class);
        profileListViewModel.profiles.observe(getViewLifecycleOwner(), new Observer<List<User>>() {

            @Override
            public void onChanged(@Nullable List<User> users) {
                profiles = users;
                profileListAdapter.setProfiles(profiles);
            }
        });

        profileListViewModel.getTitle().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer i) {
                ab.setDisplayHomeAsUpEnabled(false);
                ab.setTitle(i);
            }
        });
    }

    private void initView(View root) {
        Context context = getActivity().getApplicationContext();
        RecyclerView recyclerView = root.findViewById(R.id.profile_list);
        profileListAdapter = new ProfileListAdapter(requireContext());
        recyclerView.setAdapter(profileListAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        FloatingActionButton add_profile_button = root.findViewById(R.id.add_profile_button);
        add_profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                // Create new fragment and transaction
                Fragment addNewProfile = new AddNewProfileFragment();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                transaction.replace(R.id.profile_list_fragment, addNewProfile);
                transaction.setReorderingAllowed(true).addToBackStack(null);

                // Commit the transaction
                transaction.commit();

                //Intent intent = new Intent(getActivity(), AddNewProfileActivity.class);
                //startActivity(intent);
                //getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new AddProfileFragment()).commit();
            }
        });
    }
}
