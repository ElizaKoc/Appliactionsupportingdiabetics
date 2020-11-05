package edu.pg.DiA.ui.glucose_measurements;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import edu.pg.DiA.R;
import edu.pg.DiA.database.AppDatabase;
import edu.pg.DiA.database.dao.GlucoseMeasurementDao;
import edu.pg.DiA.database.dao.MedicineDao;
import edu.pg.DiA.models.Glucose_measurement;
import edu.pg.DiA.models.Medicine;
import edu.pg.DiA.models.User;

public class GlucoseMeasurementsViewModel extends AndroidViewModel {

    private final MutableLiveData<Integer> mText;
    private final MutableLiveData<Integer> title;
    public LiveData<List<Glucose_measurement>> measurements;
    public GlucoseMeasurementDao glucoseMeasurementDao;

    public GlucoseMeasurementsViewModel(Application application) {

        super(application);

        glucoseMeasurementDao = AppDatabase.getInstance(application.getApplicationContext()).glucoseMeasurementDao();
        measurements = glucoseMeasurementDao.getAllMeasurements(User.getCurrentUser().uId);

        mText = new MutableLiveData<>();
        mText.setValue(R.string.no_data);

        title = new MutableLiveData<>();
        title.setValue(R.string.menu_glucose_measurements);
    }

    public LiveData<Integer> getText() {
        return mText;
    }
    public LiveData<Integer> getTitle() {
        return title;
    }
}
