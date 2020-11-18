package edu.pg.DiA.ui.reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDate;
import org.threeten.bp.ZoneId;
import org.threeten.bp.temporal.TemporalAdjusters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.pg.DiA.R;
import edu.pg.DiA.database.AppDatabase;
import edu.pg.DiA.database.dao.MedicineDao;
import edu.pg.DiA.database.dao.MedicineReminderDao;
import edu.pg.DiA.database.dao.ReminderDao;
import edu.pg.DiA.database.dao.UnitDao;
import edu.pg.DiA.interfaces.DrawerLocker;
import edu.pg.DiA.models.MedicineReminder;
import edu.pg.DiA.models.Reminder;
import edu.pg.DiA.models.User;

public class EditReminderFragment extends Fragment {

    private EditReminderViewModel editReminderViewModel;
    private  int user = User.getCurrentUser().uId, reminderId, editMedicineId;
    private Context context;

    private TextView spinnerMedicineOptionLabel, medicineDoseLabel;
    private EditText medicineDoseEdit, reminderDateEdit, reminderTimeEdit;
    private Spinner alarmTypeEdit, medicineOptionEdit, repeatEdit, weekdayEdit;

    private AppDatabase db;
    private MedicineDao medicineDao;
    private ReminderDao reminderDao;
    private MedicineReminderDao medicineReminderDao;
    private UnitDao unitDao;

