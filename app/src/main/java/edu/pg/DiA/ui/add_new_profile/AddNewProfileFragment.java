package edu.pg.DiA.ui.add_new_profile;

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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import org.jetbrains.annotations.NotNull;

import edu.pg.DiA.LoadProfilesActivity;
import edu.pg.DiA.MainActivity;
import edu.pg.DiA.R;
import edu.pg.DiA.database.AppDatabase;
import edu.pg.DiA.models.User;
import edu.pg.DiA.ui.profile_list.ProfileListFragment;

public class AddNewProfileFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    private AddNewProfileViewModel addNewProfileViewModel;

    public  EditText firstNameEdit;
    public  EditText lastNameEdit;
    public  EditText birthYearEdit;
    public  EditText heightEdit;
    public Spinner genderEdit;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_add_new_profile, container, false);
        initView(root);
        return root;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    private void createSpinner(View root) {
        Context context = getActivity().getApplicationContext();
        Spinner spinner = (Spinner) root.findViewById(R.id.spinner_sex);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.sex_array, R.layout.spinner_item); //android.R.layout.simple_spinner_item custom
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item); //android.R.layout.simple_spinner_dropdown_item custom
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    private void initView(View root) {

        ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();

        firstNameEdit = (EditText) root.findViewById(R.id.first_name);
        lastNameEdit = (EditText) root.findViewById(R.id.last_name);
        birthYearEdit = (EditText) root.findViewById(R.id.birth_year);
        heightEdit = (EditText) root.findViewById(R.id.height_cm);
        genderEdit = (Spinner) root.findViewById(R.id.spinner_sex);

        addNewProfileViewModel =
                ViewModelProviders.of(this).get(AddNewProfileViewModel.class);
        addNewProfileViewModel.getTitle().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer i) {
                ab.setDisplayHomeAsUpEnabled(true);
                ab.setTitle(i);
            }
        });

        createSpinner(root);

        Button addButton =  root.findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User dataUser = getDataUser();
                long result = -1;

                if(dataUser != null) {
                    result = AppDatabase.getInstance(getContext()).userDao().insert(dataUser);
                }

               if(result == -1) {
                   Toast.makeText(getContext(), "Nie udało się dodać profilu",
                           Toast.LENGTH_SHORT).show();

               } else {
                   /*Fragment profileListFragment = new ProfileListFragment();
                   FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                   transaction.replace(R.id.profile_list_fragment, profileListFragment);
                   transaction.commit();*/

                   clear();

                   Toast.makeText(getContext(), "Dodano profil",
                           Toast.LENGTH_SHORT).show();
               }

            }
        });
    }

    @NotNull
    private User getDataUser() {

        String firstName = (firstNameEdit).getText().toString();
        String lastName = (lastNameEdit).getText().toString();
        int birthYear = ((birthYearEdit).getText().toString().equals("")) ? 0 : Integer.parseInt((birthYearEdit).getText().toString());
        int height = ((heightEdit).getText().toString().equals("")) ? 0 : Integer.parseInt((heightEdit).getText().toString());
        String gender = (genderEdit).getSelectedItem().toString();

        if(firstName.equals("") || lastName.equals("") || birthYear == 0 || height == 0 || gender.equals("")) {
            return null;
        }
       return new User(firstName, lastName, birthYear, height, gender);
    }

    private void clear() {
        firstNameEdit.setText("");
        lastNameEdit.setText("");
        birthYearEdit.setText("");
        heightEdit.setText("");
        genderEdit.setSelection(0);
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
