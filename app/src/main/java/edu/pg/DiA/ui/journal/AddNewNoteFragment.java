package edu.pg.DiA.ui.journal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.Date;

import edu.pg.DiA.R;
import edu.pg.DiA.database.AppDatabase;
import edu.pg.DiA.models.Note;
import edu.pg.DiA.models.User;

public class AddNewNoteFragment extends Fragment {

    private AddNewNoteViewModel addNewNoteViewModel;
    private AppDatabase db;

    private EditText titleEdit, bodyEdit;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_add_new_note, container, false);
        initView(root);
        return root;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    private void initView(View root) {

        ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();

        titleEdit = (EditText) root.findViewById(R.id.note_title);
        bodyEdit = (EditText) root.findViewById(R.id.note_body);

        addNewNoteViewModel =
                ViewModelProviders.of(this).get(AddNewNoteViewModel.class);
        addNewNoteViewModel.getTitle().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer i) {
                ab.setDisplayHomeAsUpEnabled(true);
                ab.setTitle(i);
            }
        });

        Button addButton =  root.findViewById(R.id.add_button_new_note);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Note dataNote = getDataNote();
                long result = -1;

                if (dataNote != null) {
                    result = AppDatabase.getInstance(getContext()).noteDao().insert(dataNote);
                }

                if(result == -1) {
                    Toast.makeText(getContext(), "Nie udało się dodać notatki",
                            Toast.LENGTH_SHORT).show();

                } else {

                    clear();

                    Toast.makeText(getContext(), "Dodano notatkę",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @NotNull
    private Note getDataNote() {

        String title = (titleEdit).getText().toString();
        String body = (bodyEdit).getText().toString();
        int userId = User.getCurrentUser().uId;
        Date date = new Date(new Date().getTime());

        if(title.equals("") || body.equals("") || userId == 0) {
            return null;
        }
        return new Note(0, userId, title, body, date);
    }

    private void clear() {
        titleEdit.setText("");
        bodyEdit.setText("");
    }
}
