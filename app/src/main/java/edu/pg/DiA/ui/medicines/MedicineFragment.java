package edu.pg.DiA.ui.medicines;

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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.lifecycle.ViewModelProvider;


import java.util.List;

import edu.pg.DiA.R;
import edu.pg.DiA.adapters.MedicineAdapter;
import edu.pg.DiA.helpers.MedicineViewModelFactory;
import edu.pg.DiA.interfaces.DrawerLocker;
import edu.pg.DiA.interfaces.EventListener;
import edu.pg.DiA.models.MedicineReminderWithMedicineAndReminder;
import edu.pg.DiA.widgets.CustomRecyclerView;

public class MedicineFragment extends Fragment implements EventListener {

    private MedicineViewModel medicineViewModel;
    private MedicineAdapter medicineAdapter;
    private FragmentManager fragmentManager;
    public List<MedicineReminderWithMedicineAndReminder> medicineReminders;
    public int medicineId;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_medicine, container, false);
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

        medicineId = getArguments().getInt("medicine_id", 0);
        medicineViewModel = new ViewModelProvider(this, new MedicineViewModelFactory(this.getActivity().getApplication(), medicineId)).get(MedicineViewModel.class);
        medicineViewModel.medicineReminders.observe(this, changeMedicineReminders -> {
            medicineReminders = changeMedicineReminders;
            medicineAdapter.setMedicineReminders(medicineReminders);}
        );
    }

    private void updateData() {

        ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();

        medicineViewModel.medicineReminders.observe(getViewLifecycleOwner(), new Observer<List<MedicineReminderWithMedicineAndReminder>>() {

            @Override
            public void onChanged(@Nullable List<MedicineReminderWithMedicineAndReminder> medicinesList) {
                medicineReminders = medicinesList;
                medicineAdapter.setMedicineReminders(medicineReminders);
            }
        });

        medicineViewModel.getTitle().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                ab.setTitle(s);
            }
        });
    }

    private void initView(View root) {

        Context context = getActivity().getApplicationContext();
        fragmentManager = getActivity().getSupportFragmentManager();
        medicineId = getArguments().getInt("medicine_id", 0);
        medicineViewModel = new ViewModelProvider(this, new MedicineViewModelFactory(this.getActivity().getApplication(), medicineId)).get(MedicineViewModel.class);
        ((DrawerLocker)getActivity()).setDrawerLocked(true);

        CustomRecyclerView recyclerView = root.findViewById(R.id.medicine_list_reminder);
        View mEmptyView = root.findViewById(R.id.empty_drops_medicine_reminder);
        recyclerView.showIfEmpty(mEmptyView);

        medicineAdapter = new MedicineAdapter(requireContext(), this);
        recyclerView.setAdapter(medicineAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        final TextView description = root.findViewById(R.id.medicine_description_txt);
        medicineViewModel.getDescription().observe(getViewLifecycleOwner(), new Observer<String>() {

            @Override
            public void onChanged(@Nullable String d) {
                description.setText(d);
            }
        });

        final TextView noData = root.findViewById(R.id.medicine_reminder_no_data);
        medicineViewModel.getText().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer i) {
                if(medicineAdapter.getItemCount() == 0) {
                    noData.setText(i);
                    noData.setVisibility(View.VISIBLE);
                } else {
                    noData.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onEvent(Fragment fragment) {

        Bundle args = new Bundle();
        args.putInt("reminder_id", medicineAdapter.getReminder().reminder.rId);
        args.putInt("medicine_id", medicineAdapter.getReminder().medicine_reminder.medicineId);
        args.putString("dose", medicineAdapter.getReminder().medicine_reminder.doseUnits);
        args.putString("alarm_type", medicineAdapter.getReminder().reminder.alarm);
        args.putLong("date", medicineAdapter.getReminder().reminder.date != null ? medicineAdapter.getReminder().reminder.date.getTime() : 0);
        args.putString("weekday", medicineAdapter.getReminder().reminder.weekday);
        args.putString("time", medicineAdapter.getReminder().reminder.time);
        fragment.setArguments(args);

        // Create new fragment and transaction
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.change_list_fragment, fragment);
        transaction.setReorderingAllowed(true).addToBackStack(null);

        // Commit the transaction
        transaction.commit();

    }
}
