package edu.pg.DiA.ui.weight_measurements;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WeightMeasurementsViewModel extends ViewModel{

    private MutableLiveData<String> mText;

    public WeightMeasurementsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is weight measurements fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
