package edu.pg.DiA.ui.journal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import edu.pg.DiA.R;
import edu.pg.DiA.helpers.MedicineViewModelFactory;
import edu.pg.DiA.helpers.NoteViewModelFactory;

public class NoteFragment extends Fragment {

    private NoteViewModel noteViewModel;
    private FragmentManager fragmentManager;
    public int noteId;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_note, container, false);
        initView(root);
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initView(View root) {

        fragmentManager = getActivity().getSupportFragmentManager();
        noteId = getArguments().getInt("note_id", 0);
        noteViewModel = new ViewModelProvider(this, new NoteViewModelFactory(this.getActivity().getApplication(), noteId)).get(NoteViewModel.class);

        ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();

        noteViewModel.getTitle().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //ab.setDisplayHomeAsUpEnabled(false);
                ab.setTitle(s);
            }
        });

        final TextView body = root.findViewById(R.id.note_body_txt);
        noteViewModel.getBody().observe(getViewLifecycleOwner(), new Observer<String>() {

            @Override
            public void onChanged(@Nullable String d) {
                body.setText(d);
            }
        });
    }
}
