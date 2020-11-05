package edu.pg.DiA.ui.reminder;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import edu.pg.DiA.R;

public class AddNewReminderViewModel extends AndroidViewModel {

    private MutableLiveData<Integer> mText;

    public AddNewReminderViewModel(@NonNull Application application) {
        super(application);
        mText = new MutableLiveData<>();
        mText.setValue(R.string.menu_add_new_reminder);
    }

    public LiveData<Integer> getTitle() {
        return mText;
    }
}
