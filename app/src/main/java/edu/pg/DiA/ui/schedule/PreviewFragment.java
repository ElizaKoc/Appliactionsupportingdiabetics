package edu.pg.DiA.ui.schedule;

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
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import edu.pg.DiA.R;
import edu.pg.DiA.adapters.PreviewAdapter;
import edu.pg.DiA.helpers.MedicineViewModelFactory;
import edu.pg.DiA.helpers.PreviewViewModelFactory;
import edu.pg.DiA.interfaces.DrawerLocker;
import edu.pg.DiA.models.Reminder;
import edu.pg.DiA.ui.medicines.MedicineViewModel;
import edu.pg.DiA.widgets.CustomRecyclerView;

public class PreviewFragment extends Fragment {

    private PreviewViewModel previewViewModel;
    private PreviewAdapter previewAdapter;
    private FragmentManager fragmentManager;
    private String selectedDate;
    public List<Reminder> reminders;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_reminder_list_schedule, container, false);
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

        if(getArguments() != null) {
            selectedDate = getArguments().getString("date");
        } else {
            selectedDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date().getTime());
        }

        previewViewModel = new ViewModelProvider(this, new PreviewViewModelFactory(this.getActivity().getApplication(), selectedDate)).get(PreviewViewModel.class);
        previewViewModel.reminders.observe(this, changeReminders -> {
            reminders = changeReminders;
            previewAdapter.setReminders(reminders, selectedDate);
        });
    }

    private void updateData() {

        ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();

        previewViewModel = new ViewModelProvider(this, new PreviewViewModelFactory(this.getActivity().getApplication(), selectedDate)).get(PreviewViewModel.class);
        previewViewModel.reminders.observe(getViewLifecycleOwner(), new Observer<List<Reminder>>() {

            @Override
            public void onChanged(@Nullable List<Reminder> scheduleReminderList) {
                reminders = scheduleReminderList;
                previewAdapter.setReminders(reminders, selectedDate);
            }
        });

        previewViewModel.getTitle().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                ab.setTitle(s);
            }
        });
    }

    private void initView(View root) {

        Context context = getActivity().getApplicationContext();
        fragmentManager = getActivity().getSupportFragmentManager();
        ((DrawerLocker)getActivity()).setDrawerLocked(true);

        CustomRecyclerView recyclerView = root.findViewById(R.id.schedule_reminder_list);
        View mEmptyView = root.findViewById(R.id.empty_drops_schedule_reminder);
        recyclerView.showIfEmpty(mEmptyView);

        previewAdapter = new PreviewAdapter(requireContext());
        recyclerView.setAdapter(previewAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        final TextView noData = root.findViewById(R.id.schedule_reminder_no_data);
        previewViewModel.getText().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer i) {
                if(previewAdapter.getItemCount() == 0) {
                    noData.setText(i);
                    noData.setVisibility(View.VISIBLE);
                } else {
                    noData.setVisibility(View.GONE);
                }
            }
        });
    }

}
