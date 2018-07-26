package me.kevinxchan.kevinxchan.stretchit.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import me.kevinxchan.kevinxchan.stretchit.db.AppDatabase;
import me.kevinxchan.kevinxchan.stretchit.db.RoutineDao;

import java.util.List;

public class RoutineViewModel extends AndroidViewModel {
    private RoutineDao routineDao;
    private LiveData<List<Routine>> routineLiveData;

    public RoutineViewModel(@NonNull Application application) {
        super(application);
        routineDao = AppDatabase.getInstance(application).routineDao();
        routineLiveData = routineDao.getAllRoutines();
    }

    public LiveData<List<Routine>> getRoutinesList() {
        return routineLiveData;
    }

    public void insert(Routine routine) {
        routineDao.insert(routine);
    }

    public void updateRoutineName(String oldName, String newName) {
        routineDao.updateRoutineName(oldName, newName);
    }

    public void updateRoutineTimesUsed(int num, String name) {
        routineDao.updateRoutineTimesUsed(num, name);
    }

    public void deleteByName(String name) {
        routineDao.deleteByName(name);
    }
}
