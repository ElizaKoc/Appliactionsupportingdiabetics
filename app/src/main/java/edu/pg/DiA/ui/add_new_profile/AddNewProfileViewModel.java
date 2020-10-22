package edu.pg.DiA.ui.add_new_profile;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import edu.pg.DiA.R;

public class AddNewProfileViewModel extends AndroidViewModel {

    private MutableLiveData<Integer> mText;

    public AddNewProfileViewModel(@NonNull Application application) {

        super(application);

        mText = new MutableLiveData<>();
        mText.setValue(R.string.add_new_profile);
    }

    public LiveData<Integer> getTitle() {
        return mText;
    }
}
