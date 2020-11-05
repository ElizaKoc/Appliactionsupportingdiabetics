package edu.pg.DiA.ui.help;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import edu.pg.DiA.R;

public class HelpViewModel extends ViewModel{

    private final MutableLiveData<String> mText;
    private final MutableLiveData<Integer> title;

    public HelpViewModel() {

        mText = new MutableLiveData<>();
        mText.setValue("This is help fragment");

        title = new MutableLiveData<>();
        title.setValue(R.string.menu_help);
    }

    public LiveData<String> getText() {
        return mText;
    }
    public LiveData<Integer> getTitle() {
        return title;
    }
}
