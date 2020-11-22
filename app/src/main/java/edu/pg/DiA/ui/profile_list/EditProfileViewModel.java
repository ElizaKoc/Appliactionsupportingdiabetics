package edu.pg.DiA.ui.profile_list;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import edu.pg.DiA.R;

public class EditProfileViewModel extends AndroidViewModel {

    private MutableLiveData<Integer> title;

    public EditProfileViewModel(@NonNull Application application) {
        super(application);
        title = new MutableLiveData<>();
        title.setValue(R.string.edit_profile);
    }

    public LiveData<Integer> getTitle() {
        return title;
    }
}
