package edu.pg.DiA.ui.glucose_measurements;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import edu.pg.DiA.R;
import edu.pg.DiA.adapters.GlucoseMeasurementsListAdapter;
import edu.pg.DiA.interfaces.EventListener;
import edu.pg.DiA.models.GlucoseMeasurement;
import edu.pg.DiA.ui.reminder.AddNewReminderFragment;
import edu.pg.DiA.widgets.CustomRecyclerView;

public class GlucoseMeasurementsFragment extends Fragment implements EventListener {

    private GlucoseMeasurementsViewModel glucoseMeasurementsViewModel;
    private GlucoseMeasurementsListAdapter glucoseMeasurementsListAdapter;
    private FragmentManager fragmentManager;
    public List<GlucoseMeasurement> measurements;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_glucose_measurements_list, container, false);
        initView(root);
        updateData();
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.glucose_measurement_menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        if (item.getItemId() == R.id.glucose_measurement_reminders_list) {
            moveToGlucoseMeasurementReminderListFragment();
            return true;
        }
        else if (item.getItemId() == R.id.glucose_ms_add_reminder) {
            moveToAddReminderFragment();
            return true;
        }
        return false;
    }

    private void initData() {

        setHasOptionsMenu(true);

        glucoseMeasurementsViewModel = ViewModelProviders.of(this).get(GlucoseMeasurementsViewModel.class);
        glucoseMeasurementsViewModel.measurements.observe(this, changeMeasurements -> {
            measurements = changeMeasurements;
            glucoseMeasurementsListAdapter.setMeasurements(measurements);
        });
    }

    private void updateData() {

        ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();

        glucoseMeasurementsViewModel = ViewModelProviders.of(this).get(GlucoseMeasurementsViewModel.class);

        glucoseMeasurementsViewModel.measurements.observe(getViewLifecycleOwner(), new Observer<List<GlucoseMeasurement>>() {

            @Override
            public void onChanged(@Nullable List<GlucoseMeasurement> glucoseMeasurementsList) {
                measurements = glucoseMeasurementsList;
                glucoseMeasurementsListAdapter.setMeasurements(measurements);
            }
        });

        glucoseMeasurementsViewModel.getTitle().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer i) {
                //ab.setDisplayHomeAsUpEnabled(false);
                ab.setTitle(i);
            }
        });
    }

    private void initView(View root) {

        Context context = getActivity().getApplicationContext();
        fragmentManager = getActivity().getSupportFragmentManager();

        CustomRecyclerView recyclerView = root.findViewById(R.id.glucose_measurement_list);
        View mEmptyView = root.findViewById(R.id.empty_drops_glucose_measurements);
        recyclerView.showIfEmpty(mEmptyView);

        glucoseMeasurementsListAdapter = new GlucoseMeasurementsListAdapter(requireContext(), this);
        recyclerView.setAdapter(glucoseMeasurementsListAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        final TextView noData = root.findViewById(R.id.glucose_measurement_no_data);
        glucoseMeasurementsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer i) {
                if(glucoseMeasurementsListAdapter.getItemCount() == 0) {
                    noData.setText(i);
                    noData.setVisibility(View.VISIBLE);
                } else {
                    noData.setVisibility(View.GONE);
                }
            }
        });

        FloatingActionButton add_glucose_measure_button = root.findViewById(R.id.add_glucose_measurement_button);

        add_glucose_measure_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create new fragment and transaction
                Fragment addNewGlucoseMeasurementFragment = new AddNewGlucoseMeasurementFragment();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                transaction.replace(R.id.change_list_fragment, addNewGlucoseMeasurementFragment);
                transaction.setReorderingAllowed(true).addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });
    }

    @Override
    public void onEvent(@Nullable Fragment fragment) {

    }

    private void moveToAddReminderFragment() {

        // Create new fragment and transaction
        Fragment addNewReminderFragment = new AddNewReminderFragment();

        Bundle args = new Bundle();
        args.putString("pomiar_glukozy", "pomiar glukozy");
        addNewReminderFragment.setArguments(args);

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.change_list_fragment, addNewReminderFragment);
        transaction.setReorderingAllowed(true).addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    private void moveToGlucoseMeasurementReminderListFragment() {

        // Create new fragment and transaction
        Fragment glucoseMeasurementReminderListFragment = new GlucoseMeasurementReminderListFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.change_list_fragment, glucoseMeasurementReminderListFragment);
        transaction.setReorderingAllowed(true).addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }
}
