package edu.pg.DiA.ui.schedule;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import edu.pg.DiA.R;

public class ScheduleViewModel extends ViewModel{

    private final MutableLiveData<String> mText;
    private final MutableLiveData<Integer> title;

    public ScheduleViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is schedule fragment");

        title = new MutableLiveData<>();
        title.setValue(R.string.menu_schedule);
    }

    public LiveData<String> getText() {
        return mText;
    }
    public LiveData<Integer> getTitle() {
        return title;
    }
}
