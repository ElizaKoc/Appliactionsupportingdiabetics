package edu.pg.DiA.ui.medicines;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import edu.pg.DiA.R;
import edu.pg.DiA.database.AppDatabase;
import edu.pg.DiA.database.dao.MedicineDao;
import edu.pg.DiA.database.dao.MedicineReminderDao;
import edu.pg.DiA.models.MedicineReminderWithMedicineAndReminder;

public class MedicineViewModel extends AndroidViewModel {

    private MutableLiveData<Integer> mText;
    private MutableLiveData<String> title;
    private MutableLiveData<String> description;
    public LiveData<List<MedicineReminderWithMedicineAndReminder>> medicineReminders;
    public MedicineReminderDao medicineReminderDao;
    public MedicineDao medicineDao;

    public MedicineViewModel(@NonNull Application application, int medicineId) {
        super(application);

        medicineReminderDao = AppDatabase.getInstance(application.getApplicationContext()).medicineReminderDao();
        medicineReminders = medicineReminderDao.getAll(medicineId);

        medicineDao = AppDatabase.getInstance(application.getApplicationContext()).medicineDao();

        mText = new MutableLiveData<>();
        mText.setValue(R.string.no_data);

        title = new MutableLiveData<>();
        title.setValue(medicineDao.getName(medicineId));

        description = new MutableLiveData<>();
        description.setValue(medicineDao.getDescription(medicineId));
    }

    public LiveData<Integer> getText() {
        return mText;
    }
    public LiveData<String> getTitle() {
        return title;
    }
    public LiveData<String> getDescription() {
        return description;
    }
}
