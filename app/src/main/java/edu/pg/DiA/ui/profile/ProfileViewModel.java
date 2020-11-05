package edu.pg.DiA.ui.profile;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import edu.pg.DiA.R;
import edu.pg.DiA.database.AppDatabase;
import edu.pg.DiA.database.dao.BodyWeightMeasurementDao;
import edu.pg.DiA.database.dao.UserDao;
import edu.pg.DiA.models.Body_weight_measurement;
import edu.pg.DiA.models.User;

public class ProfileViewModel extends AndroidViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<Integer> title;
    public LiveData<User> user;
    public LiveData<Float> weight;
    public UserDao userDao;
    public BodyWeightMeasurementDao bodyWeightMeasurementDao;

    public ProfileViewModel(Application application) {

        super(application);

        userDao = AppDatabase.getInstance(application.getApplicationContext()).userDao();
        user = userDao.getUserLive(User.getCurrentUser().uId);
        bodyWeightMeasurementDao = AppDatabase.getInstance(application.getApplicationContext()).bodyWeightMeasurementDao();
        weight = bodyWeightMeasurementDao.getLatestWeight(User.getCurrentUser().uId);

        mText = new MutableLiveData<>();
        mText.setValue("This is profile fragment");

        title = new MutableLiveData<>();
        title.setValue(R.string.menu_profile);
    }

    public LiveData<String> getText() {
        return mText;
    }
    public LiveData<Integer> getTitle() {
        return title;
    }
}
