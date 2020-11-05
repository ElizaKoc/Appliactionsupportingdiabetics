package edu.pg.DiA.ui.schedule;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
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

import java.text.SimpleDateFormat;
import java.util.Date;

import edu.pg.DiA.R;
import edu.pg.DiA.adapters.MedicineListAdapter;
import edu.pg.DiA.ui.medicines.AddNewMedicineFragment;
import edu.pg.DiA.ui.medicines.MedicinesViewModel;
import edu.pg.DiA.ui.profile.ProfileViewModel;
import edu.pg.DiA.widgets.CustomRecyclerView;

public class ScheduleFragment extends Fragment{

    private ScheduleViewModel scheduleViewModel;
    private FragmentManager fragmentManager;
    public CalendarView calenderView;
    public TextView dateView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_schedule, container, false);
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
        scheduleViewModel = ViewModelProviders.of(this).get(ScheduleViewModel.class);

    }

    private void updateData() {

        ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();

        scheduleViewModel = ViewModelProviders.of(this).get(ScheduleViewModel.class);

        calenderView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                // Store the value of date with format in String type Variable
                // Add 1 in month because month index is start with 0

                String Date = String.format("%02d", dayOfMonth) + "-" + String.format("%02d", (month + 1)) + "-" + year;

                // set this date in TextView for Display
                dateView.setText(Date);
            }
        });

        scheduleViewModel.getTitle().observe(getViewLifecycleOwner(), new Observer<Integer>() {
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

        calenderView = (CalendarView) root.findViewById(R.id.calendar_view);
        dateView = (TextView) root.findViewById(R.id.date_view);
        String strDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date().getTime());
        dateView.setText(strDate);
    }
}
