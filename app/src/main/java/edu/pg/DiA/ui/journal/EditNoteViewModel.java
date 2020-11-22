package edu.pg.DiA.ui.journal;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import edu.pg.DiA.R;

public class EditNoteViewModel extends AndroidViewModel {

    private MutableLiveData<Integer> title;

    public EditNoteViewModel(@NonNull Application application) {
        super(application);
        title = new MutableLiveData<>();
        title.setValue(R.string.edit_note);
    }

    public LiveData<Integer> getTitle() {
        return title;
    }
}
