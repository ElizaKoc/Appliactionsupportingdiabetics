package edu.pg.DiA.ui.medicines;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import edu.pg.DiA.R;

public class AddNewMedicineViewModel extends AndroidViewModel{

    private MutableLiveData<Integer> mText;

    public AddNewMedicineViewModel(@NonNull Application application) {

        super(application);

        mText = new MutableLiveData<>();
        mText.setValue(R.string.menu_add_new_medicine);
    }

    public LiveData<Integer> getTitle() {
        return mText;
    }
}