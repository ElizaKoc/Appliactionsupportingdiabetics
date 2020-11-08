package edu.pg.DiA.ui.weight_measurements;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import edu.pg.DiA.R;
import edu.pg.DiA.database.AppDatabase;
import edu.pg.DiA.database.dao.BodyWeightMeasurementDao;
import edu.pg.DiA.models.BodyWeightMeasurement;
import edu.pg.DiA.models.User;

public class WeightMeasurementsViewModel extends AndroidViewModel {

    private final MutableLiveData<Integer> mText;
    private final MutableLiveData<Integer> title;
    public LiveData<List<BodyWeightMeasurement>> measurements;
    public BodyWeightMeasurementDao bodyWeightMeasurementDao;

    public WeightMeasurementsViewModel(Application application) {

        super(application);

        bodyWeightMeasurementDao = AppDatabase.getInstance(application.getApplicationContext()).bodyWeightMeasurementDao();
        measurements = bodyWeightMeasurementDao.getAllMeasurements(User.getCurrentUser().uId);

        mText = new MutableLiveData<>();
        mText.setValue(R.string.no_data);

        title = new MutableLiveData<>();
        title.setValue(R.string.menu_weight_measurements);
    }

    public LiveData<Integer> getText() {
        return mText;
    }
    public LiveData<Integer> getTitle() {
        return title;
    }
}
