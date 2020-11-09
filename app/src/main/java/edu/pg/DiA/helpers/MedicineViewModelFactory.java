package edu.pg.DiA.helpers;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import edu.pg.DiA.models.Medicine;
import edu.pg.DiA.ui.medicines.MedicineViewModel;

public class MedicineViewModelFactory implements ViewModelProvider.Factory{

    private Application mApplication;
    private Integer mParam;


    public MedicineViewModelFactory(Application application, int param) {
        mApplication = application;
        mParam = param;
    }


    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MedicineViewModel(mApplication, mParam);
    }
}
