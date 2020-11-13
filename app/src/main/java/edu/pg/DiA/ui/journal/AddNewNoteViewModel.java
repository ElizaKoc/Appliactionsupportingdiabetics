package edu.pg.DiA.ui.journal;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import edu.pg.DiA.R;

public class AddNewNoteViewModel extends AndroidViewModel {

    private MutableLiveData<Integer> mText;

    public AddNewNoteViewModel(@NonNull Application application) {
        super(application);
        mText = new MutableLiveData<>();
        mText.setValue(R.string.menu_add_new_note);
    }

    public LiveData<Integer> getTitle() {
        return mText;
    }
}
