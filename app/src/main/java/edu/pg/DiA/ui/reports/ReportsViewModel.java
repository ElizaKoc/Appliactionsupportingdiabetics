package edu.pg.DiA.ui.reports;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import edu.pg.DiA.R;

public class ReportsViewModel extends ViewModel{

    private final MutableLiveData<String> mText;
    private final MutableLiveData<Integer> title;

    public ReportsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is reports fragment");

        title = new MutableLiveData<>();
        title.setValue(R.string.menu_reports);
    }

    public LiveData<String> getText() {
        return mText;
    }
    public LiveData<Integer> getTitle() {
        return title;
    }
}
