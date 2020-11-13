package edu.pg.DiA.ui.literature;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import edu.pg.DiA.R;

public class LiteratureViewModel extends AndroidViewModel {

    private final MutableLiveData<Integer> title;

    public LiteratureViewModel(Application application) {

        super(application);

        title = new MutableLiveData<>();
        title.setValue(R.string.menu_literature);
    }

    public LiveData<Integer> getTitle() {
        return title;
    }
}
