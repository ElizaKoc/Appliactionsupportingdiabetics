package edu.pg.DiA.ui.schedule;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import edu.pg.DiA.R;
import edu.pg.DiA.database.AppDatabase;
import edu.pg.DiA.database.dao.ReminderDao;
import edu.pg.DiA.helpers.FindWeekday;
import edu.pg.DiA.models.Reminder;
import edu.pg.DiA.models.User;

public class PreviewViewModel extends AndroidViewModel {

    private final MutableLiveData<Integer> mText;
    private final MutableLiveData<String> title;
    public LiveData<List<Reminder>> reminders;
    public ReminderDao reminderDao;

    public PreviewViewModel(Application application, String selectedDate) throws ParseException {

        super(application);

        String weekday = new FindWeekday().findWeekday(selectedDate);
        String formattedSelectedDate = selectedDate + " 00:00";

        reminderDao = AppDatabase.getInstance(application.getApplicationContext()).reminderDao();
        reminders = reminderDao.getAllUserRemindersByDate(User.getCurrentUser().uId, formattedSelectedDate, weekday);

        mText = new MutableLiveData<>();
        mText.setValue(R.string.no_data);

        title = new MutableLiveData<>();
        String setTitle = selectedDate + " przypomnienia";
        title.setValue(setTitle);
    }

    public LiveData<Integer> getText() {
        return mText;
    }
    public LiveData<String> getTitle() {
        return title;
    }
}

