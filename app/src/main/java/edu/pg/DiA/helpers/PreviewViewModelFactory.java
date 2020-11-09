package edu.pg.DiA.helpers;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.text.ParseException;

import edu.pg.DiA.ui.schedule.PreviewViewModel;

public class PreviewViewModelFactory implements ViewModelProvider.Factory {


    private Application mApplication;
    private String mParam;


    public PreviewViewModelFactory(Application application, String param) {
        mApplication = application;
        mParam = param;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        try {
            return (T) new PreviewViewModel(mApplication, mParam);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
