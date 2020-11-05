package edu.pg.DiA.ui.journal;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import edu.pg.DiA.R;

public class JournalViewModel extends ViewModel{

    private final MutableLiveData<String> mText;
    private final MutableLiveData<Integer> title;

    public JournalViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is journal fragment");

        title = new MutableLiveData<>();
        title.setValue(R.string.menu_journal);
    }

    public LiveData<String> getText() {
        return mText;
    }
    public LiveData<Integer> getTitle() {
        return title;
    }
}
