package edu.pg.DiA.ui.medicines;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import edu.pg.DiA.adapters.MedicineListAdapter;
import edu.pg.DiA.interfaces.EventListener;
import edu.pg.DiA.models.Medicine;
import edu.pg.DiA.ui.journal.JournalFragment;
import edu.pg.DiA.widgets.CustomRecyclerView;

public class MedicinesFragment extends Fragment implements EventListener {

    private MedicinesViewModel medicinesViewModel;
    private MedicineListAdapter medicineListAdapter;
    private FragmentManager fragmentManager;
    public List<Medicine> medicines;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_medicine_list, container, false);
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
        medicinesViewModel = ViewModelProviders.of(this).get(MedicinesViewModel.class);
        medicinesViewModel.medicines.observe(this, changeMedicines -> {
            medicines = changeMedicines;
            medicineListAdapter.setMedicines(medicines);}
        );
    }

    private void updateData() {

        ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();

        medicinesViewModel = ViewModelProviders.of(this).get(MedicinesViewModel.class);
        medicinesViewModel.medicines.observe(getViewLifecycleOwner(), new Observer<List<Medicine>>() {

            @Override
            public void onChanged(@Nullable List<Medicine> medicinesList) {
                medicines = medicinesList;
                medicineListAdapter.setMedicines(medicines);
            }
        });

        medicinesViewModel.getTitle().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer i) {
                //ab.setDisplayHomeAsUpEnabled(false);
                ab.setTitle(i);
            }
        });

        /*medicinesViewModel.getTitle().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer i) {
                ab.setDisplayHomeAsUpEnabled(false);
                ab.setTitle(i);
            }
        });*/
    }

    private void initView(View root) {

        Context context = getActivity().getApplicationContext();
        fragmentManager = getActivity().getSupportFragmentManager();

        //RecyclerView recyclerView = root.findViewById(R.id.medicine_list);
        CustomRecyclerView recyclerView = root.findViewById(R.id.medicine_list);
        View mEmptyView = root.findViewById(R.id.empty_drops_medicines);
        recyclerView.showIfEmpty(mEmptyView);

        medicineListAdapter = new MedicineListAdapter(requireContext(), this);
        recyclerView.setAdapter(medicineListAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        final TextView noData = root.findViewById(R.id.medicine_no_data);
        medicinesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer i) {
                if(medicineListAdapter.getItemCount() == 0) {
                    noData.setText(i);
                    noData.setVisibility(View.VISIBLE);
                } else {
                    noData.setVisibility(View.GONE);
                }
            }
        });

        FloatingActionButton add_medicine_button = root.findViewById(R.id.add_medicine_button);
        add_medicine_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Create new fragment and transaction
                Fragment addNewMedicineFragment = new AddNewMedicineFragment();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                transaction.replace(R.id.change_list_fragment, addNewMedicineFragment);
                transaction.setReorderingAllowed(true).addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });
    }

    @Override
    public void onEvent(Fragment fragment) {
        Toast.makeText(getContext(), "IT WORKS!",
                Toast.LENGTH_SHORT).show();

        Bundle args = new Bundle();
        args.putInt("medicineId", medicineListAdapter.getMedicine().mId);
        fragment.setArguments(args);


        // Create new fragment and transaction
        //Fragment medicineFragment = new MedicineFragment(medicineListAdapter.getMedicine());
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.change_list_fragment, fragment);
        transaction.setReorderingAllowed(true).addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }
}
