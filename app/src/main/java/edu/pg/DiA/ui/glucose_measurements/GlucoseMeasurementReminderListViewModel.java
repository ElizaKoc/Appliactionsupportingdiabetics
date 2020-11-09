package edu.pg.DiA.ui.glucose_measurements;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import edu.pg.DiA.R;
import edu.pg.DiA.database.AppDatabase;
import edu.pg.DiA.database.dao.ReminderDao;
import edu.pg.DiA.models.Reminder;
import edu.pg.DiA.models.User;

public class GlucoseMeasurementReminderListViewModel extends AndroidViewModel {

    private final MutableLiveData<Integer> mText;
    private final MutableLiveData<Integer> title;
    public LiveData<List<Reminder>> measurementReminders;
    public ReminderDao reminderDao;

    public GlucoseMeasurementReminderListViewModel(Application application) {

        super(application);

        reminderDao = AppDatabase.getInstance(application.getApplicationContext()).reminderDao();
        measurementReminders = reminderDao.getMeasurementReminders("pomiar glukozy", User.getCurrentUser().uId);

        mText = new MutableLiveData<>();
        mText.setValue(R.string.no_data);

        title = new MutableLiveData<>();
        title.setValue(R.string.menu_reminder_list);
    }

    public LiveData<Integer> getText() {
        return mText;
    }
    public LiveData<Integer> getTitle() {
        return title;
    }
}
