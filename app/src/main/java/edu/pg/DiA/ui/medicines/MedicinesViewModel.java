package edu.pg.DiA.ui.medicines;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import edu.pg.DiA.R;
import edu.pg.DiA.database.AppDatabase;
import edu.pg.DiA.database.dao.MedicineDao;
import edu.pg.DiA.database.dao.UserDao;
import edu.pg.DiA.models.Medicine;
import edu.pg.DiA.models.User;

public class MedicinesViewModel extends AndroidViewModel {

    private MutableLiveData<Integer> mText;
    private MutableLiveData<Integer> title;
    public LiveData<List<Medicine>> medicines;
    public MedicineDao medicineDao;

    public MedicinesViewModel(Application application) {

        super(application);

        medicineDao = AppDatabase.getInstance(application.getApplicationContext()).medicineDao();
        medicines = medicineDao.getAllMedicines(User.getCurrentUser().uId);

        mText = new MutableLiveData<>();
        mText.setValue(R.string.no_data);

        title = new MutableLiveData<>();
        title.setValue(R.string.menu_medicines);
    }

    public LiveData<Integer> getText() {
        return mText;
    }
    public LiveData<Integer> getTitle() {
        return title;
    }
}
