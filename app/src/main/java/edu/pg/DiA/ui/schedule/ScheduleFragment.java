package edu.pg.DiA.ui.schedule;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import edu.pg.DiA.R;
import edu.pg.DiA.helpers.FindWeekday;
import edu.pg.DiA.interfaces.DrawerLocker;
import edu.pg.DiA.ui.reminder.AddNewReminderFragment;

public class ScheduleFragment extends Fragment{

    private ScheduleViewModel scheduleViewModel;
    private FragmentManager fragmentManager;
    private CompactCalendarView compactCalendarView;
    private TextView dateView;
    private  String strDate;
    public List<String> reminderDates, reminderWeekdays;

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

        scheduleViewModel.reminderDates.observe(getViewLifecycleOwner(), new Observer<List<String>>() {

            @Override
            public void onChanged(@Nullable List<String> dates) {
                reminderDates = dates;
                if(reminderDates != null) {
                    try {
                        setEvents(reminderDates);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        scheduleViewModel.reminderWeekdays.observe(getViewLifecycleOwner(), new Observer<List<String>>() {

            @Override
            public void onChanged(@Nullable List<String> weekdays) {
                reminderWeekdays = weekdays;
                if(reminderWeekdays != null) {
                    setEventsPeriodically(reminderWeekdays);
                }
            }
        });

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {

                strDate = new SimpleDateFormat("yyyy-MM-dd").format(dateClicked.getTime());
                changeDateText(dateClicked);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {

                strDate = new SimpleDateFormat("yyyy-MM-dd").format(firstDayOfNewMonth.getTime());
                changeDateText(firstDayOfNewMonth);
            }
        });

        scheduleViewModel.getTitle().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer i) {
                ab.setTitle(i);
            }
        });
    }

    private void changeDateText(Date date) {
        strDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        dateView.setText(strDate);
    }

    private void initView(View root) {

        Context context = getActivity().getApplicationContext();
        fragmentManager = getActivity().getSupportFragmentManager();
        ((DrawerLocker)getActivity()).setDrawerLocked(false);

        setCalendar(root);
        compactCalendarView.shouldDrawIndicatorsBelowSelectedDays(true);
        dateView = (TextView) root.findViewById(R.id.date_view);
        strDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date().getTime());
        dateView.setText(strDate);

        Button previewButton =  root.findViewById(R.id.calendar_preview_button);
        Button addReminderButton =  root.findViewById(R.id.calendar_add_reminder_button);

        previewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToScheduleReminderListFragment();
            }
        });

        addReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToAddReminderFragment();
            }
        });
    }

    private void setCalendar(View root) {
        compactCalendarView = (CompactCalendarView) root.findViewById(R.id.compact_calendar_view);
        compactCalendarView.setFirstDayOfWeek(Calendar.SUNDAY);
        String [] weekdays = {"N","P", "W", "Ś", "C", "P", "S" };
        compactCalendarView.setDayColumnNames(weekdays);
        compactCalendarView.shouldDrawIndicatorsBelowSelectedDays(true);
    }

    private void moveToScheduleReminderListFragment() {

        Fragment previewFragment = new PreviewFragment();

        Bundle args = new Bundle();
        args.putString("date", strDate);
        previewFragment.setArguments(args);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.change_list_fragment, previewFragment);
        transaction.setReorderingAllowed(true).addToBackStack(null);
        transaction.commit();
    }

    private void moveToAddReminderFragment() {

        Fragment addNewReminderFragment = new AddNewReminderFragment();

        Bundle args = new Bundle();
        args.putString("date", strDate);
        addNewReminderFragment.setArguments(args);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.change_list_fragment, addNewReminderFragment);
        transaction.setReorderingAllowed(true).addToBackStack(null);
        transaction.commit();
    }

    private void setEvents(List<String> dates) throws ParseException {

        if(dates != null) {
            for (int i = 0; i < dates.size(); i++) {
                if (dates.get(i) != null) {

                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dates.get(i));

                    Event ev = new Event(Color.BLUE, date.getTime());
                    compactCalendarView.addEvent(ev);
                }
            }
        }
    }

    private void setEventsPeriodically(List<String> weekdays) {

        List<Integer> weekdayIds = new ArrayList<Integer>();

        if(weekdays != null) {
            for (int i = 0; i < weekdays.size(); i++) {
                if (weekdays.get(i) != null) {
                    int weekdayId = new FindWeekday().findWeekdayId(weekdays.get(i));

                    if(!weekdayIds.contains(weekdayId)) {
                        weekdayIds.add(weekdayId);
                    }
                }
            }

            for (int i = 0; i < weekdayIds.size(); i++) {
                int weekdayId = weekdayIds.get(i);

                Calendar iDateStart = Calendar.getInstance();
                Calendar iDateEnd = Calendar.getInstance();

                if(weekdayId != 0 && weekdayId <= 7) {

                    while (iDateStart.get(Calendar.DAY_OF_WEEK) != weekdayId) {
                        iDateStart.add(Calendar.DATE, 1);
                    }

                    long dateInMillis = iDateStart.getTimeInMillis();
                    iDateEnd.add(Calendar.MONTH, 6);
                    long end = iDateEnd.getTimeInMillis();

                    while (dateInMillis <= end) {
                        Event ev = new Event(Color.RED, dateInMillis);
                        compactCalendarView.addEvent(ev);
                        dateInMillis += 1000 * 60 * 60 * 24 * 7;
                    }
                }
            }
        }
    }
}
