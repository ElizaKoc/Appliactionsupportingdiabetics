package edu.pg.DiA.ui.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import edu.pg.DiA.R;

public class SettingsViewModel extends ViewModel{

    private final MutableLiveData<String> mText;
    private final MutableLiveData<Integer> title;

    public SettingsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is settings fragment");

        title = new MutableLiveData<>();
        title.setValue(R.string.menu_settings);
    }

    public LiveData<String> getText() {
        return mText;
    }
    public LiveData<Integer> getTitle() {
        return title;
    }
}
