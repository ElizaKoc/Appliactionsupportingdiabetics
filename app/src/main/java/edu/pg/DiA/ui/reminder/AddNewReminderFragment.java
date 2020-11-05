package edu.pg.DiA.ui.reminder;

import android.content.Context;
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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import edu.pg.DiA.R;
import edu.pg.DiA.database.AppDatabase;
import edu.pg.DiA.database.dao.MedicineDao;
import edu.pg.DiA.models.Medicine;
import edu.pg.DiA.models.User;
import edu.pg.DiA.ui.medicines.AddNewMedicineViewModel;

public class AddNewReminderFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    private AddNewReminderViewModel addNewReminderViewModel;

    private TextView spinnerMedicineOptionLabel, medicineDoseLabel;
    private EditText medicineDoseEdit, reminderDateEdit, reminderTimeEdit;
    private Spinner alarmTypeEdit, medicineOptionEdit, repeatEdit, weekdayEdit;
    private Context context;
    private  int user = User.getCurrentUser().uId;
    private MedicineDao medicineDao;
    private int medicineId;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_add_new_reminder, container, false);
        initView(root);
        return root;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        medicineId = getArguments().getInt("medicineId", 0);
    }

    private void createSpinner(View root) {

        Spinner spinner = (Spinner) root.findViewById(R.id.spinner_medicine_option);

        AppDatabase db = AppDatabase.getInstance(context);
        List<String> spinnerMedicineOption = new ArrayList<String>();
        spinnerMedicineOption.add("");

        for(int i = 0; i < db.medicineDao().getAllNames(user).size(); i++) {
            spinnerMedicineOption.add((db.medicineDao().getAllNames(user)).get(i));
        }

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.spinner_item_reminder_medicine_option, spinnerMedicineOption);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_reminder); //android.R.layout.simple_spinner_dropdown_item custom
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    private void createSpinner(View root, String name_of_spinner) {

        int id = getResources().getIdentifier("spinner_" + name_of_spinner, "id", getActivity().getPackageName());
        int layout = getResources().getIdentifier("spinner_item_reminder_" + name_of_spinner, "layout", getActivity().getPackageName());
        int array = getResources().getIdentifier(name_of_spinner + "_array", "array", getActivity().getPackageName());

        Spinner spinner = (Spinner) root.findViewById(id);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, array, layout);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_reminder); //android.R.layout.simple_spinner_dropdown_item custom
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    private void initView(View root) {

        context = getActivity().getApplicationContext();
        medicineDao = AppDatabase.getInstance(context.getApplicationContext()).medicineDao();
        medicineId = getArguments().getInt("medicineId", 0);

        ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();
        String[] spinnerName = new String[]{"alarm_type", "repeat", "weekday"};
        setFields(root);
        setVisibilityGone();

        addNewReminderViewModel =
                ViewModelProviders.of(this).get(AddNewReminderViewModel.class);
        addNewReminderViewModel.getTitle().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer i) {
                ab.setDisplayHomeAsUpEnabled(true);
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

        Button addButton =  root.findViewById(R.id.add_button_new_reminder);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Medicine dataMedicine = getDataMedicine();
                long result = -1;

                //if(dataMedicine != null) {
                  //  result = AppDatabase.getInstance(getContext()).medicineDao().insert(dataMedicine);
                //}

                if(result == -1) {
                    Toast.makeText(getContext(), "Nie udało się dodać przypomnienia",
                            Toast.LENGTH_SHORT).show();

                } else {
                    clear();

                    Toast.makeText(getContext(), "Dodano przypomnienie",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
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
                        medicineOptionEdit.setVisibility(View.GONE);
                    }
                }
                else if(spinner == repeatEdit) {
                    if(position == 1) {
                        weekdayEdit.setVisibility(View.VISIBLE);
                        reminderDateEdit.setVisibility(View.GONE);
                    }
                    else if(position == 2) {
                        reminderDateEdit.setVisibility(View.VISIBLE);
                        weekdayEdit.setVisibility(View.GONE);
                    }
                    else {
                        weekdayEdit.setVisibility(View.GONE);
                        reminderDateEdit.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setFields(View root) {

        spinnerMedicineOptionLabel = (TextView) root.findViewById(R.id.spinner_medicine_option_label);
        medicineDoseLabel = (TextView) root.findViewById(R.id.medicine_dose_label);

        medicineDoseEdit = (EditText) root.findViewById(R.id.medicine_dose);
        reminderDateEdit = (EditText) root.findViewById(R.id.reminder_date);
        reminderTimeEdit = (EditText) root.findViewById(R.id.reminder_time);

        alarmTypeEdit = (Spinner) root.findViewById(R.id.spinner_alarm_type);
        medicineOptionEdit = (Spinner) root.findViewById(R.id.spinner_medicine_option);
        repeatEdit = (Spinner) root.findViewById(R.id.spinner_repeat);
        weekdayEdit = (Spinner) root.findViewById(R.id.spinner_weekday);
    }

    private void setDefaultValues() {

        if(medicineId != 0) {
            alarmTypeEdit.setSelection(1);
            medicineOptionEdit.setSelection(getIndex(medicineOptionEdit, medicineDao.getName(medicineId)));
        }
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

        if(medicineId == 0) {
            spinnerMedicineOptionLabel.setVisibility(View.GONE);
            medicineDoseLabel.setVisibility(View.GONE);

            medicineDoseEdit.setVisibility(View.GONE);
            medicineOptionEdit.setVisibility(View.GONE);
        }

        weekdayEdit.setVisibility(View.GONE);
        reminderDateEdit.setVisibility(View.GONE);
    }

    /*@NotNull
    private Medicine getDataMedicineReminder() {

        String name = (nameEdit).getText().toString();
        String description = (descriptionEdit).getText().toString();
        int userId = user;
        int unitId = db.unitDao().getUnitId((unitEdit).getSelectedItem().toString());

        if(name.equals("") || description.equals("") || unitId == 0 || userId == 0 ) {
            return null;
        }
        return new Medicine(0, userId, unitId, name, description);
    }*/

    private void clear() {
        medicineDoseEdit.setText("");
        reminderDateEdit.setText("");
        reminderTimeEdit.setText("");

        if (medicineId == 0) {
            alarmTypeEdit.setSelection(0);
            medicineOptionEdit.setSelection(0);
        } else {
            alarmTypeEdit.setSelection(1);
            medicineOptionEdit.setSelection(getIndex(medicineOptionEdit, medicineDao.getName(medicineId)));
        }

        repeatEdit.setSelection(0);
        weekdayEdit.setSelection(0);

        setVisibilityGone();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
