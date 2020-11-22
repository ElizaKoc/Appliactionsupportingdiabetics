package edu.pg.DiA.ui.medicines;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.List;

import edu.pg.DiA.R;
import edu.pg.DiA.database.AppDatabase;
import edu.pg.DiA.database.dao.MedicineDao;
import edu.pg.DiA.interfaces.DrawerLocker;
import edu.pg.DiA.models.Medicine;

public class EditMedicineFragment extends Fragment {

    private EditMedicineViewModel editMedicineViewModel;
    private AppDatabase db;
    private Context context;

    private EditText nameEdit;
    private  EditText descriptionEdit;
    private Spinner unitEdit;

    private int medicineId;
    private Medicine medicine;
    private MedicineDao medicineDao;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_edit_medicine, container, false);
        initView(root);
        return root;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        context = getActivity().getApplicationContext();
        db = AppDatabase.getInstance(context);

        if(getArguments() != null) {
            medicineId = getArguments().getInt("medicine_id", 0);
            medicine =db.getInstance(context).medicineDao().getMedicineToEdit(medicineId);
        }
    }

    private void createSpinner(View root) {

        Context context = getActivity().getApplicationContext();
        Spinner spinner = (Spinner) root.findViewById(R.id.spinner_medicine_unit_edit);

        db = AppDatabase.getInstance(context);
        List<String> spinnerUnits = new ArrayList<String>();
        spinnerUnits.add("");
        for(int i=0; i < db.unitDao().getAllNames().size(); i++) {
            spinnerUnits.add((db.unitDao().getAllNames()).get(i));
        }

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.spinner_item_add_medicine, spinnerUnits);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_add_medicine);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    private void initView(View root) {

        ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();
        ((DrawerLocker)getActivity()).setDrawerLocked(true);

        medicineDao = db.medicineDao();

        nameEdit = (EditText) root.findViewById(R.id.medicine_name_edit);
        descriptionEdit = (EditText) root.findViewById(R.id.medicine_description_edit);
        unitEdit = (Spinner) root.findViewById(R.id.spinner_medicine_unit_edit);

        editMedicineViewModel =
                ViewModelProviders.of(this).get(EditMedicineViewModel.class);
        editMedicineViewModel.getTitle().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer i) {
                ab.setTitle(i);
            }
        });

        createSpinner(root);
        setData();

        Button addButton =  root.findViewById(R.id.button_edit_medicine);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!updateDataMedicine()) {

                    Toast.makeText(getContext(), "Nie udało się zmienić danych",
                            Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(getContext(), "Zmieniono dane",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setData() {
        nameEdit.setText(medicine.name);
        descriptionEdit.setText(medicine.description);
        unitEdit.setSelection(getIndex(unitEdit, db.unitDao().getName(medicine.unitId)));
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

    private boolean updateDataMedicine() {

        String name = (nameEdit).getText().toString();
        String description = (descriptionEdit).getText().toString();
        int unitId = db.unitDao().getUnitId((unitEdit).getSelectedItem().toString());

        if(name.equals("") || description.equals("") || unitId == 0) {
            return false;
        }
        else {
            try {
                medicineDao.updateMedicine(name, description, unitId, medicineId);
            } catch(Exception e) {
                Toast.makeText(getContext(), "Lek już istnieje!",
                        Toast.LENGTH_SHORT).show();

                return false;
            }
            return true;
        }
    }
}
