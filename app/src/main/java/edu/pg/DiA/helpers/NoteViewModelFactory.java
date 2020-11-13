package edu.pg.DiA.helpers;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import edu.pg.DiA.ui.journal.NoteViewModel;
import edu.pg.DiA.ui.medicines.MedicineViewModel;

public class NoteViewModelFactory implements ViewModelProvider.Factory{

    private Application mApplication;
    private Integer mParam;

    public NoteViewModelFactory(Application application, int param) {
        mApplication = application;
        mParam = param;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new NoteViewModel(mApplication, mParam);
    }
}
