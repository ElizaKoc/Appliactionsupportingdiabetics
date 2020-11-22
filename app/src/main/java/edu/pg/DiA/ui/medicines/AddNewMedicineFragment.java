package edu.pg.DiA.ui.medicines;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import edu.pg.DiA.interfaces.DrawerLocker;
import edu.pg.DiA.models.Medicine;
import edu.pg.DiA.models.User;

public class AddNewMedicineFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    private AddNewMedicineViewModel addNewMedicineViewModel;
    private AppDatabase db;

    public EditText nameEdit;
    public  EditText descriptionEdit;
    public Spinner unitEdit;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_add_new_medicine, container, false);
        initView(root);
        return root;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    private void createSpinner(View root) {

        Context context = getActivity().getApplicationContext();
        Spinner spinner = (Spinner) root.findViewById(R.id.spinner_medicine_unit);

        db = AppDatabase.getInstance(context);
        List<String> spinnerUnits = new ArrayList<String>();
        spinnerUnits.add("");
        //spinnerUnits =  db.unitDao().getAllNames();
        for(int i=0; i < db.unitDao().getAllNames().size(); i++) {
            spinnerUnits.add((db.unitDao().getAllNames()).get(i));
        }

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.spinner_item_add_medicine, spinnerUnits);//android.R.layout.simple_spinner_item custom
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_add_medicine); //android.R.layout.simple_spinner_dropdown_item custom
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    private void initView(View root) {

        ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();
        ((DrawerLocker)getActivity()).setDrawerLocked(true);

        nameEdit = (EditText) root.findViewById(R.id.medicine_name);
        descriptionEdit = (EditText) root.findViewById(R.id.medicine_description);
        unitEdit = (Spinner) root.findViewById(R.id.spinner_medicine_unit);

        addNewMedicineViewModel =
                ViewModelProviders.of(this).get(AddNewMedicineViewModel.class);
        addNewMedicineViewModel.getTitle().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer i) {
                ab.setTitle(i);
            }
        });

        createSpinner(root);

        Button addButton =  root.findViewById(R.id.add_button_new_medicine);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Medicine dataMedicine = getDataMedicine();
                long result = -1;

                try {
                    if (dataMedicine != null) {
                        result = AppDatabase.getInstance(getContext()).medicineDao().insert(dataMedicine);
                    }

                    if(result == -1) {
                        Toast.makeText(getContext(), "Nie udało się dodać leku",
                                Toast.LENGTH_SHORT).show();

                    } else {
                        clear();

                        Toast.makeText(getContext(), "Dodano lek",
                                Toast.LENGTH_SHORT).show();
                    }

                } catch(Exception e){

                    Toast.makeText(getContext(), "Lek już istnieje!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @NotNull
    private Medicine getDataMedicine() {

        String name = (nameEdit).getText().toString();
        String description = (descriptionEdit).getText().toString();
        int userId = User.getCurrentUser().uId;
        int unitId = db.unitDao().getUnitId((unitEdit).getSelectedItem().toString());

        if(name.equals("") || description.equals("") || unitId == 0 || userId == 0 ) {
            return null;
        }
        return new Medicine(0, userId, unitId, name, description);
    }

    private void clear() {
        nameEdit.setText("");
        descriptionEdit.setText("");
        unitEdit.setSelection(0);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String selectedItemText = (String) parent.getItemAtPosition(position);
        // If user change the default selection
        // First item is disable and it is used for hint
        if (position > 0) {
            // Notify the selected item text
            Toast.makeText( getActivity().getApplicationContext(), "Selected : " + selectedItemText, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
