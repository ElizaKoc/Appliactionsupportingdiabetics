package edu.pg.DiA.ui.journal;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.Date;

import edu.pg.DiA.R;
import edu.pg.DiA.database.AppDatabase;
import edu.pg.DiA.database.dao.NoteDao;
import edu.pg.DiA.interfaces.DrawerLocker;
import edu.pg.DiA.models.Note;

public class EditNoteFragment extends Fragment {

    private EditNoteViewModel editNoteViewModel;
    private AppDatabase db;
    private Context context;
    private NoteDao noteDao;

    private EditText titleEdit, bodyEdit;

    private int noteId;
    private Note note;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_edit_note, container, false);
        initView(root);
        return root;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        context = getActivity().getApplicationContext();
        db = AppDatabase.getInstance(context);
        noteDao = db.noteDao();

        if(getArguments() != null) {
            noteId = getArguments().getInt("note_id", 0);
            note = noteDao.getNote(noteId);
        }
    }

    private void initView(View root) {

        ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();
        ((DrawerLocker)getActivity()).setDrawerLocked(true);

        titleEdit = (EditText) root.findViewById(R.id.note_title_edit);
        bodyEdit = (EditText) root.findViewById(R.id.note_body_edit);

        editNoteViewModel =
                ViewModelProviders.of(this).get(EditNoteViewModel.class);
        editNoteViewModel.getTitle().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer i) {
                ab.setTitle(i);
            }
        });

        setData();

        Button addButton =  root.findViewById(R.id.button_edit_note);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!updateDataNote()) {

                    Toast.makeText(getContext(), "Nie udało się zmienić notatki",
                            Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(getContext(), "Zmieniono notatkę",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setData() {
        titleEdit.setText(note.name);
        bodyEdit.setText(note.description);
    }

    private boolean updateDataNote() {

        String title = (titleEdit).getText().toString();
        String body = (bodyEdit).getText().toString();
        Date date = new Date(new Date().getTime());

        if(title.equals("") || body.equals("")) {
            return false;
        } else {
            noteDao.updateNote(title, body, date, noteId);
            return true;
        }
    }
}
