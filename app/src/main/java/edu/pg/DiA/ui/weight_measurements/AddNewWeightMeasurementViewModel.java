package edu.pg.DiA.ui.weight_measurements;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import edu.pg.DiA.R;

public class AddNewWeightMeasurementViewModel extends AndroidViewModel {

    private MutableLiveData<Integer> mText;

    public AddNewWeightMeasurementViewModel(@NonNull Application application) {

        super(application);

        mText = new MutableLiveData<>();
        mText.setValue(R.string.menu_add_new_weight_measurement);
    }

    public LiveData<Integer> getTitle() {
        return mText;
    }
}
