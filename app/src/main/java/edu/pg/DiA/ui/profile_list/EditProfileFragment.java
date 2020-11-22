package edu.pg.DiA.ui.profile_list;

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

import edu.pg.DiA.R;
import edu.pg.DiA.database.AppDatabase;
import edu.pg.DiA.database.dao.UserDao;
import edu.pg.DiA.models.User;

public class EditProfileFragment extends Fragment {

    private EditProfileViewModel editProfileViewModel;
    private AppDatabase db;
    private Context context;

    private int profileId;
    private User profile;
    private UserDao userDao;

    private  EditText firstNameEdit;
    private  EditText lastNameEdit;
    private  EditText birthYearEdit;
    private  EditText heightEdit;
    private Spinner genderEdit;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        initView(root);
        return root;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        context = getActivity().getApplicationContext();
        db = AppDatabase.getInstance(context);

        if(getArguments() != null) {
            profileId = getArguments().getInt("profile_id", 0);
            profile = db.getInstance(context).userDao().getUser(profileId);
        }
    }

    private void createSpinner(View root) {
        Context context = getActivity().getApplicationContext();
        Spinner spinner = (Spinner) root.findViewById(R.id.spinner_sex_edit);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.sex_array, R.layout.spinner_item_add_profile);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_add_profile);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    private void initView(View root) {

        ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();
        userDao = db.userDao();

        firstNameEdit = (EditText) root.findViewById(R.id.first_name_edit);
        lastNameEdit = (EditText) root.findViewById(R.id.last_name_edit);
        birthYearEdit = (EditText) root.findViewById(R.id.birth_year_edit);
        heightEdit = (EditText) root.findViewById(R.id.height_cm_edit);
        genderEdit = (Spinner) root.findViewById(R.id.spinner_sex_edit);

        editProfileViewModel =
                ViewModelProviders.of(this).get(EditProfileViewModel.class);
        editProfileViewModel.getTitle().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer i) {
                ab.setDisplayHomeAsUpEnabled(true);
                ab.setTitle(i);
            }
        });

        createSpinner(root);

        setData();

        Button addButton =  root.findViewById(R.id.button_edit_profile);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!updateDataUser()) {

                    Toast.makeText(getContext(), "Nie udało się edytować profilu",
                            Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getContext(), "Edytowano profil",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setData() {
        firstNameEdit.setText(profile.firstName);
        lastNameEdit.setText(profile.lastName);
        birthYearEdit.setText(String.valueOf(profile.birth_year));
        heightEdit.setText(String.valueOf(profile.height_cm));
        genderEdit.setSelection(getIndex(genderEdit, profile.sex));
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

    private boolean updateDataUser() {

        String firstName = (firstNameEdit).getText().toString();
        String lastName = (lastNameEdit).getText().toString();
        int birthYear = ((birthYearEdit).getText().toString().equals("")) ? 0 : Integer.parseInt((birthYearEdit).getText().toString());
        int height = ((heightEdit).getText().toString().equals("")) ? 0 : Integer.parseInt((heightEdit).getText().toString());
        String gender = (genderEdit).getSelectedItem().toString();

        if(firstName.equals("") || lastName.equals("") || birthYear == 0 || height == 0 || gender.equals("")) {
            return false;
        } else {
            try {
                userDao.updateProfile(firstName, lastName, birthYear, height, gender, profileId);
                return true;
            } catch(Exception e) {
                return false;
            }
        }
    }
}
