package edu.pg.DiA.ui.glucose_measurements;

import android.content.Context;
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
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import edu.pg.DiA.R;
import edu.pg.DiA.adapters.GlucoseMeasurementReminderListAdapter;
import edu.pg.DiA.interfaces.DrawerLocker;
import edu.pg.DiA.models.Reminder;
import edu.pg.DiA.widgets.CustomRecyclerView;

public class GlucoseMeasurementReminderListFragment extends Fragment {

    private GlucoseMeasurementReminderListViewModel glucoseMeasurementReminderListViewModel;
    private GlucoseMeasurementReminderListAdapter glucoseMeasurementReminderListAdapter;
    private FragmentManager fragmentManager;
    public List<Reminder> measurementReminders;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_reminder_list_glucose_measurement, container, false);
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

        glucoseMeasurementReminderListViewModel = ViewModelProviders.of(this).get(GlucoseMeasurementReminderListViewModel.class);
        glucoseMeasurementReminderListViewModel.measurementReminders.observe(this, changeMeasurementReminders -> {
            measurementReminders = changeMeasurementReminders;
            glucoseMeasurementReminderListAdapter.setMeasurementReminders(measurementReminders);
        });
    }

    private void updateData() {

        ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();

        glucoseMeasurementReminderListViewModel = ViewModelProviders.of(this).get(GlucoseMeasurementReminderListViewModel.class);

        glucoseMeasurementReminderListViewModel.measurementReminders.observe(getViewLifecycleOwner(), new Observer<List<Reminder>>() {

            @Override
            public void onChanged(@Nullable List<Reminder> glucoseMeasurementReminderList) {
                measurementReminders = glucoseMeasurementReminderList;
                glucoseMeasurementReminderListAdapter.setMeasurementReminders(measurementReminders);
            }
        });

        glucoseMeasurementReminderListViewModel.getTitle().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer i) {
                ab.setTitle(i);
            }
        });
    }

    private void initView(View root) {

        Context context = getActivity().getApplicationContext();
        fragmentManager = getActivity().getSupportFragmentManager();
        ((DrawerLocker)getActivity()).setDrawerLocked(true);

        CustomRecyclerView recyclerView = root.findViewById(R.id.glucose_measurement_reminder_list);
        View mEmptyView = root.findViewById(R.id.empty_drops_glucose_measurement_reminder);
        recyclerView.showIfEmpty(mEmptyView);

        glucoseMeasurementReminderListAdapter = new GlucoseMeasurementReminderListAdapter(requireContext());
        recyclerView.setAdapter(glucoseMeasurementReminderListAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        final TextView noData = root.findViewById(R.id.glucose_measurement_reminder_no_data);
        glucoseMeasurementReminderListViewModel.getText().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer i) {
                if(glucoseMeasurementReminderListAdapter.getItemCount() == 0) {
                    noData.setText(i);
                    noData.setVisibility(View.VISIBLE);
                } else {
                    noData.setVisibility(View.GONE);
                }
            }
        });
    }
}