    private String isMeasurement, selectedDate;
    private String alarmType, weekday, reminderTime, reminderDate, medicineOption, medicineDose, editAlarmType, editDate, editWeekday, editReminderTime, editDose;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_edit_reminder, container, false);
        initView(root);
        return root;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        context = getActivity().getApplicationContext();
        db = AppDatabase.getInstance(context);

        medicineDao = db.medicineDao();
        reminderDao = db.reminderDao();
        medicineReminderDao = db.medicineReminderDao();
        unitDao = db.unitDao();

        if(getArguments() != null) {

            reminderId = getArguments().getInt("reminder_id", 0);
            editMedicineId = getArguments().getInt("medicine_id", 0);
            editDose = getArguments().getString("dose", "");
            editAlarmType = getArguments().getString("alarm_type", "");
            editWeekday = getArguments().getString("weekday", "");

            if(editWeekday.equals("")) {
                String d = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(getArguments().getLong("date", 0)));
                String[] array = d.split(" ");
                editDate = array[0];
            }else {
                editDate = "";
            }

            editReminderTime = getArguments().getString("time", "");
        }
    }

    private void createSpinner(View root) {

        Spinner spinner = (Spinner) root.findViewById(R.id.spinner_medicine_option_edit);

        db = AppDatabase.getInstance(context);
        List<String> spinnerMedicineOption = new ArrayList<String>();
        spinnerMedicineOption.add("");

        for(int i = 0; i < db.medicineDao().getAllNames(user).size(); i++) {
            spinnerMedicineOption.add((db.medicineDao().getAllNames(user)).get(i));
        }

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.spinner_item_reminder_medicine_option, spinnerMedicineOption);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_reminder);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    private void createSpinner(View root, String name_of_spinner) {

        int id = getResources().getIdentifier("spinner_" + name_of_spinner + "_edit", "id", getActivity().getPackageName());
        int layout = getResources().getIdentifier("spinner_item_reminder_" + name_of_spinner, "layout", getActivity().getPackageName());
        int array = getResources().getIdentifier(name_of_spinner + "_array", "array", getActivity().getPackageName());

        Spinner spinner = (Spinner) root.findViewById(id);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, array, layout);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_reminder);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    private void initView(View root) {

        ((DrawerLocker)getActivity()).setDrawerLocked(true);
        ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();
        String[] spinnerName = new String[]{"alarm_type", "repeat", "weekday"};
        setFields(root);
        setVisibilityGone();

        editReminderViewModel =
                ViewModelProviders.of(this).get(EditReminderViewModel.class);
        editReminderViewModel.getTitle().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer i) {
                ab.setTitle(i);
            }
        });

        for (String s : spinnerName) {
            createSpinner(root, s);
        }

        createSpinner(root);
        setDefaultValues();
        setOnItemSelected(alarmTypeEdit);
        setOnItemSelected(repeatEdit);

        Button addButton =  root.findViewById(R.id.edit_button_reminder);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                long result = -1;

                try {
                    if(updateReminder()) {

                        result = 1;

                        if (!(medicineOption.equals(""))) {

                            int medicineId = medicineDao.getMedicineId(user, medicineOption);
                            medicineReminderDao.updateMedicineReminder(medicineId, medicineDose, reminderId);
                        }

                        setAlarm();
                    }

                    if (result == -1) {
                        Toast.makeText(getContext(), "Nie udało się zmienić przypomnienia",
                                Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getContext(), "Zmieniono przypomnienie",
                                Toast.LENGTH_SHORT).show();
                    }

                } catch (ParseException e) {

                    if(reminderDateEdit.getVisibility() == View.GONE) {
                        Toast.makeText(getContext(), "Nie udało się zmienić przypomnienia",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Niewłaściwy format daty!",
                                Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();

                    Toast.makeText(getContext(), "Nie udało się zmienić przypomnienia",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setAlarm() throws ParseException {

        String repeat = (repeatEdit).getSelectedItem().toString();
        Long dateInMillis;

        AlarmManager alarmManager  = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(context, Receiver.class);

        alarmIntent.putExtra("reminder_id", reminderDao.getLatesReminderId());
        alarmIntent.putExtra("alarm_type", alarmType);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, reminderDao.getLatesReminderId(), alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        if(repeat.equals("nie")) {

            String date = reminderDate + " " + reminderTime;
            Date reminderDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date);
            dateInMillis = reminderDateTime.getTime();

            if (Build.VERSION.SDK_INT >= 23) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, dateInMillis, pendingIntent);
            } else if (Build.VERSION.SDK_INT >= 19) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, dateInMillis, pendingIntent);
            } else {
                alarmManager.set(AlarmManager.RTC_WAKEUP, dateInMillis, pendingIntent);
            }
        }
        else if(repeat.equals("tak")) {

            int weekdayId = getIndex(weekdayEdit, weekday);

            LocalDate date = LocalDate.now();
            String[] parts = reminderTime.split(":");
            int hours = Integer.parseInt(parts[0]);
            int mins =   Integer.parseInt(parts[1]);

            Date currentDate = new Date();
            int currentHours = Integer.parseInt(new SimpleDateFormat("HH").format(currentDate));
            int currentMins = Integer.parseInt(new SimpleDateFormat("mm").format(currentDate));

            if(hours < currentHours || (hours == currentHours && mins < currentMins)) {
                date = date.with(TemporalAdjusters.next(DayOfWeek.of(weekdayId)));
            }
            else {
                date = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.of(weekdayId)));
            }

            dateInMillis = date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli() + (hours * 60 * 60 * 1000) + (mins * 60 * 1000);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,  dateInMillis, 1000 * 60 * 60 * 24 * 7, pendingIntent);
        }
    }

    private void setOnItemSelected(Spinner spinner) {

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(spinner == alarmTypeEdit) {
                    if(position == 1) {
                        spinnerMedicineOptionLabel.setVisibility(View.VISIBLE);
                        medicineDoseLabel.setVisibility(View.VISIBLE);

                        medicineDoseEdit.setVisibility(View.VISIBLE);
                        medicineOptionEdit.setVisibility(View.VISIBLE);
                    }
                    else {
                        spinnerMedicineOptionLabel.setVisibility(View.GONE);
                        medicineDoseLabel.setVisibility(View.GONE);

                        medicineDoseEdit.setVisibility(View.GONE);
                        medicineDoseEdit.setText("");
                        medicineOptionEdit.setVisibility(View.GONE);
                        medicineOptionEdit.setSelection(0);
                    }
                }
                else if(spinner == repeatEdit) {
                    if(position == 1) {
                        weekdayEdit.setVisibility(View.VISIBLE);
                        reminderDateEdit.setVisibility(View.GONE);
                        reminderDateEdit.setText("");
                    }
                    else if(position == 2) {
                        reminderDateEdit.setVisibility(View.VISIBLE);
                        weekdayEdit.setVisibility(View.GONE);
                        weekdayEdit.setSelection(0);
                    }
                    else {
                        weekdayEdit.setVisibility(View.GONE);
                        weekdayEdit.setSelection(0);
                        reminderDateEdit.setVisibility(View.GONE);
                        reminderDateEdit.setText("");
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setFields(View root) {

        spinnerMedicineOptionLabel = (TextView) root.findViewById(R.id.spinner_medicine_option_label_edit);
        medicineDoseLabel = (TextView) root.findViewById(R.id.medicine_dose_label_edit);

        medicineDoseEdit = (EditText) root.findViewById(R.id.medicine_dose_edit);
        reminderDateEdit = (EditText) root.findViewById(R.id.reminder_date_edit);
        reminderTimeEdit = (EditText) root.findViewById(R.id.reminder_time_edit);

        alarmTypeEdit = (Spinner) root.findViewById(R.id.spinner_alarm_type_edit);
        medicineOptionEdit = (Spinner) root.findViewById(R.id.spinner_medicine_option_edit);
        repeatEdit = (Spinner) root.findViewById(R.id.spinner_repeat_edit);
        weekdayEdit = (Spinner) root.findViewById(R.id.spinner_weekday_edit);

        (root.findViewById(R.id.spinner_alarm_type_label_edit)).setVisibility(View.GONE);
    }

    private void setDefaultValues() {

        alarmTypeEdit.setVisibility(View.INVISIBLE);

        if(editMedicineId != 0) {
            alarmTypeEdit.setSelection(1);
            medicineOptionEdit.setSelection(getIndex(medicineOptionEdit, medicineDao.getName(editMedicineId)));
            medicineDoseEdit.setText(editDose);
        }
        else if(editAlarmType.equals("pomiar glukozy")) {
            alarmTypeEdit.setSelection(2);
        }

        if(!editDate.equals("")) {
            repeatEdit.setSelection(2);
            reminderDateEdit.setText(editDate);
        }
        else if(!editWeekday.equals("")){
            repeatEdit.setSelection(1);
            weekdayEdit.setSelection(getIndex(weekdayEdit, editWeekday));
        }

        reminderTimeEdit.setText(editReminderTime);

    }

    private int getIndex(Spinner spinner, String myString){

        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++){
            if (spinner.getItemAtPosition(i).equals(myString)){
                index = i;
            }
        }
        return index;
    }

    private void setVisibilityGone() {

        if(editMedicineId == 0) {
            spinnerMedicineOptionLabel.setVisibility(View.GONE);
            medicineDoseLabel.setVisibility(View.GONE);

            medicineDoseEdit.setVisibility(View.GONE);
            medicineDoseEdit.setText("");
            medicineOptionEdit.setVisibility(View.GONE);
            medicineOptionEdit.setSelection(0);
        }

        weekdayEdit.setVisibility(View.GONE);
        weekdayEdit.setSelection(0);

        if(editDate.equals("")) {
            reminderDateEdit.setVisibility(View.GONE);
            reminderDateEdit.setText("");
        }
    }

    private boolean updateReminder() throws ParseException {

        Date reminderDateParsed = null;

        alarmType = (alarmTypeEdit).getSelectedItem().toString();
        weekday = (weekdayEdit).getSelectedItem().toString();
        reminderTime = (reminderTimeEdit).getText().toString();
        reminderDate = "";

        if(weekday.equals("")) {
            reminderDate = (reminderDateEdit).getText().toString();
            reminderDateParsed =  new SimpleDateFormat("yyyy-MM-dd").parse(reminderDate);
        }

        medicineOption = (medicineOptionEdit).getSelectedItem().toString();
        medicineDose = (medicineDoseEdit).getText().toString();

        if(alarmType.equals("") || reminderTime.equals("") || (weekday.equals("") && reminderDate.equals("")) || (alarmType.equals("lekarstwo") && (medicineDose.equals("") || medicineOption.equals(""))) || (!weekday.equals("") && !reminderDate.equals(""))) {
            return false;
        }
        else {

            reminderDao.updateReminder(weekday, reminderTime, reminderDateParsed, reminderId);
            return true;
        }
    }
}
