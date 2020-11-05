package edu.pg.DiA.ui.diet;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import edu.pg.DiA.R;

public class DietViewModel extends ViewModel{

    private final MutableLiveData<String> mText;
    private final MutableLiveData<Integer> title;

    public DietViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is diet fragment");
        
        title = new MutableLiveData<>();
        title.setValue(R.string.menu_diet);
    }

    public LiveData<String> getText() {
        return mText;
    }
    public LiveData<Integer> getTitle() {
        return title;
    }
}
