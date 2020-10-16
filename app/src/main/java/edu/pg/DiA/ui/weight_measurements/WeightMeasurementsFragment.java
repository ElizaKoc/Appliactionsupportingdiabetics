package edu.pg.DiA.ui.weight_measurements;

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

import edu.pg.DiA.R;

public class WeightMeasurementsFragment extends Fragment{

    private WeightMeasurementsViewModel weightMeasurementsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        weightMeasurementsViewModel =
                ViewModelProviders.of(this).get(WeightMeasurementsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_weight_measurements, container, false);
        final TextView textView = root.findViewById(R.id.text_weight_measurements);
        weightMeasurementsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
