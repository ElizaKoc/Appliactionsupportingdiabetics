package com.example.appliactionsupportingdiabetics.ui.medicines;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.appliactionsupportingdiabetics.R;

public class MedicinesFragment extends Fragment{

    private MedicinesViewModel medicinesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        medicinesViewModel =
                ViewModelProviders.of(this).get(MedicinesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_medicines, container, false);
        final TextView textView = root.findViewById(R.id.text_medicines);
        medicinesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
