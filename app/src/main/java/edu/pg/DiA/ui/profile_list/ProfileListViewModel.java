package edu.pg.DiA.ui.profile_list;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import edu.pg.DiA.database.AppDatabase;
import edu.pg.DiA.database.dao.UserDao;
import edu.pg.DiA.models.User;

public class ProfileListViewModel extends AndroidViewModel {

    private MutableLiveData<String> mText;
    public LiveData<List<User>> profiles;
    public UserDao userDao;

    public ProfileListViewModel(Application application) {

        super(application);

        userDao = AppDatabase.getInstance(application.getApplicationContext()).userDao();
        profiles = userDao.getAll();
    }

    public LiveData<String> getText() {
        return mText;
    }
}
