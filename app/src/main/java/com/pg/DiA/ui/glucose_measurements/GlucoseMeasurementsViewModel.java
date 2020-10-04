package com.pg.DiA.ui.glucose_measurements;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GlucoseMeasurementsViewModel extends ViewModel{

    private MutableLiveData<String> mText;

    public GlucoseMeasurementsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is glucose measurements fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
