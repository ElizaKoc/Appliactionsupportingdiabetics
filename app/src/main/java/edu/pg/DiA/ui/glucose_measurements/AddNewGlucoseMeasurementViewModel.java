package edu.pg.DiA.ui.glucose_measurements;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import edu.pg.DiA.R;

public class AddNewGlucoseMeasurementViewModel extends AndroidViewModel{

    private MutableLiveData<Integer> mText;

    public AddNewGlucoseMeasurementViewModel(@NonNull Application application) {

        super(application);

        mText = new MutableLiveData<>();
        mText.setValue(R.string.menu_add_new_glucose_measurement);
    }

    public LiveData<Integer> getTitle() {
        return mText;
    }
}