package edu.pg.DiA.ui.journal;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import edu.pg.DiA.R;
import edu.pg.DiA.database.AppDatabase;
import edu.pg.DiA.database.dao.NoteDao;

public class NoteViewModel extends AndroidViewModel {

    private final MutableLiveData<Integer> mText;
    private final MutableLiveData<String> title;
    private final MutableLiveData<String> body;
    public NoteDao noteDao;

    public NoteViewModel(@NonNull Application application, int noteId) {
        super(application);

        noteDao = AppDatabase.getInstance(application.getApplicationContext()).noteDao();

        mText = new MutableLiveData<>();
        mText.setValue(R.string.no_data);

        title = new MutableLiveData<>();
        title.setValue(noteDao.getName(noteId));

        body = new MutableLiveData<>();
        body.setValue(noteDao.getDescription(noteId));
    }

    public LiveData<Integer> getText() {
        return mText;
    }
    public LiveData<String> getTitle() {
        return title;
    }
    public LiveData<String> getBody() {
        return body;
    }
}
