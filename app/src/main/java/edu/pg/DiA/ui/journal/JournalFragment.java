package edu.pg.DiA.ui.journal;

import android.content.Context;
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
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import edu.pg.DiA.R;
import edu.pg.DiA.adapters.JournalAdapter;
import edu.pg.DiA.interfaces.DrawerLocker;
import edu.pg.DiA.interfaces.EventListener;
import edu.pg.DiA.models.Note;
import edu.pg.DiA.widgets.CustomRecyclerView;

public class JournalFragment extends Fragment implements EventListener {

    private JournalViewModel journalViewModel;
    private JournalAdapter journalAdapter;
    private FragmentManager fragmentManager;
    public List<Note> notes;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_journal, container, false);
        initView(root);
        updateData();
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {

        setHasOptionsMenu(true);
        ((DrawerLocker)getActivity()).setDrawerLocked(false);

        journalViewModel = ViewModelProviders.of(this).get(JournalViewModel.class);
        journalViewModel.notes.observe(this, changeNotes -> {
            notes = changeNotes;
            journalAdapter.setNotes(notes);
        });
    }

    private void updateData() {

        ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();

        journalViewModel = ViewModelProviders.of(this).get(JournalViewModel.class);

        journalViewModel.notes.observe(getViewLifecycleOwner(), new Observer<List<Note>>() {

            @Override
            public void onChanged(@Nullable List<Note> notesList) {
                notes = notesList;
                journalAdapter.setNotes(notes);
            }
        });

        journalViewModel.getTitle().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer i) {
                ab.setTitle(i);
            }
        });
    }

    private void initView(View root) {

        Context context = getActivity().getApplicationContext();
        fragmentManager = getActivity().getSupportFragmentManager();
        ((DrawerLocker)getActivity()).setDrawerLocked(false);

        CustomRecyclerView recyclerView = root.findViewById(R.id.note_list);
        View mEmptyView = root.findViewById(R.id.empty_drops_notes);
        recyclerView.showIfEmpty(mEmptyView);

        journalAdapter = new JournalAdapter(requireContext(), this);
        recyclerView.setAdapter(journalAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        final TextView noData = root.findViewById(R.id.note_no_data);
        journalViewModel.getText().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer i) {
                if(journalAdapter.getItemCount() == 0) {
                    noData.setText(i);
                    noData.setVisibility(View.VISIBLE);
                } else {
                    noData.setVisibility(View.GONE);
                }
            }
        });

        FloatingActionButton add_note_button = root.findViewById(R.id.add_note_button);

        add_note_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create new fragment and transaction
                Fragment addNewNoteFragment = new AddNewNoteFragment();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                transaction.replace(R.id.change_list_fragment, addNewNoteFragment);
                transaction.setReorderingAllowed(true).addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });
    }

    @Override
    public void onEvent(@Nullable Fragment fragment) {

        Bundle args = new Bundle();
        args.putInt("note_id", journalAdapter.getNote().nId);
        fragment.setArguments(args);

        // Create new fragment and transaction
        //Fragment medicineFragment = new MedicineFragment(medicineListAdapter.getMedicine());
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.change_list_fragment, fragment);
        transaction.setReorderingAllowed(true).addToBackStack(null);

        // Commit the transaction
        transaction.commit();

    }
}
