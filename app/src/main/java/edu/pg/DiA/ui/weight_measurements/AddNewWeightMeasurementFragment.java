package edu.pg.DiA.ui.weight_measurements;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

import edu.pg.DiA.R;
import edu.pg.DiA.database.AppDatabase;
import edu.pg.DiA.models.Body_weight_measurement;
import edu.pg.DiA.models.User;

public class AddNewWeightMeasurementFragment extends Fragment{

    private AddNewWeightMeasurementViewModel addNewWeightMeasurementViewModel;
    private AppDatabase db;

    public EditText measurementValueEdit;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_add_new_weight_measurement, container, false);
        initView(root);
        return root;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    private void initView(View root) {

        ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();

        measurementValueEdit = (EditText) root.findViewById(R.id.weight_measurement_value);

        addNewWeightMeasurementViewModel =
                ViewModelProviders.of(this).get(AddNewWeightMeasurementViewModel.class);
        addNewWeightMeasurementViewModel.getTitle().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer i) {
                ab.setDisplayHomeAsUpEnabled(true);
                ab.setTitle(i);
            }
        });

        Button addButton =  root.findViewById(R.id.add_button_new_weight_measurement);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Body_weight_measurement dataWeightMeasurement = getDataWeightMeasurement();
                long result = -1;

                if(dataWeightMeasurement != null) {
                    result = AppDatabase.getInstance(getContext()).bodyWeightMeasurementDao().insert(dataWeightMeasurement);
                }

                if(result == -1) {
                    Toast.makeText(getContext(), "Nie udało się dodać pomiaru",
                            Toast.LENGTH_SHORT).show();

                } else {
                    clear();

                    Toast.makeText(getContext(), "Dodano pomiar",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @NotNull
    private Body_weight_measurement getDataWeightMeasurement() {

        Float measurementValue = ((measurementValueEdit).getText().toString().equals("")) ? 0 : Float.parseFloat((measurementValueEdit).getText().toString());
        int userId = User.getCurrentUser().uId;
        Date date = new Date(new Date().getTime());

        if(measurementValue == 0 || userId == 0 ) {
            return null;
        }
        return new Body_weight_measurement(0, userId, measurementValue, date);
    }

    private void clear() {
        measurementValueEdit.setText("");
    }
}
