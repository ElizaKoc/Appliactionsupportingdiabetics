package edu.pg.DiA.ui.weight_measurements;

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
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import edu.pg.DiA.R;
import edu.pg.DiA.adapters.WeightMeasurementsListAdapter;
import edu.pg.DiA.interfaces.EventListener;
import edu.pg.DiA.models.BodyWeightMeasurement;
import edu.pg.DiA.widgets.CustomRecyclerView;

public class WeightMeasurementsFragment extends Fragment implements EventListener {

    private WeightMeasurementsViewModel weightMeasurementsViewModel;
    private WeightMeasurementsListAdapter weightMeasurementsListAdapter;
    private FragmentManager fragmentManager;
    public List<BodyWeightMeasurement> measurements;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_weight_measurements_list, container, false);
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
        weightMeasurementsViewModel = ViewModelProviders.of(this).get(WeightMeasurementsViewModel.class);
        weightMeasurementsViewModel.measurements.observe(this, changeMeasurements -> {
            measurements = changeMeasurements;
            weightMeasurementsListAdapter.setMeasurements(measurements);
        });
    }

    private void updateData() {

        ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();

        weightMeasurementsViewModel = ViewModelProviders.of(this).get(WeightMeasurementsViewModel.class);

        weightMeasurementsViewModel.measurements.observe(getViewLifecycleOwner(), new Observer<List<BodyWeightMeasurement>>() {

            @Override
            public void onChanged(@Nullable List<BodyWeightMeasurement> weightMeasurementsList) {
                measurements = weightMeasurementsList;
                weightMeasurementsListAdapter.setMeasurements(measurements);
            }
        });

        weightMeasurementsViewModel.getTitle().observe(getViewLifecycleOwner(), new Observer<Integer>() {
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

        CustomRecyclerView recyclerView = root.findViewById(R.id.weight_measurement_list);
        View mEmptyView = root.findViewById(R.id.empty_drops_weight_measurements);
        recyclerView.showIfEmpty(mEmptyView);

        weightMeasurementsListAdapter = new WeightMeasurementsListAdapter(requireContext(), this);
        recyclerView.setAdapter(weightMeasurementsListAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        final TextView noData = root.findViewById(R.id.weight_measurement_no_data);
        weightMeasurementsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer i) {
                if(weightMeasurementsListAdapter.getItemCount() == 0) {
                    noData.setText(i);
                    noData.setVisibility(View.VISIBLE);
                } else {
                    noData.setVisibility(View.GONE);
                }
            }
        });

        FloatingActionButton add_weight_measure_button = root.findViewById(R.id.add_weight_measurement_button);

        add_weight_measure_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create new fragment and transaction
                Fragment addNewWeightMeasurementFragment = new AddNewWeightMeasurementFragment();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                transaction.replace(R.id.change_list_fragment, addNewWeightMeasurementFragment);
                transaction.setReorderingAllowed(true).addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });
    }

    @Override
    public void onEvent(Fragment fragment) {

    }
}
