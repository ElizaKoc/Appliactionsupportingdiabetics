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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import edu.pg.DiA.R;
import edu.pg.DiA.ui.profile.ProfileViewModel;

public class JournalFragment extends Fragment{

    private JournalViewModel journalViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_journal, container, false);
        updateData(root);
        return root;
    }

    private void updateData(View root) {

        ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();

        journalViewModel = ViewModelProviders.of(this).get(JournalViewModel.class);

        final TextView textView = root.findViewById(R.id.text_journal);
        journalViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        journalViewModel.getTitle().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer i) {
                //ab.setDisplayHomeAsUpEnabled(false);
                ab.setTitle(i);
            }
        });
    }
}
